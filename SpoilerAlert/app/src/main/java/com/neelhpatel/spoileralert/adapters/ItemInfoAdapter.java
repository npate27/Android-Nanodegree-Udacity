package com.neelhpatel.spoileralert.adapters;


import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.neelhpatel.spoileralert.ModifyItemActivity;
import com.neelhpatel.spoileralert.R;
import com.neelhpatel.spoileralert.database.AppDatabase;
import com.neelhpatel.spoileralert.database.AppExecutors;
import com.neelhpatel.spoileralert.models.ItemInfo;
import com.neelhpatel.spoileralert.models.LocationInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.neelhpatel.spoileralert.ui.nav.ItemsFragment.ITEM_KEY;

public class ItemInfoAdapter extends RecyclerView.Adapter<ItemInfoAdapter.ViewHolder> {
    private List<ItemInfo> mItemInfos;
    private final ItemsOnClickHandler mClickHandler;
    private Context mContext;
    private AppDatabase mDb;


    public ItemInfoAdapter(Context context, List<ItemInfo> itemInfos, ItemsOnClickHandler clickHandler) {
        mItemInfos = itemInfos;
        mClickHandler = clickHandler;
        mDb = AppDatabase.getsInstance(context);
        mContext = context;
    }

    public interface ItemsOnClickHandler {
        void onClick(ItemInfo movieInfo);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_food, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ItemInfo itemInfo = mItemInfos.get(position);
        holder.itemTitleTv.setText(itemInfo.getItemName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        holder.itemExpirationTv.setText(dateFormat.format(itemInfo.getExpireDate()));
        //TODO:FIX Binding locs

        Observer observer = (Observer<LocationInfo>) locationInfo -> holder.itemLocationNameTv.setText(locationInfo.getLocationName());
        mDb.locationDao().loadLocationById(itemInfo.getLocationId()).observeForever(observer);
        mDb.locationDao().loadLocationById(itemInfo.getLocationId()).removeObserver(observer);
        //TODO://IMAGE STUCK
    }

    @Override
    public int getItemCount() {
        return mItemInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView itemTitleTv;
        public final TextView itemLocationNameTv;
        public final TextView itemExpirationTv;
        public final ImageView itemIv;
        public final ImageButton editBtn;
        public final ImageButton deleteBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitleTv = itemView.findViewById(R.id.item_title_tv);
            itemLocationNameTv = itemView.findViewById(R.id.item_location_tv);
            itemExpirationTv = itemView.findViewById(R.id.item_expiration_tv);
            itemIv = itemView.findViewById(R.id.thumbnail);
            editBtn = itemView.findViewById(R.id.edit_item_btn);
            deleteBtn = itemView.findViewById(R.id.delete_item_btn);
            //TODO: Edit and trash buttons
            itemView.setOnClickListener(this);
            editBtn.setOnClickListener(this);
            deleteBtn.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            ItemInfo iteminfo = mItemInfos.get(adapterPosition);
            switch(v.getId()) {
                case R.id.edit_item_btn:
                    Intent intent = new Intent(mContext, ModifyItemActivity.class);
                    intent.putExtra(ITEM_KEY, iteminfo);
                    mContext.startActivity(intent);
                    break;
                case R.id.delete_item_btn:
                    AppExecutors.getInstance().diskIO().execute(() ->
                            mDb.itemDao().deleteItem(iteminfo));
                    break;
                default:
                    mClickHandler.onClick(iteminfo);
            }
        }
    }

    /**
     * Swaps the current list of ItemInfo objects with a new one
     *
     * @param newItems List of ItemInfo objects to be displayed by adapter
     */
    public void changeData(List<ItemInfo> newItems) {
        mItemInfos = new ArrayList<>();
        mItemInfos.addAll(newItems);
        notifyDataSetChanged();

    }
}
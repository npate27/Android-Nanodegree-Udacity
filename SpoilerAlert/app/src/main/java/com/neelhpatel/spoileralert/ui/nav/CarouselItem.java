package com.neelhpatel.spoileralert.ui.nav;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.neelhpatel.spoileralert.R;
import com.neelhpatel.spoileralert.databinding.ItemCarouselBinding;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.databinding.BindableItem;
import com.xwray.groupie.databinding.ViewHolder;

public class CarouselItem extends BindableItem<ItemCarouselBinding> implements OnItemClickListener {

    private GroupAdapter adapter;
    private RecyclerView.ItemDecoration carouselDecoration;

    public CarouselItem(RecyclerView.ItemDecoration itemDecoration, GroupAdapter adapter) {
        this.carouselDecoration = itemDecoration;
        this.adapter = adapter;
        adapter.setOnItemClickListener(this);
    }

    @Override
    public ViewHolder<ItemCarouselBinding> createViewHolder(@NonNull View itemView) {
        ViewHolder<ItemCarouselBinding> viewHolder = super.createViewHolder(itemView);
        RecyclerView recyclerView = viewHolder.binding.recyclerView;
        recyclerView.addItemDecoration(carouselDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        return viewHolder;
    }

    @Override public void bind(@NonNull ItemCarouselBinding viewBinding, int position) {
        viewBinding.recyclerView.setAdapter(adapter);
    }

    @Override public int getLayout() {
        return R.layout.item_carousel;
    }

    @Override
    public void onItemClick(Item item, View view) {
        adapter.remove(item);
    }
}
package com.neelhpatel.spoileralert;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neelhpatel.spoileralert.database.AppDatabase;
import com.neelhpatel.spoileralert.database.AppExecutors;
import com.neelhpatel.spoileralert.models.ItemInfo;
import com.neelhpatel.spoileralert.models.LocationDetailViewModel;
import com.neelhpatel.spoileralert.models.LocationDetailViewModelFactory;
import com.neelhpatel.spoileralert.models.LocationInfo;
import com.neelhpatel.spoileralert.models.LocationViewModel;

import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

import static com.neelhpatel.spoileralert.ui.nav.ItemsFragment.ITEM_KEY;

public class ItemDetailActivity extends AppCompatActivity {

    ImageView mPhotoIv;
    TextView mLocationTv;
    TextView mExpirationTv;
    TextView mPurchaseTv;
    TextView mPriceTv;
    TextView mQuantityTv;
    TextView mItemTitleTv;
    FloatingActionButton mFab;

    ItemInfo mItemInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        mPhotoIv = findViewById(R.id.photo);
        mLocationTv = findViewById(R.id.location_val_tv);
        mExpirationTv = findViewById(R.id.expiration_val_tv);
        mPurchaseTv = findViewById(R.id.purchase_val_tv);
        mQuantityTv = findViewById(R.id.quantity_val_tv);
        mPriceTv = findViewById(R.id.price_val_tv);
        mItemTitleTv = findViewById(R.id.article_title);
        mFab = findViewById(R.id.edit_fab);
        mFab.setOnClickListener(new FabOnClickListener());


        if (savedInstanceState == null) {
            Intent intent = getIntent();
            //For modifying item
            if (intent.getExtras() != null && intent.getExtras().containsKey(ITEM_KEY)) {
                mItemInfo = getIntent().getParcelableExtra(ITEM_KEY);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                mExpirationTv.setText(dateFormat.format(mItemInfo.getExpireDate()));
                mPurchaseTv.setText(dateFormat.format(mItemInfo.getPurchaseDate()));
                mQuantityTv.setText(mItemInfo.getQuantity());
                mItemTitleTv.setText(mItemInfo.getItemName());
                mPriceTv.setText(Double.toString(mItemInfo.getPrice()));
                AppDatabase mDb = AppDatabase.getsInstance(this);
                LocationDetailViewModelFactory factory = new LocationDetailViewModelFactory(mDb, mItemInfo.getLocationId());
                final LocationDetailViewModel viewModel = ViewModelProviders.of(this, factory).get(LocationDetailViewModel.class);
                viewModel.getLocation().observe(this, locationInfo -> {
                    mLocationTv.setText(locationInfo.getLocationName());
                });
            }
        } else {
            //Get from previous existing item
            if(savedInstanceState.containsKey(ITEM_KEY)) {
                mItemInfo = savedInstanceState.getParcelable(ITEM_KEY);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ITEM_KEY, mItemInfo);
    }

    public class FabOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), ModifyItemActivity.class);
            intent.putExtra(ITEM_KEY, mItemInfo);
            startActivity(intent);
        }
    }
}

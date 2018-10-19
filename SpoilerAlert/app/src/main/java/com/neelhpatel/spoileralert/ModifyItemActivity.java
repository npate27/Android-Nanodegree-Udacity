package com.neelhpatel.spoileralert;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.neelhpatel.spoileralert.database.AppDatabase;
import com.neelhpatel.spoileralert.database.AppExecutors;
import com.neelhpatel.spoileralert.models.ItemInfo;
import com.neelhpatel.spoileralert.models.LocationInfo;
import com.neelhpatel.spoileralert.models.LocationViewModel;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.neelhpatel.spoileralert.ui.BarcodeScanningProcessor.UPC_KEY;

public class ModifyItemActivity extends AppCompatActivity {

    public final static String ITEM_KEY = "item_key";
    public final static String EXPIRE_KEY = "expire_key";
    public final static String PURCHASE_KEY = "purchase_key";
    public final static String QUANTITY_KEY = "quantity_key";
    public final static String PRICE_KEY = "price_key";
    public final static String LOCATION_KEY = "location_key";
    public final static String TITLE_KEY = "title_key";

    ImageView mPhotoIv;
    Spinner mLocationSpinner;
    TextView mExpirationEv;
    TextView mPurchaseEv;
    EditText mPriceEv;
    EditText mQuantityEv;
    EditText mItemTitleEv;
    FloatingActionButton mFab;

    ItemInfo mItemInfo;
    ArrayAdapter<String> mAdapter;
    List<String> locationStrings;
    List<Integer> locationIds;
    //TODO: Handle adding fresh item
    //TODO: handle modifying item
    //TODO: scan button for item?

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_edit);

        mPhotoIv = findViewById(R.id.photo);
        mLocationSpinner = findViewById(R.id.location_spinner);
        mExpirationEv = findViewById(R.id.expiration_val_tv);
        mPurchaseEv = findViewById(R.id.purchase_val_tv);
        mQuantityEv = findViewById(R.id.quantity_ev);
        mPriceEv = findViewById(R.id.price_ev);
        mItemTitleEv = findViewById(R.id.item_name_ev);
        mFab = findViewById(R.id.save_fab);
        mFab.setOnClickListener(new FabOnClickListener());
        mExpirationEv.setOnClickListener(new DateOnClickListener(mExpirationEv));
        mPurchaseEv.setOnClickListener(new DateOnClickListener(mPurchaseEv));
        mPriceEv.addTextChangedListener(new CurrencyEditTextListener());

        LocationViewModel locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        locationViewModel.getLocations().observe(this, locationInfos -> {
            locationStrings = locationInfos.stream().map(LocationInfo::getLocationName).collect(Collectors.toList());
            locationIds = locationInfos.stream().map(LocationInfo::getId).collect(Collectors.toList());
            mAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, locationStrings);
            mLocationSpinner.setAdapter(mAdapter);
            if(mItemInfo != null) {
                mLocationSpinner.setSelection(locationIds.indexOf(mItemInfo.getLocationId()));
            }
        });

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            //For modifying item
            if (intent.getExtras() != null && intent.getExtras().containsKey(ITEM_KEY)){
                mItemInfo = getIntent().getParcelableExtra(ITEM_KEY);

                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                mExpirationEv.setText(dateFormat.format(mItemInfo.getExpireDate()));
                mPurchaseEv.setText(dateFormat.format(mItemInfo.getPurchaseDate()));
                mQuantityEv.setText(mItemInfo.getQuantity());
                mPriceEv.setText(Double.toString(mItemInfo.getPrice()));
                mItemTitleEv.setText(mItemInfo.getItemName());
            } else if (intent.getExtras() != null && intent.getExtras().containsKey(UPC_KEY)){
                String barcode = getIntent().getStringExtra(UPC_KEY);
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("https://api.upcdatabase.org")
                        .addConverterFactory(GsonConverterFactory.create());

                Retrofit retrofit = builder.build();
                BarcodeService service = retrofit.create(BarcodeService.class);

                final Call<UPCItem> call = service.lookupBarcode(barcode, BuildConfig.upcAPIKey);
                new NetworkCall().execute(call);
            }
        } else {
            //Get from previous existing item
            if(savedInstanceState.containsKey(ITEM_KEY)) {
                mItemInfo = savedInstanceState.getParcelable(ITEM_KEY);

                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                mExpirationEv.setText(dateFormat.format(mItemInfo.getExpireDate()));
                mPurchaseEv.setText(dateFormat.format(mItemInfo.getPurchaseDate()));
                mQuantityEv.setText(mItemInfo.getQuantity());
                mPriceEv.setText(Double.toString(mItemInfo.getPrice()));
                mItemTitleEv.setText(mItemInfo.getItemName());
            } else if (savedInstanceState.containsKey(TITLE_KEY)){
                //TODO: Photo stuff
                mItemTitleEv.setText(savedInstanceState.getString(TITLE_KEY));
                mExpirationEv.setText(savedInstanceState.getString(EXPIRE_KEY));
                mPurchaseEv.setText(savedInstanceState.getString(PURCHASE_KEY));
                mQuantityEv.setText(savedInstanceState.getString(QUANTITY_KEY));
                mPriceEv.setText(savedInstanceState.getString(PRICE_KEY));
                mLocationSpinner.setSelection(savedInstanceState.getInt(LOCATION_KEY));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mItemInfo != null) {
            outState.putParcelable(ITEM_KEY, mItemInfo);
        } else {
            //TODO:Photo stuff
            //outState.putString(mPhotoIv.)
            outState.putString(TITLE_KEY, mItemTitleEv.getText().toString());
            outState.putString(EXPIRE_KEY, mExpirationEv.getText().toString());
            outState.putString(PURCHASE_KEY, mPurchaseEv.getText().toString());
            outState.putString(QUANTITY_KEY, mQuantityEv.getText().toString());
            outState.putString(PRICE_KEY, mPriceEv.getText().toString());
            outState.putInt(LOCATION_KEY, mLocationSpinner.getSelectedItemPosition());
        }
    }

    public class FabOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                if(TextUtils.isEmpty(mItemTitleEv.getText())) {
                    mItemTitleEv.setError("Title is required!");
                    return;
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                String title = mItemTitleEv.getText().toString();
                Date expirationDate = dateFormat.parse(mExpirationEv.getText().toString());
                Date purchaseDate = dateFormat.parse(mPurchaseEv.getText().toString());
                Double price = Double.parseDouble(mPriceEv.getText().toString().replaceAll("[^\\d]", ""));
                String quantity = mQuantityEv.getText().toString();
                int locationId = locationIds.get(mLocationSpinner.getSelectedItemPosition());


                if(mItemInfo != null) {
                    mItemInfo.setItemName(title);
                    mItemInfo.setExpireDate(expirationDate);
                    mItemInfo.setPurchaseDate(purchaseDate);
                    mItemInfo.setPrice(price);
                    mItemInfo.setQuantity(quantity);
                    mItemInfo.setLocationId(locationId);

                    AppExecutors.getInstance().diskIO().execute(() -> {
                        AppDatabase mDb = AppDatabase.getsInstance(getApplicationContext());
                        mDb.itemDao().updateItem(mItemInfo);
                        finish();
                    });
                } else {
                    mItemInfo = new ItemInfo(title, expirationDate, purchaseDate, null, price, quantity, locationId);

                    AppExecutors.getInstance().diskIO().execute(() -> {
                        AppDatabase mDb = AppDatabase.getsInstance(getApplicationContext());
                        mDb.itemDao().insertItem(mItemInfo);
                        finish();
                    });
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public class DateOnClickListener implements View.OnClickListener {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        TextView mTextView;

        public DateOnClickListener(TextView textView) {
            mTextView = textView;
        }

        @Override
        public void onClick(View v) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(ModifyItemActivity.this,
                    (view, year, monthOfYear, dayOfMonth) ->
                            mTextView.setText((monthOfYear + 1) + "/" +dayOfMonth + "/" + year),
                    mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    //CREDIT: https://stackoverflow.com/a/5233488
    public class CurrencyEditTextListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            mPriceEv.removeTextChangedListener(this);

            String cleanString = s.toString().replaceAll("[^\\d]", "");

            double parsed = Double.parseDouble(cleanString);
            String formatted = NumberFormat.getCurrencyInstance().format((parsed/100));

            mPriceEv.setText(formatted);
            mPriceEv.setSelection(formatted.length());

            mPriceEv.addTextChangedListener(this);
        }
    }

    private class NetworkCall extends AsyncTask<Call, Void, UPCItem> {
        @Override
        protected UPCItem doInBackground(Call... calls) {
            try {
                Call<UPCItem> call = calls[0];
                Response<UPCItem> response = call.execute();
                return response.body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(UPCItem result) {
            if(result != null) {
                mItemTitleEv.setText(result.getTitle());

            }
        }
    }
}

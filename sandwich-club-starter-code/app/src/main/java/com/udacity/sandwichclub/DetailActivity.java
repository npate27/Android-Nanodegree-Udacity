package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    ActivityDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        // Modified this so that a loading image comes up as a placeholder
        // while the main image loads. If it doesn't load, the error
        // image that is assigned is displayed. 
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.loading_circle)
                .error(R.drawable.stock_image_not_available)
                .into(mBinding.imageIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Populates the DetailActivity UI given a sandwich
     * This method orginially had no Sandwich parameter,
     * I modified it for use with data binding.
     *
     * @param  sandwich the Sandwich object that will be binded to the UI.
     * @return
     */
    private void populateUI(Sandwich sandwich) {
        mBinding.descriptionTv.setText(sandwich.getDescription());
        mBinding.ingredientsTv.setText(sandwich.getIngredients().toString().replaceAll("[\\[\\]]", ""));

        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();

        //Checks to see if any useful info is available for these two details
        //If not, the corresponding textView and label are set to invisible.
        if (placeOfOrigin.isEmpty()) {
            mBinding.originTv.setVisibility(View.INVISIBLE);
            mBinding.textView4.setVisibility(View.INVISIBLE);
        } else {
            mBinding.originTv.setText(placeOfOrigin);
        }

        if (alsoKnownAs.isEmpty()) {
            mBinding.alsoKnownTv.setVisibility(View.INVISIBLE);
            mBinding.textView.setVisibility(View.INVISIBLE);
        } else {
            mBinding.alsoKnownTv.setText(alsoKnownAs.toString().replaceAll("[\\[\\]]", ""));
        }
    }
}

package com.neelhpatel.spoileralert.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.neelhpatel.spoileralert.ModifyItemActivity;
import com.neelhpatel.spoileralert.R;
import com.neelhpatel.spoileralert.ui.nav.ExpiringFragment;
import com.neelhpatel.spoileralert.ui.nav.ItemsFragment;
import com.neelhpatel.spoileralert.ui.nav.LocationsFragment;
import com.neelhpatel.spoileralert.ui.settings.SettingsActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.neelhpatel.spoileralert.ui.MainActivity.FragmentTypes.EXPIRATION;
import static com.neelhpatel.spoileralert.ui.MainActivity.FragmentTypes.ITEMS;
import static com.neelhpatel.spoileralert.ui.MainActivity.FragmentTypes.LOCATIONS;

public class MainActivity extends AppCompatActivity {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({EXPIRATION, LOCATIONS, ITEMS})
    public @interface FragmentTypes {
        int EXPIRATION = 0;
        int LOCATIONS = 1;
        int ITEMS = 2;
    }

    private FloatingActionButton mFab;
    private FloatingActionButton mScanFab;
    private int currentFragmentLayout = EXPIRATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new FabOnClickListener());

        mScanFab = findViewById(R.id.scan_fab);
        mScanFab.setOnClickListener(new FabOnClickListener());

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
        = item -> {
            switch (item.getItemId()) {
                case R.id.expiring_nav_btn:
                    switchToFragment(new ExpiringFragment());
                    updateFab(GONE, R.drawable.ic_add_black_24dp);
                    currentFragmentLayout = EXPIRATION;
                    return true;
                case R.id.locations_nav_btn:
                    switchToFragment(new LocationsFragment());
                    updateFab(VISIBLE, R.drawable.ic_add_location_black_24dp);
                    currentFragmentLayout = LOCATIONS;
                    return true;
                case R.id.items_nav_btn:
                    switchToFragment(new ItemsFragment());
                    updateFab(VISIBLE, R.drawable.ic_add_black_24dp);
                    currentFragmentLayout = ITEMS;
                    return true;
            }
            return false;
        };

    public void switchToFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    public void updateFab(final int visibility, int icon) {
        if(mFab.getVisibility() != visibility) {
            int fromVal = visibility == VISIBLE ? 0 : 1;
            int toVal = fromVal^1;
            ScaleAnimation anim = new ScaleAnimation(fromVal, toVal, fromVal, toVal, 50, 50);
            anim.setFillBefore(true);
            anim.setFillAfter(true);
            anim.setFillEnabled(true);
            anim.setDuration(300);
            anim.setInterpolator(new FastOutSlowInInterpolator());
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mFab.setVisibility(visibility);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            mFab.setAnimation(anim);
        }
        mFab.setImageDrawable(ContextCompat.getDrawable(this, icon));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.settings_option:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class FabOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.fab:
                    switch(currentFragmentLayout) {
                        case EXPIRATION:
                            break;
                        case LOCATIONS:
                            FragmentManager fm = getSupportFragmentManager();
                            AddLocationDialogFragment alertDialog = new AddLocationDialogFragment();
                            alertDialog.show(fm, "fragment_alert");
                            break;
                        case ITEMS:
                            Intent intent = new Intent(getApplicationContext(), ModifyItemActivity.class);
                            startActivity(intent);
                            break;
                        default:
                            break;
                    }
                case R.id.scan_fab:
                    FirebaseApp.initializeApp(getApplicationContext());
                    startActivity(new Intent(MainActivity.this, LivePreviewActivity.class));
                    break;
                default:
                    break;
            }

        }
    }
}

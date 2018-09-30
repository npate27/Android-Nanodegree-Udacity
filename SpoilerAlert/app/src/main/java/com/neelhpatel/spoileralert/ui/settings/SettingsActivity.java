package com.neelhpatel.spoileralert.ui.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.neelhpatel.spoileralert.R;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_settings);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

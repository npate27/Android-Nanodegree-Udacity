package com.neelhpatel.spoileralert.ui.settings;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.neelhpatel.spoileralert.R;
import com.neelhpatel.spoileralert.database.AppDatabase;
import com.neelhpatel.spoileralert.database.AppExecutors;
import com.neelhpatel.spoileralert.models.LocationInfo;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_main);
        Preference dialogPreference = getPreferenceScreen().findPreference("delete_all_items");
        dialogPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(getResources().getString(R.string.dialog_delete_all_confirmation));
                builder.setNegativeButton("CANCEL", null);
                builder.setPositiveButton("OK", (dialog, id) -> AppExecutors.getInstance().diskIO().execute(() -> {
                    AppDatabase db = AppDatabase.getsInstance(getContext());
                    db.clearAllTables();
                    db.locationDao().insertLocation(new LocationInfo("Unknown"));
                }));
                builder.show();
                return true;
            }
        });
    }

}

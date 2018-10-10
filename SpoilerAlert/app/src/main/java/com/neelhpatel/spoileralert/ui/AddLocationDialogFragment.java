package com.neelhpatel.spoileralert.ui;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.neelhpatel.spoileralert.R;
import com.neelhpatel.spoileralert.database.AppDatabase;
import com.neelhpatel.spoileralert.database.AppExecutors;
import com.neelhpatel.spoileralert.models.LocationInfo;
import com.neelhpatel.spoileralert.models.LocationViewModel;

public class AddLocationDialogFragment extends DialogFragment {

    public AddLocationDialogFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_new_location, null);
        final EditText locationName = view.findViewById(R.id.location_et);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
            .setTitle(getResources().getString(R.string.add_location_title))
            .setView(view)
            .setNegativeButton(getResources().getString(R.string.cancel_btn_text), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                }
            })
            .setPositiveButton(getResources().getString(R.string.submit_btn_text), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            AppDatabase db = AppDatabase.getsInstance(getContext());
                            LocationInfo newLocationInfo = new LocationInfo(locationName.getText().toString());
                            db.locationDao().insertLocation(newLocationInfo);
                        }
                    });
                }
            });
        return dialog.create();
    }
}


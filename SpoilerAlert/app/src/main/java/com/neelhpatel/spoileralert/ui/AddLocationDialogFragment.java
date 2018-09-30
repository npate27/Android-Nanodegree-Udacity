package com.neelhpatel.spoileralert.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.neelhpatel.spoileralert.R;

public class AddLocationDialogFragment extends DialogFragment {

    public AddLocationDialogFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext())
            .setTitle(getResources().getString(R.string.add_location_title))
            .setView(R.layout.dialog_new_location)
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
                    //TODO: implement.
                }
            });
        return dialog.create();
    }
}


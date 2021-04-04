package com.example.arduinocontroller.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arduinocontroller.R;

public class DialogUtils {

    public static AlertDialog createLoadingDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(activity.getBaseContext()).inflate(R.layout.dialog_loading, viewGroup, false);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.setCancelable(false);

        return alertDialog;
    }
}

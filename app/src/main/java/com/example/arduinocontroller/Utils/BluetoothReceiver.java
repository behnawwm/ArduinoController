package com.example.arduinocontroller.Utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class BluetoothReceiver extends BroadcastReceiver {
    Context mContext;

    public BluetoothReceiver(Context context) {
        this.mContext = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                    BluetoothAdapter.ERROR);
            switch (state) {
                case BluetoothAdapter.STATE_OFF:
                    // Bluetooth has been turned off;
//                    Toast.makeText(mContext, "Bluetooth has been turned off", Toast.LENGTH_LONG).show();
                    Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult((Activity) context, turnBTon, 1, new Bundle());
                    break;
            }
        }
    }

}

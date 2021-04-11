package com.example.arduinocontroller.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.arduinocontroller.CommandActivity;
import com.example.arduinocontroller.R;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

import static android.content.ContentValues.TAG;

public class BluetoothUtils {
    static Context mContext;
    static BluetoothAdapter mBluetooth = null;
    static BluetoothSocket btSocket = null;
    static boolean isBtConnected = false;
    static String address = null;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public BluetoothUtils(Context context, String address) {
        this.mContext = context;
        this.address = address;
    }

    public static boolean unpairDevice(BluetoothDevice device) {
        try {
            Method m = device.getClass()
                    .getMethod("removeBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
            return true;
        } catch (Exception e) {
            Log.d(TAG, "unpairDevice: " + e.getMessage());
            return false;
        }
    }

    private void sendSignal(String number) {
        if (btSocket != null) {
            try {
                btSocket.getOutputStream().write(number.getBytes());
            } catch (IOException e) {
                Toasty.error(mContext, "Error sending signal\n" + e, Toast.LENGTH_SHORT, true).show();
            }
        }
    }

    private void Disconnect() {
        if (btSocket != null) {
            try {
                btSocket.close();
            } catch (IOException e) {
                Toasty.error(mContext, "Error disconnecting from device\n" + e, Toast.LENGTH_SHORT, true).show();
            }
        }

//        finish();
    }

    public static class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean ConnectSuccess = true;
        AlertDialog alertDialog;

        @Override
        protected void onPreExecute() {
//            DialogUtils dialogUtils = new DialogUtils();
//            alertDialog = dialogUtils.createLoadingDialog((Activity) mContext); //todo fix dialog not showing bug
            Activity activity = (Activity) mContext;
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            ViewGroup viewGroup = activity.findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(activity.getBaseContext()).inflate(R.layout.dialog_loading, viewGroup, false);
            builder.setView(dialogView);
            AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            alertDialog.setCancelable(false);

            alertDialog.show();
        }

        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if (btSocket == null || !isBtConnected) {
                    mBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = mBluetooth.getRemoteDevice(address);
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            } catch (IOException e) {
                ConnectSuccess = false;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                Toasty.error(mContext, "Connection Failed. Is it a SPP Bluetooth? Try again.", Toast.LENGTH_SHORT, true).show();
//                finish();
            } else {
                Toasty.success(mContext, "Connected!", Toast.LENGTH_SHORT, true).show();
                isBtConnected = true;
            }
            alertDialog.dismiss();
        }
    }

}

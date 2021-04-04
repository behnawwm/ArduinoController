package com.example.arduinocontroller.Utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.arduinocontroller.BluetoothReceiver;

import java.lang.reflect.Method;

import es.dmoral.toasty.Toasty;

import static android.content.ContentValues.TAG;

public class BluetoothUtils {

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


}

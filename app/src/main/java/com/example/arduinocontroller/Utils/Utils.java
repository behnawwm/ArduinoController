package com.example.arduinocontroller.Utils;

import android.bluetooth.BluetoothDevice;

import java.lang.reflect.Method;

public class Utils {

    public static void unpairDevice(BluetoothDevice device) {
        try {
            Method m = device.getClass()
                    .getMethod("removeBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
        } catch (Exception e) {
//            Log.e(TAG, e.getMessage());
        }
    }
}

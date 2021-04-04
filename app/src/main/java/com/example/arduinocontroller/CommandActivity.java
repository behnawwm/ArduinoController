package com.example.arduinocontroller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.arduinocontroller.CommandWidgets.CommandWidgetAdapter;
import com.example.arduinocontroller.CommandWidgets.CommandWidgetItem;
import com.example.arduinocontroller.Utils.DialogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class CommandActivity extends AppCompatActivity {
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    String address = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command);

        address = getIntent().getStringExtra(MainActivity.BLUETOOTH_ADDRESS);
        new ConnectBT().execute();

        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("1");
            }
        });
        //////////////
        ArrayList<CommandWidgetItem> mExampleList = new ArrayList<>();
        mExampleList.add(new CommandWidgetItem(R.drawable.ic_refresh, "Line 1"));
        mExampleList.add(new CommandWidgetItem(R.drawable.ic_delete, "Line 3"));
        mExampleList.add(new CommandWidgetItem(R.drawable.ic_bt_disconnect, "Line 5"));

        RecyclerView mRecyclerView = findViewById(R.id.rv_cmnd);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        CommandWidgetAdapter mAdapter = new CommandWidgetAdapter(mExampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new CommandWidgetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                changeItem(position, "Clicked");
            }
            @Override
            public void onDeleteClick(int position) {
//                removeItem(position);
            }
        });
    }

    private void sendSignal(String number) {
        if (btSocket != null) {
            try {
                btSocket.getOutputStream().write(number.getBytes());
            } catch (IOException e) {
                Toasty.error(getBaseContext(), "Error sending signal\n" + e, Toast.LENGTH_SHORT, true).show();
            }
        }
    }

    private void Disconnect() {
        if (btSocket != null) {
            try {
                btSocket.close();
            } catch (IOException e) {
                Toasty.error(getBaseContext(), "Error disconnecting from device\n" + e, Toast.LENGTH_SHORT, true).show();
            }
        }

        finish();
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean ConnectSuccess = true;
        AlertDialog alertDialog;

        @Override
        protected void onPreExecute() {
            alertDialog = DialogUtils.createLoadingDialog(CommandActivity.this);
            alertDialog.show();
        }

        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);
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
                Toasty.error(getBaseContext(), "Connection Failed. Is it a SPP Bluetooth? Try again.", Toast.LENGTH_SHORT, true).show();
                finish();
            } else {
                Toasty.success(getBaseContext(), "Connected!", Toast.LENGTH_SHORT, true).show();
                isBtConnected = true;
            }
            alertDialog.dismiss();
        }
    }
}
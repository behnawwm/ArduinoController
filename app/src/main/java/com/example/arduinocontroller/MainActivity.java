package com.example.arduinocontroller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.arduinocontroller.PairedList.BluetoothListAdapter;
import com.example.arduinocontroller.PairedList.BluetoothListItem;
import com.example.arduinocontroller.Utils.Utils;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class MainActivity extends AppCompatActivity {
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    BluetoothAdapter myBluetooth;
    BluetoothReceiver mReceiver;
    Set<BluetoothDevice> pairedDevices;
    ArrayList<BluetoothDevice> pairedDevicesList;

    RecyclerView mRecyclerView;
    private BluetoothListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<BluetoothListItem> pairedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        mReceiver = new BluetoothReceiver(getApplicationContext());
        registerReceiver(mReceiver, filter);

        setBluetooth(true);
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        if (myBluetooth == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth device not available", Toast.LENGTH_LONG).show();
            finish();
        }

        setupPairedDevicesList();
    }

    public static boolean setBluetooth(boolean enable) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (enable && !isEnabled) {
            return bluetoothAdapter.enable();
        } else if (!enable && isEnabled) {
            return bluetoothAdapter.disable();
        }
        // No need to change bluetooth state
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                setupPairedDevicesList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupPairedDevicesList() {
        pairedDevices = myBluetooth.getBondedDevices();
        pairedDevicesList = new ArrayList<>();
        pairedDevicesList.addAll(pairedDevices);

        pairedList = new ArrayList<>();
        if (pairedDevices.size() > 0)
            for (BluetoothDevice bt : pairedDevices)
                pairedList.add(new BluetoothListItem(bt.getName(), bt.getAddress()));
        else
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();

        buildRecyclerView();
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
//        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new BluetoothListAdapter(pairedList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BluetoothListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                changeItem(position, "Clicked");
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

    private void removeItem(int position) {
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("Unpair?")
                .setMessage("Are you sure you want to unpair this device?")
                .setCancelable(false)
                .setPositiveButton("Unpair", R.drawable.ic_bt_disconnect, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Utils.unpairDevice(pairedDevicesList.get(position));
                        Toast.makeText(MainActivity.this, "Unpaired!", Toast.LENGTH_SHORT).show();
                        mAdapter.notifyDataSetChanged();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", R.drawable.ic_close, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Bluetooth activated!", Toast.LENGTH_LONG).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Please enable Bluetooth!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // Unregister broadcast listeners
        unregisterReceiver(mReceiver);
    }
}
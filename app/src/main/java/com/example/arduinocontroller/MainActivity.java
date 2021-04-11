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

import com.example.arduinocontroller.UI.PairedList.BluetoothListAdapter;
import com.example.arduinocontroller.UI.PairedList.BluetoothListItem;
import com.example.arduinocontroller.Utils.BluetoothReceiver;
import com.example.arduinocontroller.Utils.BluetoothUtils;

import java.util.ArrayList;
import java.util.Set;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements BluetoothListAdapter.OnItemClickListener {
    static final String BLUETOOTH_ADDRESS = "bb_address";

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


        if (!setBluetooth(true))
            Toasty.warning(getBaseContext(), "Bluetooth device not available", Toast.LENGTH_LONG).show();

        setupPairedDevicesList();
    }

    public boolean setBluetooth(boolean enable) {
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        if (myBluetooth == null) {
            return false;
        }

        boolean isEnabled = myBluetooth.isEnabled();
        if (enable && !isEnabled) {
            return myBluetooth.enable();
        } else if (!enable && isEnabled) {
            return myBluetooth.disable();
        }
        // No need to change bluetooth state
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_refresh:
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
            Toasty.info(getBaseContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();

        buildRecyclerView();
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
//        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new BluetoothListAdapter(pairedList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);
    }

    private void removeItem(int position) {
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("Unpair ?")
                .setMessage("Are you sure you want to unpair this device?")
                .setCancelable(false)
                .setPositiveButton("Unpair", R.drawable.ic_bt_disconnect, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (!BluetoothUtils.unpairDevice(pairedDevicesList.get(position))) {
                            Toasty.warning(getBaseContext(), "Unable to unpair this device!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toasty.success(getBaseContext(), "Unpaired!", Toast.LENGTH_LONG).show();
                        mAdapter.notifyDataSetChanged();
                        dialogInterface.dismiss();
                        setupPairedDevicesList();
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
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, CommandActivity.class);
        intent.putExtra(BLUETOOTH_ADDRESS, pairedDevicesList.get(position).getAddress());
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        removeItem(position);
    }


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        mReceiver = new BluetoothReceiver(getApplicationContext());
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        // Unregister broadcast listeners
        unregisterReceiver(mReceiver);
    }
}
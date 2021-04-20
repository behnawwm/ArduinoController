package com.example.arduinocontroller.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.arduinocontroller.R;
import com.example.arduinocontroller.UI.CommandWidgets.CommandWidgetAdapter;
import com.example.arduinocontroller.UI.CommandWidgets.CommandWidgetItem;
import com.example.arduinocontroller.UI.CommandWidgets.CommandWidgetViewHolder;
import com.example.arduinocontroller.UI.Widgets.Button.ButtonActivity;

import java.util.ArrayList;

public class CommandActivity extends AppCompatActivity implements CommandWidgetViewHolder.OnCommandItemClickListener {
    public static final String BLUETOOTH_ADDRESS = "bb_address";
    RecyclerView mRecyclerView;
    String address = null;
    ArrayList<CommandWidgetItem> mExampleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command);

        address = getIntent().getStringExtra(MainActivity.BLUETOOTH_ADDRESS);

        setUpListItems();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.rv_cmnd);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        CommandWidgetAdapter mAdapter = new CommandWidgetAdapter(mExampleList, this);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setUpListItems() {
        mExampleList = new ArrayList<>();
        mExampleList.add(new CommandWidgetItem(R.drawable.switch_button, "Button"));
        mExampleList.add(new CommandWidgetItem(R.drawable.dimmer, "Dimmer"));
        mExampleList.add(new CommandWidgetItem(R.drawable.voice, "Voice Command"));
        mExampleList.add(new CommandWidgetItem(R.drawable.terminal, "Terminal"));
        mExampleList.add(new CommandWidgetItem(R.drawable.joystick, "Joystick"));
        mExampleList.add(new CommandWidgetItem(R.drawable.accelerometer, "Accelerometer"));
        mExampleList.add(new CommandWidgetItem(R.drawable.timer, "Timer"));
        mExampleList.add(new CommandWidgetItem(R.drawable.custom, "Custom"));
    }

    @Override
    public void onItemClick(int position) {
//        changeItem(position, "Clicked");
        switch (position) {
            case 0:
                Intent intent = new Intent(CommandActivity.this, ButtonActivity.class);
                intent.putExtra(BLUETOOTH_ADDRESS, address);
                startActivity(intent);
                break;
            default:

        }
    }

}

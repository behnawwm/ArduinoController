package com.example.arduinocontroller.UI.Widgets.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;
import com.example.arduinocontroller.R;
import com.example.arduinocontroller.UI.Widgets.Button.BottomSheet.ButtonWidgetBottomSheetDialog;
import com.example.arduinocontroller.UI.Widgets.Button.RV.ButtonWidgetAdapter;
import com.example.arduinocontroller.UI.Widgets.Button.RV.ButtonWidgetViewHolder;
import com.example.arduinocontroller.UI.Widgets.Button.RV.ButtonWidgetViewModel;
import com.example.arduinocontroller.Utils.BluetoothUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.mikepenz.itemanimators.AlphaInAnimator;
import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.ArrowPositionRules;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import stream.customalert.CustomAlertDialogue;

@AndroidEntryPoint
public class ButtonActivity extends AppCompatActivity implements ButtonWidgetViewHolder.OnWidgetButtonListener {
    BluetoothUtils bluetoothUtils;

    private ButtonWidgetViewModel mButtonWidgetViewModel;
    RecyclerView recyclerView;
    ButtonWidgetAdapter adapter;
    FloatingActionButton fab;
    List<ButtonWidgetItem> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
        getSupportActionBar().setTitle("Button");

        //todo this & send singnal further
//        String address = getIntent().getStringExtra(CommandActivity.BLUETOOTH_ADDRESS);
//        bluetoothUtils = new BluetoothUtils(ButtonActivity.this, address);
//        new BluetoothUtils.ConnectBT().execute();

        recyclerView = findViewById(R.id.rv_widget_button);
        fab = findViewById(R.id.fab_activity_widget_button);

        setUpRecyclerView();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupAddBottomSheet();
            }
        });
    }

    private void setUpRecyclerView() {
        adapter = new ButtonWidgetAdapter( this);
        mButtonWidgetViewModel = new ViewModelProvider(this).get(ButtonWidgetViewModel.class);
        mButtonWidgetViewModel.getAllItems().observe(this, items -> {
            adapter.submitList(items);
            mList = items;
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new AlphaInAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_widget_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_switch_delete:
                CustomAlertDialogue.Builder alert = new CustomAlertDialogue.Builder(this)
                        .setStyle(CustomAlertDialogue.Style.DIALOGUE)
                        .setCancelable(false)
                        .setTitle("Delete All")
                        .setMessage("Are you sure you want to delete all Buttons?")
                        .setPositiveText("Confirm")
                        .setPositiveColor(R.color.negative)
                        .setPositiveTypeface(Typeface.DEFAULT)
                        .setOnPositiveClicked(new CustomAlertDialogue.OnPositiveClicked() {
                            @Override
                            public void OnClick(View view, Dialog dialog) {
                                mButtonWidgetViewModel.deleteAll();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeText("Cancel")
                        .setNegativeColor(R.color.positive)
                        .setOnNegativeClicked(new CustomAlertDialogue.OnNegativeClicked() {
                            @Override
                            public void OnClick(View view, Dialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .setDecorView(getWindow().getDecorView())
                        .build();
                alert.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupAddBottomSheet() {
        ButtonWidgetBottomSheetDialog bottomSheet = new ButtonWidgetBottomSheetDialog();
        bottomSheet.show(getSupportFragmentManager(),
                "ModalBottomSheet");
    }

    @Override
    public void onItemClick(int position) {
//        Toast.makeText(this, "item click", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOptionsClick(int position) {
        Balloon balloon = new Balloon.Builder(getApplicationContext())
                .setLayout(R.layout.popup_options_widget_button)
                .setArrowSize(10)
                .setArrowOrientation(ArrowOrientation.RIGHT)
                .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
                .setArrowPosition(0.5f)
                .setCornerRadius(4f)
                .setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.semi_gray))
                .setBalloonAnimation(BalloonAnimation.CIRCULAR)
                .build();
        balloon.show(recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.iv_options_list_widget_button));
        balloon.getContentView().findViewById(R.id.btn_popup_edit_widget_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ButtonWidgetBottomSheetDialog bottomSheet = new ButtonWidgetBottomSheetDialog(mList.get(position));
                bottomSheet.show(getSupportFragmentManager(),
                        "ModalBottomSheet");
                balloon.dismiss();
            }
        });
        balloon.getContentView().findViewById(R.id.btn_popup_customize_widget_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ButtonActivity.this, "Coming soon...", Toast.LENGTH_SHORT).show();
                //todo
            }
        });
        balloon.getContentView().findViewById(R.id.btn_popup_delete_widget_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomAlertDialogue.Builder alert = new CustomAlertDialogue.Builder(ButtonActivity.this)
                        .setStyle(CustomAlertDialogue.Style.DIALOGUE)
                        .setCancelable(false)
                        .setTitle("Delete Button?")
                        .setMessage("Are you sure you want to delete this button")
                        .setPositiveText("Confirm")
                        .setPositiveColor(R.color.negative)
                        .setPositiveTypeface(Typeface.DEFAULT)
                        .setOnPositiveClicked(new CustomAlertDialogue.OnPositiveClicked() {
                            @Override
                            public void OnClick(View view, Dialog dialog) {
                                mButtonWidgetViewModel.deleteById(mList.get(position).getId());
                                dialog.dismiss();
                                balloon.dismiss();
                            }
                        })
                        .setNegativeText("Cancel")
                        .setNegativeColor(R.color.positive)
                        .setOnNegativeClicked(new CustomAlertDialogue.OnNegativeClicked() {
                            @Override
                            public void OnClick(View view, Dialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .build();
                alert.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onToggleClick(int position, boolean b) {
        SwitchMaterial itemToggleView = recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.switch_toggle_list_widget_button);
        View itemView = recyclerView.findViewHolderForAdapterPosition(position).itemView;

        if (b) {
            itemToggleView.setText("ON");
            itemToggleView.setTextColor(itemView.getContext().getColor(R.color.forest_green));
            itemView.setBackgroundColor(itemView.getContext().getColor(R.color.yellow2));
        } else {
            itemToggleView.setText("OFF");
            itemToggleView.setTextColor(itemView.getContext().getColor(R.color.google));
            itemView.setBackgroundColor(itemView.getContext().getColor(R.color.blue_grey_light));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onTouchClick(int position, MotionEvent event) {
        SwitchMaterial itemToggleView = recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.switch_toggle_list_widget_button);
        View itemView = recyclerView.findViewHolderForAdapterPosition(position).itemView;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            itemToggleView.setChecked(true);
            itemToggleView.setText("ON");
            itemToggleView.setTextColor(itemView.getContext().getColor(R.color.forest_green));
            itemView.setBackgroundColor(itemView.getContext().getColor(R.color.yellow2));
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            itemToggleView.setChecked(false);
            itemToggleView.setText("OFF");
            itemToggleView.setTextColor(itemView.getContext().getColor(R.color.google));
            itemView.setBackgroundColor(itemView.getContext().getColor(R.color.blue_grey_light));
        }
    }
}
package com.example.arduinocontroller.UI.Widgets.Dimmer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;
import com.example.arduinocontroller.DB.Model.DimmerWidgetItem;
import com.example.arduinocontroller.R;
import com.example.arduinocontroller.UI.Widgets.Button.BottomSheet.ButtonWidgetBottomSheetDialog;
import com.example.arduinocontroller.UI.Widgets.Button.ButtonActivity;
import com.example.arduinocontroller.UI.Widgets.Dimmer.BottomSheet.DimmerWidgetBottomSheetDialog;
import com.example.arduinocontroller.UI.Widgets.Dimmer.RV.DimmerWidgetAdapter;
import com.example.arduinocontroller.UI.Widgets.Dimmer.RV.DimmerWidgetViewHolder;
import com.example.arduinocontroller.UI.Widgets.Dimmer.RV.DimmerWidgetViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikepenz.itemanimators.AlphaInAnimator;
import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.ArrowPositionRules;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import stream.customalert.CustomAlertDialogue;

@AndroidEntryPoint
public class DimmerActivity extends AppCompatActivity implements DimmerWidgetViewHolder.OnWidgetDimmerListener {
    private DimmerWidgetViewModel mDimmerWidgetViewModel;
    RecyclerView recyclerView;
    DimmerWidgetAdapter adapter;
    FloatingActionButton fab;
    List<DimmerWidgetItem> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimmer);
        getSupportActionBar().setTitle("Dimmer");

        recyclerView = findViewById(R.id.rv_widget_dimmer);
        fab = findViewById(R.id.fab_activity_widget_dimmer);

        setUpRecyclerview();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupAddBottomSheet();
            }
        });
//        seekBar.getConfigBuilder()
//                .min(0)
//                .max(255)
//                .progress(0)
//                .trackColor(ContextCompat.getColor(getBaseContext(), R.color.semi_gray))
//                .secondTrackColor(ContextCompat.getColor(getBaseContext(), R.color.forest_green))
//
//                .sectionCount(1)
//                .sectionTextColor(ContextCompat.getColor(getBaseContext(), R.color.gray))
//                .sectionTextSize(10)
//                .sectionTextPosition(BubbleSeekBar.TextPosition.BELOW_SECTION_MARK)
//
//                .showThumbText()
//
//                .bubbleColor(ContextCompat.getColor(getBaseContext(), R.color.forest_green))
//                .bubbleTextColor(ContextCompat.getColor(getBaseContext(), R.color.white))
//                .bubbleTextSize(14)
//                .build();

    }

    private void setUpRecyclerview() {
        adapter = new DimmerWidgetAdapter(this);
        mDimmerWidgetViewModel = new ViewModelProvider(this).get(DimmerWidgetViewModel.class);
        mDimmerWidgetViewModel.getAllItems().observe(this, items -> {
            adapter.submitList(items);
            mList = items;
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new AlphaInAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupAddBottomSheet() {
        DimmerWidgetBottomSheetDialog bottomSheet = new DimmerWidgetBottomSheetDialog();
        bottomSheet.show(getSupportFragmentManager(),
                "ModalBottomSheet");
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onProgressChanged(int position, int progress) {
        DimmerWidgetItem item = mList.get(position);
        item.setProgress(progress);
        mDimmerWidgetViewModel.update(mList.get(position), item);
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
        balloon.show(recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.iv_options_list_widget_dimmer));
        balloon.getContentView().findViewById(R.id.btn_popup_edit_widget_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DimmerWidgetBottomSheetDialog bottomSheet = new DimmerWidgetBottomSheetDialog(mList.get(position));
                bottomSheet.show(getSupportFragmentManager(),
                        "ModalBottomSheet");
                balloon.dismiss();
            }
        });
        balloon.getContentView().findViewById(R.id.btn_popup_customize_widget_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DimmerActivity.this, "Coming soon...", Toast.LENGTH_SHORT).show();
                //todo
            }
        });
        balloon.getContentView().findViewById(R.id.btn_popup_delete_widget_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomAlertDialogue.Builder alert = new CustomAlertDialogue.Builder(DimmerActivity.this)
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
                                mDimmerWidgetViewModel.deleteById(mList.get(position).getId());
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
}
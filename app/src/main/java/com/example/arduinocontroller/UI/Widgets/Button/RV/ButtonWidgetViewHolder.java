package com.example.arduinocontroller.UI.Widgets.Button.RV;


import android.app.Application;
import android.app.Dialog;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;
import com.example.arduinocontroller.R;
import com.example.arduinocontroller.UI.Widgets.Button.BottomSheet.ButtonWidgetBottomSheetDialog;
import com.example.arduinocontroller.Utils.AnimationUtils;
import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.ArrowPositionRules;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;

import stream.customalert.CustomAlertDialogue;

public class ButtonWidgetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final TextView itemNameView;
    private final TextView itemOnView;
    private final TextView itemOffView;
    private final ImageView itemToggleView;
    private final ImageView itemOptionsView;

    OnWidgetButtonListener onWidgetButtonListener;

    public ButtonWidgetViewHolder(View itemView, OnWidgetButtonListener onWidgetButtonListener) {
        super(itemView);

        itemNameView = itemView.findViewById(R.id.tv_name_list_widget_button);
        itemOnView = itemView.findViewById(R.id.tv_on_list_widget_button);
        itemOffView = itemView.findViewById(R.id.tv_off_list_widget_button);
        itemToggleView = itemView.findViewById(R.id.iv_toggle_list_widget_button);
        itemOptionsView = itemView.findViewById(R.id.iv_options_list_widget_button);

        this.onWidgetButtonListener = onWidgetButtonListener;

        itemView.setOnClickListener(this);
        itemOptionsView.setOnClickListener(this);
    }

    public void bind(ButtonWidgetItem item) {
        itemNameView.setText(item.getText());
        itemOnView.setText(item.getOnCommand());
        itemOffView.setText(item.getOffCommand());

//        itemOptionsView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Balloon balloon = new Balloon.Builder(view.getContext())
//                        .setLayout(R.layout.popup_options_widget_button)
//                        .setArrowSize(10)
//                        .setArrowOrientation(ArrowOrientation.RIGHT)
//                        .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
//                        .setArrowPosition(0.5f)
//                        .setCornerRadius(4f)
//                        .setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.semi_gray))
//                        .setBalloonAnimation(BalloonAnimation.CIRCULAR)
//                        .build();
//                balloon.show(itemOptionsView);
//                //todo listener
//                balloon.getContentView().findViewById(R.id.btn_popup_edit_widget_button).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        ButtonWidgetBottomSheetDialog bottomSheet = new ButtonWidgetBottomSheetDialog(item);
//                        bottomSheet.show(((AppCompatActivity) itemView.getContext()).getSupportFragmentManager(),
//                                "ModalBottomSheet");
//                    }
//                });
////                balloon.getContentView().findViewById(R.id.btn_popup_customize_widget_button).setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View view) {
////                          //todo
////                    }
////                });
//                balloon.getContentView().findViewById(R.id.btn_popup_delete_widget_button).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
////                        ButtonWidgetViewModel viewModel = new ButtonWidgetViewModel((Application) itemView.getContext().getApplicationContext());
////                        viewModel.deleteById(item.getId());
//                    }
//                });
//            }
//        });
        //todo fix instant touch and scroll
        if (item.getType() == ButtonWidgetItem.TYPE_PUSH) {
            itemToggleView.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN)
                        AnimationUtils.buttonWidgetTurnAnimation(v.getContext(), itemToggleView, true);
                    else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                        AnimationUtils.buttonWidgetTurnAnimation(v.getContext(), itemToggleView, false);

                    return true;
                }
            });
        } else {
            itemToggleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AnimationUtils.buttonWidgetTurnOnOffAnimation(itemView.getContext(), itemToggleView);
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        onWidgetButtonListener.onItemClick(getAdapterPosition());
        onWidgetButtonListener.onOptionsClick(getAdapterPosition());

    }



    public interface OnWidgetButtonListener {
        void onItemClick(int position);

        void onOptionsClick(int position);

    }

}

package com.example.arduinocontroller.UI.Widgets.Button.RV;


import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;
import com.example.arduinocontroller.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class ButtonWidgetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, View.OnTouchListener {
    private final TextView itemNameView;
    private final TextView itemOnView;
    private final TextView itemOffView;
    //    private final ImageView itemToggleView;
    private final SwitchMaterial itemToggleView;
    private final ImageView itemOptionsView;

    OnWidgetButtonListener onWidgetButtonListener;

    public ButtonWidgetViewHolder(View itemView, OnWidgetButtonListener onWidgetButtonListener) {
        super(itemView);

        itemNameView = itemView.findViewById(R.id.tv_name_list_widget_button);
        itemOnView = itemView.findViewById(R.id.tv_on_list_widget_button);
        itemOffView = itemView.findViewById(R.id.tv_off_list_widget_button);
//        itemToggleView = itemView.findViewById(R.id.iv_toggle_list_widget_button);
        itemToggleView = itemView.findViewById(R.id.switch_toggle_list_widget_button);
        itemOptionsView = itemView.findViewById(R.id.iv_options_list_widget_button);

        this.onWidgetButtonListener = onWidgetButtonListener;
    }

    public void bind(ButtonWidgetItem item) {
        itemNameView.setText(item.getText());
        itemOnView.setText(item.getOnCommand());
        itemOffView.setText(item.getOffCommand());

        itemView.setOnClickListener(this);
        itemOptionsView.setOnClickListener(this);
        if (item.getType() == ButtonWidgetItem.TYPE_TOGGLE)
            itemToggleView.setOnCheckedChangeListener(this);
        else
            itemToggleView.setOnTouchListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == itemOptionsView)
            onWidgetButtonListener.onOptionsClick(getAdapterPosition());
        else
            onWidgetButtonListener.onItemClick(getAdapterPosition());


    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        onWidgetButtonListener.onToggleClick(getAdapterPosition(), b);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        onWidgetButtonListener.onTouchClick(getAdapterPosition(),motionEvent);
        return true;
    }


    public interface OnWidgetButtonListener {
        void onItemClick(int position);

        void onOptionsClick(int position);

        void onToggleClick(int position, boolean b);

        void onTouchClick(int adapterPosition, MotionEvent motionEvent);
    }

}

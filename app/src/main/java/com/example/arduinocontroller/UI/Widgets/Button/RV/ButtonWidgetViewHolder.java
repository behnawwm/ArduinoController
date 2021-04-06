package com.example.arduinocontroller.UI.Widgets.Button.RV;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;
import com.example.arduinocontroller.R;
import com.example.arduinocontroller.Utils.AnimationUtils;

public class ButtonWidgetViewHolder extends RecyclerView.ViewHolder {
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
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onWidgetButtonListener.onItemClick(getAdapterPosition());
            }
        });
        itemToggleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimationUtils.turnOnAnimation(itemView.getContext(), itemToggleView);
//                onWidgetButtonListener.onToggleClick(getAdapterPosition());
            }
        });
        itemOptionsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onWidgetButtonListener.onOptionsClick(getAdapterPosition());
            }
        });
    }

    public void bind(Context context, ButtonWidgetItem item) {
        itemNameView.setText(item.getText());
        itemOnView.setText(item.getOnCommand());
        itemOffView.setText(item.getOffCommand());
        //change push & toggle
        //todo
    }


    public interface OnWidgetButtonListener {
        void onItemClick(int position);

        void onOptionsClick(int position);

    }

}

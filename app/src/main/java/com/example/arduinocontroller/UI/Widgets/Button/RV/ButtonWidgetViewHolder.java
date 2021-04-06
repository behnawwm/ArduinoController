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

    public ButtonWidgetViewHolder(View itemView) {
        super(itemView);
        itemNameView = itemView.findViewById(R.id.tv_name_list_widget_button);
        itemOnView = itemView.findViewById(R.id.tv_on_list_widget_button);
        itemOffView = itemView.findViewById(R.id.tv_off_list_widget_button);
        itemToggleView = itemView.findViewById(R.id.iv_toggle_list_widget_button);
    }

    public void bind(Context context, ButtonWidgetItem item) {
        itemNameView.setText(item.getText());
        itemOnView.setText(item.getOnCommand());
        itemOffView.setText(item.getOffCommand());
        //change push & toggle
        //todo
//        AnimationUtils.turnOnAnimation(context, itemToggleView);
    }

}

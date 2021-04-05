package com.example.arduinocontroller.Widgets.Button.RV;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.arduinocontroller.R;

public class ButtonWidgetViewHolder extends RecyclerView.ViewHolder {
    private final TextView wordItemView;

    private ButtonWidgetViewHolder(View itemView) {
        super(itemView);
        wordItemView = itemView.findViewById(R.id.tv_name_list_widget_button);
    }

    public void bind(String text) {
        wordItemView.setText(text);
    }

    public static ButtonWidgetViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_widget_button, parent, false);
        return new ButtonWidgetViewHolder(view);
    }

}

package com.example.arduinocontroller.UI.Widgets.Button.RV;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;
import com.example.arduinocontroller.R;

import java.util.ArrayList;
import java.util.List;

public class ButtonWidgetAdapter extends ListAdapter<ButtonWidgetItem, ButtonWidgetViewHolder> {
    private ButtonWidgetViewHolder.OnWidgetButtonListener mOnWidgetButtonListener;


    public ButtonWidgetAdapter(ButtonWidgetViewHolder.OnWidgetButtonListener mOnWidgetButtonListener) {
        super(DIFF_CALLBACK);
        this.mOnWidgetButtonListener = mOnWidgetButtonListener;
    }

    @NonNull
    @Override
    public ButtonWidgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_widget_button, parent, false);
        return new ButtonWidgetViewHolder(view, mOnWidgetButtonListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonWidgetViewHolder holder, int position) {
        ButtonWidgetItem current = getItem(position);
        holder.bind(current);
    }

    private static final DiffUtil.ItemCallback<ButtonWidgetItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<ButtonWidgetItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull ButtonWidgetItem oldItem, @NonNull ButtonWidgetItem newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ButtonWidgetItem oldItem, @NonNull ButtonWidgetItem newItem) {
            return oldItem.getText().equals(newItem.getText()) &&
                    oldItem.getOffCommand().equals(newItem.getOffCommand()) &&
                    oldItem.getOnCommand().equals(newItem.getOnCommand()) &&
                    oldItem.getType() == newItem.getType();
        }
    };
}

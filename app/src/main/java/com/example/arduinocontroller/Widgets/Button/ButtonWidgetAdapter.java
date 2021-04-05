package com.example.arduinocontroller.Widgets.Button;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;
import com.example.arduinocontroller.R;
import com.example.arduinocontroller.Widgets.Button.RV.ButtonWidgetViewHolder;

import java.util.ArrayList;

public class ButtonWidgetAdapter extends ListAdapter<ButtonWidgetItem, ButtonWidgetViewHolder> {

    protected ButtonWidgetAdapter(@NonNull DiffUtil.ItemCallback<ButtonWidgetItem> diffCallback) {
        super(diffCallback);
    }

    @Override
    public ButtonWidgetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ButtonWidgetViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(ButtonWidgetViewHolder holder, int position) {
        ButtonWidgetItem current = getItem(position);
        holder.bind(current.getText());
    }

    static class WordDiff extends DiffUtil.ItemCallback<ButtonWidgetItem> {

        @Override
        public boolean areItemsTheSame(@NonNull ButtonWidgetItem oldItem, @NonNull ButtonWidgetItem newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ButtonWidgetItem oldItem, @NonNull ButtonWidgetItem newItem) {
            return oldItem.getText().equals(newItem.getText());
        }
    }

}

package com.example.arduinocontroller.UI.Widgets.Dimmer.RV;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;
import com.example.arduinocontroller.DB.Model.DimmerWidgetItem;
import com.example.arduinocontroller.R;
import com.example.arduinocontroller.UI.Widgets.Dimmer.DimmerActivity;

import java.util.ArrayList;
import java.util.List;

public class DimmerWidgetAdapter extends ListAdapter<DimmerWidgetItem, DimmerWidgetViewHolder> {
    private DimmerWidgetViewHolder.OnWidgetDimmerListener mOnWidgetDimmerListener;


    public DimmerWidgetAdapter(DimmerWidgetViewHolder.OnWidgetDimmerListener mOnWidgetDimmerListener) {
        super(DIFF_CALLBACK);
        this.mOnWidgetDimmerListener = mOnWidgetDimmerListener;

    }

    @NonNull
    @Override
    public DimmerWidgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_widget_dimmer, parent, false);
        return new DimmerWidgetViewHolder(view, mOnWidgetDimmerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DimmerWidgetViewHolder holder, int position) {
        DimmerWidgetItem current = getItem(position);
        holder.bind(current);
    }


    private static final DiffUtil.ItemCallback<DimmerWidgetItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<DimmerWidgetItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull DimmerWidgetItem oldItem, @NonNull DimmerWidgetItem newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull DimmerWidgetItem oldItem, @NonNull DimmerWidgetItem newItem) {
            return oldItem.getText().equals(newItem.getText()) &&
                    oldItem.getProgress() == newItem.getProgress();
//                    oldItem.getCommand().equals(newItem.getCommand());
        }
    };
}

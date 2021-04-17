package com.example.arduinocontroller.UI.Widgets.Dimmer.RV;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;
import com.example.arduinocontroller.DB.Model.DimmerWidgetItem;
import com.example.arduinocontroller.R;
import com.example.arduinocontroller.UI.Widgets.Dimmer.DimmerActivity;

import java.util.ArrayList;
import java.util.List;

public class DimmerWidgetAdapter extends RecyclerView.Adapter<DimmerWidgetViewHolder> {
    Context mContext;
    private DimmerWidgetViewHolder.OnWidgetDimmerListener mOnWidgetDimmerListener;
    private List<DimmerWidgetItem> mItems = new ArrayList<>();

    public DimmerWidgetAdapter(Context context, List<DimmerWidgetItem> items, DimmerWidgetViewHolder.OnWidgetDimmerListener mOnWidgetDimmerListener) {
        this.mContext = context;
        this.mItems = items;
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
        DimmerWidgetItem current = mItems.get(position);
        holder.bind(current);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateDimmerWidgetListItems(List<DimmerWidgetItem> list) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DimmerWidgetDiff(this.mItems, list));
        this.mItems.clear();
        this.mItems.addAll(list);
        diffResult.dispatchUpdatesTo(this);
    }

    static class DimmerWidgetDiff extends DiffUtil.Callback {
        List<DimmerWidgetItem> oldItems;
        List<DimmerWidgetItem> newItems;

        public DimmerWidgetDiff(List<DimmerWidgetItem> oldItems, List<DimmerWidgetItem> newItems) {
            this.oldItems = oldItems;
            this.newItems = newItems;
        }


        @Override
        public int getOldListSize() {
            return oldItems.size();
        }

        @Override
        public int getNewListSize() {
            return newItems.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldItems.get(oldItemPosition).getId() == newItems.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldItems.get(oldItemPosition).getText().equals(newItems.get(oldItemPosition).getText()) &&
                    oldItems.get(oldItemPosition).getProgress() == (newItems.get(oldItemPosition).getProgress());
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            return super.getChangePayload(oldItemPosition, newItemPosition);
        }
    }
}

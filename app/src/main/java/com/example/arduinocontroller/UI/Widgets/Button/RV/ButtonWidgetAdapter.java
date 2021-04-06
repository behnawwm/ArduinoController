package com.example.arduinocontroller.UI.Widgets.Button.RV;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;
import com.example.arduinocontroller.R;

import java.util.ArrayList;
import java.util.List;

public class ButtonWidgetAdapter extends RecyclerView.Adapter<ButtonWidgetViewHolder> {
    Context mContext;
    private ButtonWidgetViewHolder.OnWidgetButtonListener mOnWidgetButtonListener;
    private ArrayList<ButtonWidgetItem> mItems = new ArrayList<>();

    public ButtonWidgetAdapter(Context mContext, ArrayList<ButtonWidgetItem> items, ButtonWidgetViewHolder.OnWidgetButtonListener mOnWidgetButtonListener) {
        this.mItems = items;
        this.mContext = mContext;
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
        ButtonWidgetItem current = mItems.get(position);
        holder.bind(mContext, current);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void toggleButton(int position){

    }
    public void updateButtonWidgetListItems(List<ButtonWidgetItem> list) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ButtonWidgetDiff(this.mItems, list));
        this.mItems.clear();
        this.mItems.addAll(list);
        diffResult.dispatchUpdatesTo(this);
    }

    static class ButtonWidgetDiff extends DiffUtil.Callback {
        List<ButtonWidgetItem> oldItems;
        List<ButtonWidgetItem> newItems;

        public ButtonWidgetDiff(List<ButtonWidgetItem> oldItems, List<ButtonWidgetItem> newItems) {
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
                    oldItems.get(oldItemPosition).getOnCommand().equals(newItems.get(oldItemPosition).getOnCommand()) &&
                    oldItems.get(oldItemPosition).getOffCommand().equals(newItems.get(oldItemPosition).getOffCommand());
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            return super.getChangePayload(oldItemPosition, newItemPosition);
        }
    }
}

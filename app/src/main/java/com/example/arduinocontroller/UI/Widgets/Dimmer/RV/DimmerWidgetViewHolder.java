package com.example.arduinocontroller.UI.Widgets.Dimmer.RV;


import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;
import com.example.arduinocontroller.DB.Model.DimmerWidgetItem;
import com.example.arduinocontroller.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.xw.repo.BubbleSeekBar;

public class DimmerWidgetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, BubbleSeekBar.OnProgressChangedListener {
    private final TextView itemNameView;
    private final TextView itemCommandTv;
    private final TextView itemSeekbarText;
    private final BubbleSeekBar itemSeekbar;
    private final ImageView itemOptionsView;

    OnWidgetDimmerListener onWidgetDimmerListener;

    public DimmerWidgetViewHolder(View itemView, OnWidgetDimmerListener onWidgetDimmerListener) {
        super(itemView);

        itemNameView = itemView.findViewById(R.id.tv_name_list_widget_dimmer);
        itemCommandTv = itemView.findViewById(R.id.tv_command_list_widget_dimmer);
        itemSeekbarText = itemView.findViewById(R.id.tv_seekbar_list_widget_dimmer);
        itemSeekbar = itemView.findViewById(R.id.seekbar_list_widget_dimmer);
        itemOptionsView = itemView.findViewById(R.id.iv_options_list_widget_dimmer);

        this.onWidgetDimmerListener = onWidgetDimmerListener;
    }

    public void bind(DimmerWidgetItem item) {
        itemNameView.setText(item.getText());
        itemCommandTv.setText(item.getCommand());
        itemSeekbarText.setText(String.valueOf(item.getProgress()));
        itemSeekbar.setProgress(item.getProgress());

        itemSeekbar.setOnProgressChangedListener(this);
        itemView.setOnClickListener(this);
        itemOptionsView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == itemOptionsView)
            onWidgetDimmerListener.onOptionsClick(getAdapterPosition());
        else
            onWidgetDimmerListener.onItemClick(getAdapterPosition());


    }

    @Override
    public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
        itemSeekbarText.setText(String.valueOf(progress));
        //todo send signal
    }

    @Override
    public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
        onWidgetDimmerListener.onProgressChanged(getAdapterPosition(), progress);
    }

    @Override
    public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
    }


    public interface OnWidgetDimmerListener {
        void onItemClick(int position);

        void onProgressChanged(int position, int progress);

        void onOptionsClick(int position);
    }

}

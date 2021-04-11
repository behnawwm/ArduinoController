package com.example.arduinocontroller.UI.CommandWidgets;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;
import com.example.arduinocontroller.R;

public class CommandWidgetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    OnCommandItemClickListener onCommandItemClickListener;
    public ImageView mImageView;
    public TextView mTextView;

    public CommandWidgetViewHolder(View itemView, OnCommandItemClickListener listener) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.iv_cmnd);
        mTextView = itemView.findViewById(R.id.tv_cmnd);

        this.onCommandItemClickListener = listener;
    }

    public void bind(CommandWidgetItem item) {
        mImageView.setImageResource(item.getImageResource());
        mTextView.setText(item.getText1());
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onCommandItemClickListener.onItemClick(getAdapterPosition());
    }

    public interface OnCommandItemClickListener {
        void onItemClick(int position);
    }
}

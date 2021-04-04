package com.example.arduinocontroller.CommandWidgets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.arduinocontroller.R;

import java.util.ArrayList;

public class CommandWidgetAdapter extends RecyclerView.Adapter<CommandWidgetAdapter.CommandWidgetViewHolder> {

    private ArrayList<CommandWidgetItem> mList;
    private OnItemClickListener mListener;

    public CommandWidgetAdapter(ArrayList<CommandWidgetItem> exampleList) {
        mList = exampleList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class CommandWidgetViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;

        public CommandWidgetViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_cmnd);
            mTextView1 = itemView.findViewById(R.id.tv_cmnd);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }

    }

    @Override
    public CommandWidgetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_widget_command, parent, false);
        CommandWidgetViewHolder evh = new CommandWidgetViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(CommandWidgetViewHolder holder, int position) {
        CommandWidgetItem currentItem = mList.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}

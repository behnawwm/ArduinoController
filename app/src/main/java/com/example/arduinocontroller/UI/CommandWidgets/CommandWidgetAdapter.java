package com.example.arduinocontroller.UI.CommandWidgets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.arduinocontroller.R;

import java.util.ArrayList;

public class CommandWidgetAdapter extends RecyclerView.Adapter<CommandWidgetViewHolder> {
    private CommandWidgetViewHolder.OnCommandItemClickListener onCommandItemClickListener;

    private ArrayList<CommandWidgetItem> mList;

    public CommandWidgetAdapter(ArrayList<CommandWidgetItem> exampleList, CommandWidgetViewHolder.OnCommandItemClickListener listener) {
        mList = exampleList;
        this.onCommandItemClickListener = listener;
    }

    @Override
    public CommandWidgetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_widget_command, parent, false);
        CommandWidgetViewHolder evh = new CommandWidgetViewHolder(v, onCommandItemClickListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(CommandWidgetViewHolder holder, int position) {
        CommandWidgetItem currentItem = mList.get(position);
        holder.bind(currentItem);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}

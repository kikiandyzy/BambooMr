package com.example.bamboomr.Daily;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bamboomr.R;


import java.util.List;

public class DailyItemAdapter extends RecyclerView.Adapter<DailyItemAdapter.ViewHolder>{
    private List<DailyItem> dailyItemList;
    public DailyItemAdapter(List<DailyItem> dailyItemList){
        this.dailyItemList = dailyItemList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DailyItem dailyItem = dailyItemList.get(position);
        holder.taskName.setText(dailyItem.getTask_name());
        holder.time.setText(dailyItem.getTime());
        holder.duration.setText(dailyItem.getDuration());
    }

    @Override
    public int getItemCount() {
        return dailyItemList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView time;
        TextView taskName;
        TextView duration;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time_daily_item);
            taskName = itemView.findViewById(R.id.task_daily_item);
            duration = itemView.findViewById(R.id.duration_day_item);
        }
    }
}

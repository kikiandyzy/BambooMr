package com.example.bamboomr.Daily;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bamboomr.DailyActivity;
import com.example.bamboomr.R;
import com.example.bamboomr.house.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{
    private List<Task> taskList;

    public TaskAdapter(List<Task> taskList,AddCostomListener addCostomListener){
        this.taskList = taskList;
        this.addCostomListener = addCostomListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_custom_task_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addCostomListener.add(viewHolder.position)){
                    Toast.makeText(DailyActivity.context, "添加成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DailyActivity.context, "没有成功", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.position = position;
        holder.taskName.setText(task.getTask_name());
        holder.taskTime.setText(""+task.getDuration()+"min");

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView taskName;
        TextView taskTime;
        ImageView imageView;
        int position;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.dialog_task_name);
            taskTime = itemView.findViewById(R.id.dialog_task_time);
            imageView = itemView.findViewById(R.id.dialog_task_add);

        }

    }

    public interface AddCostomListener{
        boolean add(int position);
    }

    private AddCostomListener addCostomListener;
}

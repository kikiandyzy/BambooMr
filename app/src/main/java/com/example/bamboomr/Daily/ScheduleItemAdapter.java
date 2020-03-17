package com.example.bamboomr.Daily;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bamboomr.DailyActivity;
import com.example.bamboomr.R;

import java.util.List;


public class ScheduleItemAdapter extends RecyclerView.Adapter<ScheduleItemAdapter.ViewHolder>{
    private Context context;
    private List<ScheduleItem> days;
    public static boolean if_month = false;//是不是本月
    public static int position_day = 0;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.schedule_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);


        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                if(!days.get(position).getDate().equals("0-0-0")){
                    //if(if_month && position_day == position)
                    //Toast.makeText(context, days.get(position).getDate(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, DailyActivity.class);
                    intent.putExtra(String.valueOf(R.string.date),days.get(position).getDate());
                    context.startActivity(intent);

                }

            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String day;
        if(days.get(position).getDay() == 0){
            day = days.get(position).getWeek();
        }else {
            day = ""+days.get(position).getDay();
        }

        holder.textView.setText(day);
        if(if_month && position_day == position){
            //holder.textView.setTextColor(Color.YELLOW);
            holder.imageView.setImageResource(R.drawable.ic_radio_button_unchecked_black_40dp);
            if_month = false;
            position_day = 0;
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView)itemView;
            textView = itemView.findViewById(R.id.day_schedule_item);
            imageView = itemView.findViewById(R.id.circle);
        }

    }

    public ScheduleItemAdapter(List<ScheduleItem> days){
        this.days = days;
    }

}

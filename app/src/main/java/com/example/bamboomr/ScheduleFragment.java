package com.example.bamboomr;




import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bamboomr.Daily.ScheduleItem;
import com.example.bamboomr.Daily.ScheduleItemAdapter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment{

    private RecyclerView recyclerView;
    //显示日期
    private TextView textView_date;
    private Context context;
    private static int[] black_of_day = {0,1,2,3,4,5,6};
    private static String[] DAY_OF_WEEK = {"日","一","二","三","四","五","六"};
    private List<ScheduleItem> days = new ArrayList<>();
    private Calendar calendar = Calendar.getInstance();
    private int year;
    private int month;
    private int day;
    private ImageView left;
    private ImageView right;
    ScheduleItemAdapter scheduleItemAdapter;

    public ScheduleFragment(Context context) {
        // Required empty public constructor
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_schedule);
        textView_date = view.findViewById(R.id.date_schedule_fragment);
        //一开始将本月的信息记录下来
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);//注意一月份返回值为0
        day = calendar.get(Calendar.DAY_OF_MONTH);

        //一开始初始化
        initDays(0);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 7);
        recyclerView.setLayoutManager(layoutManager);
        scheduleItemAdapter = new ScheduleItemAdapter(days);
        recyclerView.setAdapter(scheduleItemAdapter);
        left = view.findViewById(R.id.left_button_schedule);
        right = view.findViewById(R.id.right_button_schedule);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDays(-1);//减一个月
                scheduleItemAdapter.notifyDataSetChanged();
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDays(1);//加一个月
                scheduleItemAdapter.notifyDataSetChanged();
            }
        });

        return view;


    }


    //这个函数配置要显示的days
    private void initDays(int add){
        days.clear();
        //第一行先配置上日期
        for(int i=0;i<DAY_OF_WEEK.length;i++){
            days.add(new ScheduleItem(DAY_OF_WEEK[i]));
        }
        //加上相应的月份调整，+1为加，-1为剪，0为本月
        calendar.add(Calendar.MONTH,add);
        //现在开始判断是不是本月，是的话在当天特殊标记
        boolean now = false;
        if(add == 0 ){
            now = true;
        }else if(year == calendar.get(Calendar.YEAR) && month == calendar.get(Calendar.MONTH)){
            now = true;
        }

        //将日历类设置为这个月的第一天
        calendar.set(Calendar.DAY_OF_MONTH,1);
        //获取需要空出来的空格
        int black = black_of_day[calendar.get(Calendar.DAY_OF_WEEK)-1];
        for(int i=0;i<black;i++){
            days.add(new ScheduleItem(""));
        }
        //获取本月的天数
        int count = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH)+1;
        for(int i=0;i<count;i++){
            days.add(new ScheduleItem(mYear,mMonth,i+1));
        }
        //如果是当前本月份
        if(now){
            //今天的位置
            int position = 7 + black + day - 1;
            ScheduleItemAdapter.if_month = true;
            ScheduleItemAdapter.position_day = position;

        }
        textView_date.setText(""+calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1));



    }










}

package com.example.bamboomr;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bamboomr.Daily.DailyItem;
import com.example.bamboomr.house.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class FocusFragment extends Fragment implements View.OnClickListener{
    private TextView time;
    private int second = 0;
    private int min =0;
    private Timer timer = null;
    private TimerTask task = null;
    private int start=0;
    private TextView title;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.fragment_focus, container, false);
        time=messageLayout.findViewById(R.id.time);
        title = messageLayout.findViewById(R.id.title_focus);
        time.setOnClickListener(this);
        //长按停止
        time.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                stopTime();
                start=0;
                time.setText("00"+":"+"00");
                second=0;
                min=0;
                return false;
            }
        });


        return messageLayout;
    }

    //倒计时功能
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String s;
            String m;
            if(second<10)
                s="0"+second;
            else
                s=""+second;
            if(min<10)
                m="0"+min;
            else
                m=""+min;
            time.setText(m+":"+s);
            recordTime();
            startTime();
        };
    };

    public void startTime() {
        timer = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {//加入判断不能小于0
                second++;
                if(second==60)
                {
                    min++;
                    second=0;
                }
                Message message = mHandler.obtainMessage();
                mHandler.sendMessage(message);

            }
        };
        timer.schedule(task, 1000);
    }

    public void stopTime(){
        timer.cancel();
    }

    //点击一下开始，再点一下结束
    @Override
    public void onClick(View view) {
        //如果活动开始确认
        if(MainActivity.CONFIRMTASK){
            if(start==0) {
                startTime();
                start=1;

                try {
                    //点击一次开始计时
                    startTime = DailyItem.simpleDateFormat.parse(""+ Calendar.HOUR_OF_DAY+":"+Calendar.MINUTE);


                } catch (ParseException e) {
                    e.printStackTrace();
                }



            }
            else {
                startTime = null;
                start=0;
                stopTime();
            }
        }else {
            Toast.makeText(MainActivity.context, "请在schedule页面确认今天任务", Toast.LENGTH_SHORT).show();
        }

    }

    //添加上一个参数用于尝试


    private Date startTime = null;//这个记录了点下计时按钮的那一时刻的时间

    private void recordTime(){
        if(startTime != null){
            for(int i=0;i<MainActivity.dailyItemList.size();i++){
                Task task = null;
                //首先判断有没有任务
                if((task = MainActivity.dailyItemList.get(i).getTask()) != null){
                    //其次判断在不在计时区间
                    if(MainActivity.dailyItemList.get(i).getDate().getTime() > startTime.getTime()){
                        //最后判断计时有没有开始
                        Date date = new Date();
                        try {
                            date = DailyItem.simpleDateFormat.parse(""+ Calendar.HOUR_OF_DAY+":"+Calendar.MINUTE);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(MainActivity.dailyItemList.get(i).getDate().getTime() < date.getTime()){
                            //加一秒
                            MainActivity.dailyItemList.get(i).getTask().recordDuration++;
                            title.setText(MainActivity.dailyItemList.get(i).getTask_name());
                        }
                    }
                }
            }
        }

    }







}

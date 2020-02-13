package com.example.bamboomr;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.fragment_focus, container, false);
        time=messageLayout.findViewById(R.id.time);
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
        if(start==0) {
            startTime();
            start=1;
        }
        else {
            start=0;
            stopTime();
        }
    }
}

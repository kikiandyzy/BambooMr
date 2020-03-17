package com.example.bamboomr;
/*
第一步，写底部的导航按钮的布局bottom.xml
* */
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.bamboomr.Daily.DailyItem;
import com.example.bamboomr.Daily.DailyTaskDatabaseHelper;
import com.example.bamboomr.house.Task;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ViewPager mViewPager;
    private List<Fragment> mViews = new ArrayList<Fragment>();
    private MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(),mViews);
    private RelativeLayout create,focus,schedule;
    public static boolean CONFIRMTASK = false;
    public static List<DailyItem> dailyItemList = new ArrayList<>();
    public static Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        init();
        Toast.makeText(this, "请在schedule页面确认今天任务", Toast.LENGTH_SHORT).show();
    }
    private void init(){
        //赋值
        mViewPager = findViewById(R.id.viewpager);
        create =  findViewById(R.id.create_button);
        focus = findViewById(R.id.focus_button);
        schedule = findViewById(R.id.schedule_button);

        create.setOnClickListener(this);
        focus.setOnClickListener(this);
        schedule.setOnClickListener(this);

        mViews.add(new CreateFragment());
        mViews.add(new FocusFragment());
        mViews.add(new ScheduleFragment(this));
        mViewPager.setAdapter(adapter);

    }

    //View.OnClickListene接口响应函数
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create_button:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.focus_button:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.schedule_button:
                mViewPager.setCurrentItem(2);
                break;
        }
    }





    private void updateDatebase(){
        DailyTaskDatabaseHelper dailyTaskDatabaseHelper = new DailyTaskDatabaseHelper(this,DailyTaskDatabaseHelper.DATEBASE_NAME,null,1);
        SQLiteDatabase db = dailyTaskDatabaseHelper.getWritableDatabase();
        List<Task> tasks = new ArrayList<>();
        for(int i=0;i<dailyItemList.size();i++){
            if(dailyItemList.get(i).getTask() != null){
                tasks.add(dailyItemList.get(i).getTask());
            }
        }
        for(int i=0;i<tasks.size();i++){
            db.execSQL("update task set record_duration = ? where id = ? and date = ?",new String[]{
                    ""+tasks.get(i).getRecordDuration(),tasks.get(i).getId(),tasks.get(i).getDate()
            });

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        updateDatebase();
    }
}

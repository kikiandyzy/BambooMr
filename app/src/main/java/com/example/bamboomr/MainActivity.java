package com.example.bamboomr;
/*
第一步，写底部的导航按钮的布局bottom.xml
* */
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;



import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ViewPager mViewPager;
    private List<Fragment> mViews = new ArrayList<Fragment>();
    private MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(),mViews);
    private RelativeLayout create,focus,schedule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();




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
        mViews.add(new ScheduleFragment(MainActivity.this,getSupportFragmentManager()));
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



}

package com.example.bamboomr;


import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.bamboomr.house.house;
import com.example.bamboomr.house.house_serve;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.forward.androids.views.BitmapScrollPicker;
import cn.forward.androids.views.ScrollPickerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateFragment extends Fragment {

    static public house_serve serve;
    static public ArrayList<house> houses;
    static public int wei=-1;
    private ImageView ge;
    private int choice=1;
    private Float x=0f;
    private Float y=0f;
    public CreateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View messageLayout = inflater.inflate(R.layout.fragment_create, container, false);
        init(messageLayout);

        Bitmap bitmap= ((BitmapDrawable)getResources().getDrawable(R.drawable.house1)).getBitmap();//将资源文件转化为bitmap
        Bitmap bitmap2 = ((BitmapDrawable)getResources().getDrawable(R.drawable.house2)).getBitmap();//将资源文件转化为bitmap
        Bitmap bitmap3=((BitmapDrawable)getResources().getDrawable(R.drawable.house3)).getBitmap();//将资源文件转化为bitmap
        List<Bitmap> bitmaps= Arrays.asList(new Bitmap[]{bitmap, bitmap2, bitmap3});
        final BitmapScrollPicker picker = messageLayout.findViewById(R.id.picker);

        //弹出房子选择
        picker.setData(bitmaps);
        picker.setOnSelectedListener(new ScrollPickerView.OnSelectedListener() {
            @Override
            public void onSelected(ScrollPickerView scrollPickerView, int position) {
                //Toast.makeText(getActivity(), "选择的城市为：" + position, Toast.LENGTH_SHORT).show();
                choice=position;
            }
        });
        final RelativeLayout relativeLayout = (RelativeLayout) messageLayout.findViewById(R.id.relativeLayout);
        picker.setOnClickListener(new ScrollPickerView.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过代码来创建一个房子
                ImageView imageView = new ImageView(messageLayout.getContext());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(300, 300) );//设置图片宽高
                int[] house={R.drawable.house1,R.drawable.house2,R.drawable.house3};
                imageView.setImageDrawable(getResources().getDrawable(house[choice]));  //图片资源
                imageView.setX(x-120);
                imageView.setY(y);
                imageView.setId(houses.size());
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //发送房子的id识别房子
                        wei=v.getId();
                        wei=v.getId();
                        Intent intent = new Intent(getContext(), addActivity.class);
                        //startActivityForResult(intent, 111);
                        startActivity(intent);
                    }
                });
                houses.add(new house(x-120,y,choice,houses.size()));
                serve.save();
                relativeLayout.addView(imageView); //动态添加图片
                picker.setVisibility(View.INVISIBLE);
            }
        });
        ge=(ImageView) messageLayout.findViewById(R.id.ge);
        ge.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                picker.bringToFront();
                x=event.getX();
                y=event.getY();
                picker.setVisibility(View.VISIBLE);
                return false;
            }
        });
        return messageLayout;
    }

    private void init(View messageLayout) {
        serve=new house_serve(messageLayout.getContext());
        houses=serve.load();
        if(houses.size()!=0)
        {
            /*Toast.makeText(messageLayout.getContext(), "我来自其他线程！",
                           Toast.LENGTH_SHORT).show();*/
            for(int i=0;i<houses.size();i++)
            {
                final RelativeLayout relativeLayout = (RelativeLayout) messageLayout.findViewById(R.id.relativeLayout);
                ImageView imageView = new ImageView(messageLayout.getContext());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(300, 300) );//设置图片宽高
                int[] house={R.drawable.house1,R.drawable.house2,R.drawable.house3};
                imageView.setImageDrawable(getResources().getDrawable(house[houses.get(i).getPicture()]));  //图片资源
                imageView.setX(houses.get(i).getX());
                imageView.setY(houses.get(i).getY());
                imageView.setId(houses.get(i).getId());
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        wei=v.getId();
                        Intent intent = new Intent(getContext(), addActivity.class);
                        //startActivityForResult(intent, 111);
                        startActivity(intent);
                    }
                });

                relativeLayout.addView(imageView); //动态添加图片
            }
        }
    }

}

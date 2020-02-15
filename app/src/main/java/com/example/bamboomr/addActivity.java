package com.example.bamboomr;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.example.bamboomr.house.Aim;
import com.example.bamboomr.house.Phase;
import com.example.bamboomr.house.Task;
import com.example.bamboomr.house.house;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class addActivity extends AppCompatActivity  implements View.OnClickListener,  AdapterView.OnItemClickListener,DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private EditText ambition;
    private ImageView cancel;
    private ImageView finish;
    private ImageView everyday_jia;
    private ImageView everyday_jian;
    private ImageView ziding_jia;
    private ImageView ziding_jian;
    private View messageLayout;
    private TextView start;
    private TextView end;
    private View xian;
    private View xian3;
    private TextView time;
    private int position=-1;
    private Context context;

    //thseset2用来存放第一个listview的数据
    private ArrayList<Task> theset2;
    private GoodsArrayAdapter2 theAdaper2;
    //thseset3用来存放第一个listview的数据
    private ArrayList<Task> theset3;
    private GoodsArrayAdapter2 theAdaper3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        context=this;
        //各数据的绑定
        cancel=findViewById(R.id.cancel);
        finish=findViewById(R.id.finish);
        everyday_jia=findViewById(R.id.everyday_jia);
        everyday_jian=findViewById(R.id.everyday_jian);
        ziding_jia=findViewById(R.id.ziding_jia);
        ziding_jian=findViewById(R.id.ziding_jian);
        start=findViewById(R.id.start);
        end=findViewById(R.id.end);
        xian=findViewById(R.id.xian2);
        xian3=findViewById(R.id.xian3);
        time=findViewById(R.id.time);
        ambition=findViewById(R.id.ambitions);

        cancel.setOnClickListener(this);
        finish.setOnClickListener(this);
        everyday_jia.setOnClickListener(this);
        everyday_jian.setOnClickListener(this);
        ziding_jia.setOnClickListener(this);
        ziding_jian.setOnClickListener(this);
        start.setOnClickListener(this);
        end.setOnClickListener(this);

        //数据初始化
        InitData();
        //listview数据绑定
        theAdaper2 = new GoodsArrayAdapter2(this, R.layout.list_item_set_2, theset2);
        ListView listViewSuper2 = (ListView) findViewById(R.id.evert_listview);
        listViewSuper2.setAdapter(theAdaper2);
        listViewSuper2.setOnItemClickListener(this);

        theAdaper3 = new GoodsArrayAdapter2(this, R.layout.list_item_set_2, theset3);
        ListView listViewSuper3 = (ListView) findViewById(R.id.ziding_listview);
        listViewSuper3.setAdapter(theAdaper3);
        listViewSuper3.setOnItemClickListener(this);
    }

    //数据初始化
    private void InitData() {
        //使用creatfragment的全局变量获取数据
        house chu=  CreateFragment.houses.get(CreateFragment.wei);
        //通过空判定房子是否含有数据
        if(!chu.isEmpty()) {
            Aim aim=chu.getAim();
            ambition.setText(aim.getBig_aim());
            start.setText(aim.getStart_time());
            end.setText(aim.getEnd_time());
            ArrayList<Phase> phases=aim.getPhase();
            ArrayList<Task> every_tasts=phases.get(0).getEvery_tast();
            theset2 = new ArrayList<Task>();
            for(int i=0;i<every_tasts.size();i++)
            {
                theset2.add(every_tasts.get(i));
            }
            ArrayList<Task> myself_tasts=phases.get(0).getMyself_tast();
            theset3 = new ArrayList<Task>();
            for(int i=0;i<myself_tasts.size();i++)
            {
                theset3.add(myself_tasts.get(i));
            }
            set_everydat_time();
        }
        else {
            //如果没有数据就添加数据
            theset2 = new ArrayList<Task>();
            theset2.add(new Task("空", -1,-1));
            theset3 = new ArrayList<Task>();
            theset3.add(new Task("空", -1,-1));
        }
    }
    public void onClick(View view) {
        switch (view.getId()) {
            //设置开始和结束的日期
            case R.id.start:
            case R.id.end:
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
                break;
            case R.id.cancel:
                //点击返回不进行保存进行返回
                /*FragmentManager fm = getActivity().getFragmentManager();
                //注意v4包的配套使用0
                CreateFragment fragment = new CreateFragment();
                fm.beginTransaction().replace(R.id.content,fragment).commit();*/
                this.finish();

                break;
            case R.id.finish:
                /*FragmentManager fm1 = getActivity().getFragmentManager();
                //注意v4包的配套使用
                ScheduleFragment fragment1 = new ScheduleFragment();
                fm1.beginTransaction().replace(R.id.content,fragment1).commit();*/

                //进行信息保存然后返回
                house gai=  CreateFragment.houses.get(CreateFragment.wei);
                ArrayList<Phase> phases=new ArrayList<Phase>();
                Phase phase=new Phase();
                phase.setEvery_tast(theset2);
                phase.setMyself_tast(theset3);
                phases.add(phase);
                Aim aim=new Aim();
                aim.setPhase(phases);
                aim.setStart_time(start.getText().toString());
                aim.setEnd_time(end.getText().toString());
                aim.setBig_aim(ambition.getText().toString());
                gai.setAim(aim);
                gai.setEmpty(false);
                CreateFragment.serve.save();
                this.finish();
                /*FragmentManager fm1 = getActivity().getFragmentManager();
                //注意v4包的配套使用0
                CreateFragment fragment1 = new CreateFragment();
                fm1.beginTransaction().replace(R.id.content,fragment1).commit();*/
                break;
            case R.id.everyday_jian:
                //每日任务的删除，通过弹窗填信息来进行删除对应行
                final EditText inputServer = new EditText(this);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("要删除哪一行").setView(inputServer)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String text = inputServer.getText().toString();
                        try {
                            //抓取转换数字的错误
                            int i = Integer.parseInt(text);
                            if(theset2.size()>i-1)
                                theset2.remove(i-1);
                            else
                                Toast.makeText(getBaseContext(), "没有对应行", Toast.LENGTH_SHORT).show();
                        }
                        catch (NumberFormatException e1)
                        {
                            Toast.makeText(getBaseContext(), "请输入数字", Toast.LENGTH_SHORT).show();
                        }
                        theAdaper2.notifyDataSetChanged();
                        if(theset2.size()==0)
                            //最下面的横线为是自添加，所以要自己判断是否显示
                            xian.setVisibility(View.INVISIBLE);
                    }
                });
                builder.show();
                break;
            case R.id.everyday_jia:
                //点击加号直接添加一个空的
                theset2.add(new Task("空",-1,-1));
                theAdaper2.notifyDataSetChanged();
                if(theset2.size()>0)
                    xian.setVisibility(View.VISIBLE);
                break;
            case R.id.ziding_jian:
                //与每日任务的差不多
                final EditText inputServer3 = new EditText(this);
                AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
                builder3.setTitle("要删除哪一行").setView(inputServer3)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder3.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String text = inputServer3.getText().toString();
                        try {
                            int i = Integer.parseInt(text);
                            if(theset3.size()>i-1)
                                theset3.remove(i-1);
                            else
                                Toast.makeText(getBaseContext(), "没有对应行", Toast.LENGTH_SHORT).show();
                        }
                        catch (NumberFormatException e1)
                        {
                            Toast.makeText(getBaseContext(), "请输入数字", Toast.LENGTH_SHORT).show();
                        }
                        theAdaper3.notifyDataSetChanged();
                        if(theset3.size()==0)
                            xian3.setVisibility(View.INVISIBLE);
                    }
                });
                builder3.show();
                break;
            case R.id.ziding_jia:
                //与每日任务的差不多
                theset3.add(new Task("空",-1,-1));
                theAdaper3.notifyDataSetChanged();
                if(theset3.size()>0)
                    xian3.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        //设置日期
        String start_time=monthOfYear+1+"月"+dayOfMonth+"日";
        String end_time=monthOfYearEnd+1+"月"+dayOfMonthEnd+"日";
        start.setText(start_time);
        end.setText(end_time);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int positions, long id) {
        switch (adapterView.getId()) {

            case R.id.evert_listview:
                //设置位置
                position = positions;
        /*Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.show(getFragmentManager(), "Timepickerdialog");*/

                //通过弹窗的信息进行修改，通过两次弹窗来进行修改，//////////////////////////之间为第二次弹窗
                final EditText inputServer = new EditText(this);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("输入目标名").setView(inputServer)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String text1 = inputServer.getText().toString();
                        if(text1.isEmpty())
                            Toast.makeText(getBaseContext(), "目标不能为空", Toast.LENGTH_SHORT).show();
                        else {
                            //////////////////////////
                            final EditText inputServer = new EditText(context);
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("输入每日所用时间（单位：分钟）").setView(inputServer)
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String text2 = inputServer.getText().toString();
                                    try {
                                        int i = Integer.parseInt(text2);
                                        Task gai = theset2.get(position);
                                        gai.setTast_name(text1);
                                        gai.setDuration(i);
                                        gai.setCycle(-1);
                                    } catch (NumberFormatException e1) {
                                        Toast.makeText(getBaseContext(), "请输入数字", Toast.LENGTH_SHORT).show();
                                    }
                                    theAdaper2.notifyDataSetChanged();
                                    set_everydat_time();

                                }
                            });
                            builder.show();
                            ////////////////////////////
                        }
                    }
                });
                builder.show();
                break;
            case R.id.ziding_listview:
                //通过弹窗的信息进行修改，通过三次弹窗来进行修改，//////////////////////////之间为间隔另一个弹窗
                position = positions;
                final EditText inputServer3 = new EditText(this);
                AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
                builder3.setTitle("输入目标名").setView(inputServer3)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder3.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String text1 = inputServer3.getText().toString();
                        if(text1.isEmpty())
                            Toast.makeText(getBaseContext(), "目标不能为空", Toast.LENGTH_SHORT).show();
                        else {
                            //////////////////////////
                            final EditText inputServer = new EditText(context);
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("输入所用天数").setView(inputServer)
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String text2 = inputServer.getText().toString();
                                    try {
                                        int w = Integer.parseInt(text2);
                                        //////////////////////////
                                        final EditText inputServer = new EditText(context);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setTitle("输入总时长(单位：分钟)").setView(inputServer)
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                String text3 = inputServer.getText().toString();
                                                try {
                                                    int i = Integer.parseInt(text3);
                                                    int j = Integer.parseInt(text2);
                                                    Task gai = theset3.get(position);
                                                    gai.setTast_name(text1);
                                                    gai.setDuration(i);
                                                    gai.setCycle(j);
                                                } catch (NumberFormatException e1) {
                                                    Toast.makeText(getBaseContext(), "请输入数字", Toast.LENGTH_SHORT).show();
                                                }
                                                theAdaper3.notifyDataSetChanged();
                                                set_everydat_time();

                                            }
                                        });
                                        builder.show();
                                        ////////////////////////////
                                    }
                                    catch (NumberFormatException e1)
                                    {
                                        Toast.makeText(getBaseContext(), "请输入数字", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                            builder.show();
                            ////////////////////////////
                        }
                    }
                });
                builder3.show();
                break;
        }

    }

    //设置平均每日所花时间
    public void set_everydat_time()
    {
        double times = 0;
        for (int i = 0; i < theset2.size(); i++) {
            if(theset2.get(i).getDuration()>=0)
                times += theset2.get(i).getDuration();
        }
        for (int i = 0; i < theset3.size(); i++) {
            if(theset3.get(i).getDuration()>=0)
                times += theset3.get(i).getDuration()/theset3.get(i).getCycle();
        }
        DecimalFormat df = new DecimalFormat("0.00");
        time.setText(df.format(times/60) + "h/天");
    }

    //进行小时分钟时间选定的
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        /*set_kind_2 gai=theset2.get(position);
        gai.setTwo(hourOfDay+":"+minute+"-"+hourOfDayEnd+":"+minuteEnd);
        gai.setCha(hourOfDayEnd-hourOfDay+(double)(minuteEnd-minute)/60);
        theAdaper2.notifyDataSetChanged();
        double times=0;
        for(int i=0;i<theset2.size();i++)
        {
            times+=theset2.get(i).getCha();
        }
        DecimalFormat df = new DecimalFormat("0.00");
        time.setText(df.format(times)+"h/天");*/

    }


    //listview的所使用的类
    class GoodsArrayAdapter2 extends ArrayAdapter<Task> {
        private int resourceId;

        public GoodsArrayAdapter2(@NonNull Context context, @LayoutRes int resource, @NonNull List<Task> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater mInflater = LayoutInflater.from(this.getContext());
            View item = mInflater.inflate(this.resourceId, null);

            TextView string1 = (TextView) item.findViewById(R.id.text_view_string1);
            TextView string2 = (TextView) item.findViewById(R.id.text_view_string2);

            Task item1 = this.getItem(position);
            string1.setText(item1.getTast_name());
            if(item1.getDuration()==-1)
                string2.setText("点击设置目标与时间");
            else if(item1.getCycle()==-1)
                string2.setText("每天"+item1.getDuration()+"分钟");
            else
                string2.setText(item1.getCycle()+"天共"+item1.getDuration()+"分钟");

            return item;
        }
    }
}

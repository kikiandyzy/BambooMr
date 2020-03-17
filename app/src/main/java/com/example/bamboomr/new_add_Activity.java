package com.example.bamboomr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.example.bamboomr.house.Aim;
import com.example.bamboomr.house.Phase;
import com.example.bamboomr.house.house;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class new_add_Activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener,DatePickerDialog.OnDateSetListener {

    private RecyclerView recyclerView;
    private ArrayList<Phase> mDatas;
    private MyRecyclerAdapter recycleAdapter;
    private EditText ambition;
    private ImageView cancel;
    private ImageView finish;
    private TextView start;
    private TextView end;
    private ImageView jia;
    private ImageView jian;
    private Date start_data=null;
    private Date end_data=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_add_);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView );

        jia=findViewById(R.id.jia);
        jian=findViewById(R.id.jian);
        cancel=findViewById(R.id.cancel);
        finish=findViewById(R.id.finish);
        start=findViewById(R.id.start);
        end=findViewById(R.id.end);
        ambition=findViewById(R.id.ambitions);

        jia.setOnClickListener(this);
        jian.setOnClickListener(this);
        cancel.setOnClickListener(this);
        finish.setOnClickListener(this);
        start.setOnClickListener(this);
        end.setOnClickListener(this);

        initData();
        recycleAdapter= new MyRecyclerAdapter(this , mDatas );
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        //设置Adapter
        recyclerView.setAdapter( recycleAdapter);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void initData() {
        house chu=  CreateFragment.houses.get(CreateFragment.wei);
        /*ambition.setText(aim.getBig_aim());
        start.setText(aim.getStart_time());
        end.setText(aim.getEnd_time());*/
        mDatas=new ArrayList<Phase>();
        if(!chu.isEmpty()) {
            Aim aim=chu.getAim();
            mDatas = aim.getPhase();
            if(!aim.getBig_aim().isEmpty())
                ambition.setText(aim.getBig_aim());
            if(aim.getStart_time()!=null) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
                start_data=aim.getStart_time();
                start.setText(sdf.format(aim.getStart_time()));

            }
            if(aim.getEnd_time()!=null) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
                end_data=aim.getEnd_time();
                end.setText(sdf.format(aim.getEnd_time()));

            }
        }
        else
        {
            Phase phase=new Phase();
            mDatas.add(phase);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.jia:
                recycleAdapter.addData();
                break;
            case R.id.jian:
                final EditText inputServer = new EditText(this);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("要删除第几个阶段").setView(inputServer)
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
                            house chu=  CreateFragment.houses.get(CreateFragment.wei);
                            if(chu.getAim().getPhase().size()>i-1)
                                recycleAdapter.moveData(i-1);
                            else
                                Toast.makeText(getBaseContext(), "没有对应阶段", Toast.LENGTH_SHORT).show();
                        }
                        catch (NumberFormatException e1)
                        {
                            Toast.makeText(getBaseContext(), "请输入数字", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
                break;
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
            case R.id.finish:
                if(ambition.getText().toString().isEmpty())
                    Toast.makeText(getBaseContext(), "请输入大目标", Toast.LENGTH_SHORT).show();
                else if(start.getText().toString().isEmpty())
                    Toast.makeText(getBaseContext(), "请输入开始和结束时间", Toast.LENGTH_SHORT).show();
                else {
                    house chu = CreateFragment.houses.get(CreateFragment.wei);
                    ArrayList<Phase> data=recycleAdapter.getmDatas();
                    boolean right=true;
                    for(int i=0;i<data.size();i++)
                    {
                        if(data.get(i).getStart_time()==null)
                        {
                            Toast.makeText(getBaseContext(), "第"+(i+1)+"阶段没有设置时间", Toast.LENGTH_SHORT).show();
                            right=false;
                            break;
                        }
                    }
                    if(right==true) {
                        chu.setEmpty(false);
                        Aim aim = new Aim();
                        aim.setBig_aim(ambition.getText().toString());
                        aim.setStart_time(start_data);
                        aim.setEnd_time(end_data);
                        aim.setPhase(recycleAdapter.getmDatas());
                        chu.setAim(aim);
                        CreateFragment.serve.save();
                        this.finish();
                    }
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        /*SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        Calendar start_time = new GregorianCalendar();
        start_time.set(Calendar.YEAR,year);
        start_time.set(Calendar.MONTH,monthOfYear);
        start_time.set(Calendar.DATE,dayOfMonth);
        Date date = start_time.getTime();
        Calendar end_time = new GregorianCalendar();
        end_time.set(Calendar.YEAR,yearEnd);
        end_time.set(Calendar.MONTH,monthOfYearEnd);
        end_time.set(Calendar.DATE,dayOfMonthEnd);
        Date date2 = end_time.getTime();*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date date2 = null;
        try {
            date = sdf.parse(""+year+"-"+(monthOfYear)+"-"+dayOfMonth);

            date2 = sdf.parse(""+yearEnd+"-"+(monthOfYearEnd)+"-"+dayOfMonthEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }




        if(date.compareTo(date2)!=-1)
            Toast.makeText(getBaseContext(), "结束时间不可小于开始时间", Toast.LENGTH_SHORT).show();
        else {
            ///start.setText(sdf.format(date));
            //end.setText(sdf.format(date2));
            start_data = date;
            end_data = date2;
            house chu = CreateFragment.houses.get(CreateFragment.wei);
            chu.setEmpty(false);
            Aim aim = new Aim();
            aim.setBig_aim(ambition.getText().toString());
            aim.setStart_time(start_data);
            aim.setEnd_time(end_data);
            aim.setPhase(recycleAdapter.getmDatas());
            chu.setAim(aim);
            CreateFragment.serve.save();
            //新加的
            SimpleDateFormat sdf2 = new SimpleDateFormat("MM月dd日");
            start.setText(sdf2.format(date));
            end.setText(sdf2.format(date2));
        }
    }
}

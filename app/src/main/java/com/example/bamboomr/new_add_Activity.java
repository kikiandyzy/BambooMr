package com.example.bamboomr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.example.bamboomr.house.Aim;
import com.example.bamboomr.house.Phase;
import com.example.bamboomr.house.house;

import java.util.ArrayList;
import java.util.Calendar;
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
            if(!aim.getStart_time().isEmpty())
                start.setText(aim.getStart_time());
            if(!aim.getEnd_time().isEmpty())
                end.setText(aim.getEnd_time());
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
                recycleAdapter.moveData(0);
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
                house chu=  CreateFragment.houses.get(CreateFragment.wei);
                chu.setEmpty(false);
                Aim aim=new Aim();
                aim.setBig_aim(ambition.getText().toString());
                aim.setStart_time(start.getText().toString());
                aim.setEnd_time(end.getText().toString());
                aim.setPhase(recycleAdapter.getmDatas());
                chu.setAim(aim);
                CreateFragment.serve.save();
                this.finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {

        String start_time=monthOfYear+1+"月"+dayOfMonth+"日";
        String end_time=monthOfYearEnd+1+"月"+dayOfMonthEnd+"日";
        start.setText(start_time);
        end.setText(end_time);
    }
}

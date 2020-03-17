package com.example.bamboomr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bamboomr.house.Aim;
import com.example.bamboomr.house.Phase;
import com.example.bamboomr.house.Task;
import com.example.bamboomr.house.house;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private ArrayList<Phase> mDatas;
    private Context mContext;
    private LayoutInflater inflater;


    public ArrayList<Phase> getmDatas() {
        return mDatas;
    }

    public MyRecyclerAdapter(Context context, ArrayList<Phase> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount() {
        if(mDatas!=null)
        return mDatas.size();
        else
            return 1;
    }


    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int positions) {
        holder.duan.setText("阶段"+(positions+1));
        if(mDatas.get(positions).getStart_time()!=null)
            holder.start.setText("        "+((mDatas.get(positions).getEnd_time().getTime() - mDatas.get(positions).getStart_time().getTime()) / (1000 * 60 * 60 * 24))+"天");
        else
            holder.start.setText("点击设置时间长度");
        mDatas.get(positions).setPhase_num(positions+1);
        final ArrayList<Task> theset2=new ArrayList<Task>();
        GoodsArrayAdapter2 theAdaper2;
        //thseset3用来存放第一个listview的数据
        final ArrayList<Task> theset3=new ArrayList<Task>();
        GoodsArrayAdapter2 theAdaper3;
        //final Phase phase;
        if(!mDatas.get(positions).isHave_done()) {
            theset2.add(new Task("空", -1, -1));
            theset3.add(new Task("空", -1, -1));
            holder.du.setText("完成度：0.0%");
        }
        else
        {
            ArrayList<Task> every_tasts=mDatas.get(positions).getEvery_tast();
            for(int i=0;i<every_tasts.size();i++)
            {
                theset2.add(every_tasts.get(i));
            }
            ArrayList<Task> myself_tasts=mDatas.get(positions).getMyself_tast();
            for(int i=0;i<myself_tasts.size();i++)
            {
                theset3.add(myself_tasts.get(i));
            }
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
            holder.time.setText(df.format(times/60) + "h/天");
            holder.du.setText("完成度："+mDatas.get(positions).getFinish_time()/mDatas.get(positions).getAll_time()+"%");
        }
        /*theset2.add(new Task("空", -1, -1));
        theset3.add(new Task("空", -1, -1));*/

        theAdaper2 = new GoodsArrayAdapter2(mContext, R.layout.list_item_set_2, theset2);
        holder.evety_listView.setAdapter(theAdaper2);
        setPullLvHeight(holder.evety_listView);//重置listview高度
        holder.evety_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                final EditText inputServer = new EditText(mContext);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
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
                            Toast.makeText(mContext, "目标不能为空", Toast.LENGTH_SHORT).show();
                        else {
                            //////////////////////////
                            final EditText inputServer = new EditText(mContext);
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
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
                                        gai.setTask_name(text1);
                                        gai.setDuration(i);
                                        gai.setCycle(-1);
                                        ArrayList<Integer> id=new ArrayList<Integer>();
                                        gai.setId(CreateFragment.wei,mDatas.get(positions).getPhase_num(),0,position);
                                    } catch (NumberFormatException e1) {
                                        Toast.makeText(mContext, "请输入数字", Toast.LENGTH_SHORT).show();
                                    }
                                    mDatas.get(positions).setHave_done(true);
                                    mDatas.get(positions).setEvery_tast_empty(false);
                                    mDatas.get(positions).setEvery_tast(theset2);
                                    mDatas.get(positions).setMyself_tast(theset3);
                                    theAdaper2.notifyDataSetChanged();
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
                                    holder.time.setText(df.format(times/60) + "h/天");
                                    mDatas.get(positions).setAll_time(times);

                                }
                            });
                            builder.show();
                            ////////////////////////////
                        }
                    }
                });
                builder.show();
            }
        });

        theAdaper3 = new GoodsArrayAdapter2(mContext, R.layout.list_item_set_2, theset3);
        holder.myself_listView.setAdapter(theAdaper3);
        setPullLvHeight(holder.myself_listView);//重置listview高度
        holder.myself_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                final EditText inputServer3 = new EditText(mContext);
                AlertDialog.Builder builder3 = new AlertDialog.Builder(mContext);
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
                            Toast.makeText(mContext, "目标不能为空", Toast.LENGTH_SHORT).show();
                        else {
                            //////////////////////////
                            final EditText inputServer = new EditText(mContext);
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
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
                                        final EditText inputServer = new EditText(mContext);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
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
                                                    gai.setTask_name(text1);
                                                    gai.setDuration(i);
                                                    gai.setCycle(j);
                                                    gai.setId(CreateFragment.wei,mDatas.get(positions).getPhase_num(),1,position);
                                                } catch (NumberFormatException e1) {
                                                    Toast.makeText(mContext, "请输入数字", Toast.LENGTH_SHORT).show();
                                                }
                                                mDatas.get(positions).setHave_done(true);
                                                mDatas.get(positions).setMyself_tast_empty(false);
                                                mDatas.get(positions).setEvery_tast(theset2);
                                                mDatas.get(positions).setMyself_tast(theset3);
                                                theAdaper3.notifyDataSetChanged();
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
                                                holder.time.setText(df.format(times/60) + "h/天");
                                                mDatas.get(positions).setAll_time(times);

                                            }
                                        });
                                        builder.show();
                                        ////////////////////////////
                                    }
                                    catch (NumberFormatException e1)
                                    {
                                        Toast.makeText(mContext, "请输入数字", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                            builder.show();
                            ////////////////////////////
                        }
                    }
                });
                builder3.show();
            }
        });

holder.every_jia.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        theset2.add(new Task("空",-1,-1));
        theAdaper2.notifyDataSetChanged();
        setPullLvHeight(holder.evety_listView);
        mDatas.get(positions).setHave_done(true);
        mDatas.get(positions).setEvery_tast(theset2);
        mDatas.get(positions).setMyself_tast(theset3);
        notifyDataSetChanged();
    }
});
holder.every_jian.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        final EditText inputServer = new EditText(mContext);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
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
                        Toast.makeText(mContext, "没有对应行", Toast.LENGTH_SHORT).show();
                }
                catch (NumberFormatException e1)
                {
                    Toast.makeText(mContext, "请输入数字", Toast.LENGTH_SHORT).show();
                }
                theAdaper2.notifyDataSetChanged();
                setPullLvHeight(holder.evety_listView);
                mDatas.get(positions).setHave_done(true);
                if(theset2.size()==0)
                    mDatas.get(positions).setEvery_tast_empty(true);
                mDatas.get(positions).setEvery_tast(theset2);
                mDatas.get(positions).setMyself_tast(theset3);
                notifyDataSetChanged();
            }
        });
        builder.show();
    }
});
        holder.myself_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theset3.add(new Task("空",-1,-1));
                theAdaper3.notifyDataSetChanged();
                setPullLvHeight(holder.myself_listView);
                mDatas.get(positions).setHave_done(true);
                mDatas.get(positions).setEvery_tast(theset2);
                mDatas.get(positions).setMyself_tast(theset3);
                notifyDataSetChanged();
            }
        });
        holder.myself_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //与每日任务的差不多
                final EditText inputServer3 = new EditText(mContext);
                AlertDialog.Builder builder3 = new AlertDialog.Builder(mContext);
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
                                Toast.makeText(mContext, "没有对应行", Toast.LENGTH_SHORT).show();
                        }
                        catch (NumberFormatException e1)
                        {
                            Toast.makeText(mContext, "请输入数字", Toast.LENGTH_SHORT).show();
                        }
                        theAdaper3.notifyDataSetChanged();
                        setPullLvHeight(holder.myself_listView);
                        mDatas.get(positions).setHave_done(true);
                        if(theset3.size()==0)
                        mDatas.get(positions).setMyself_tast_empty(true);
                        mDatas.get(positions).setEvery_tast(theset2);
                        mDatas.get(positions).setMyself_tast(theset3);
                        notifyDataSetChanged();
                    }
                });
                builder3.show();
            }
        });
        holder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText inputServer3 = new EditText(mContext);
                AlertDialog.Builder builder3 = new AlertDialog.Builder(mContext);
                builder3.setTitle("输入该阶段的时间长度（单位：天）").setView(inputServer3)
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
                            if(positions==0) {
                                house chu=  CreateFragment.houses.get(CreateFragment.wei);
                                Aim aim=chu.getAim();
                                if(aim==null)
                                    Toast.makeText(mContext, "请先输入总的开始和结束时间", Toast.LENGTH_SHORT).show();
                                else {
                                    long days=((aim.getEnd_time().getTime() - aim.getStart_time().getTime()) / (1000 * 60 * 60 * 24))+1;
                                    if(days<i)
                                        Toast.makeText(mContext, "填写天数超过可用天数，可用天数："+days, Toast.LENGTH_SHORT).show();
                                    else {
                                        holder.start.setText("        " + i + "天");
                                        mDatas.get(positions).setStart_time(aim.getStart_time());
                                        Date end = aim.getStart_time();
                                        Calendar cal = Calendar.getInstance();
                                        cal.setTime(end);
                                        cal.add(Calendar.DATE, i);
                                        end = cal.getTime();
                                        mDatas.get(positions).setEnd_time(end);
                                    }
                                }
                            }
                            else {
                                if(mDatas.get(positions-1).getEnd_time()==null)
                                    Toast.makeText(mContext, "请先输入上一阶段的时间", Toast.LENGTH_SHORT).show();
                                else {
                                    house chu=  CreateFragment.houses.get(CreateFragment.wei);
                                    Aim aim=chu.getAim();
                                    long days=((aim.getEnd_time().getTime() - aim.getStart_time().getTime()) / (1000 * 60 * 60 * 24));
                                    for(int j=0;j<positions;j++)
                                        days-=((mDatas.get(j).getEnd_time().getTime() - mDatas.get(j).getStart_time().getTime()) / (1000 * 60 * 60 * 24));
                                    if (days<i)
                                        Toast.makeText(mContext, "填写天数超过可用天数，可用天数："+days, Toast.LENGTH_SHORT).show();
                                    else {
                                        holder.start.setText("        " + i + "天");
                                        Date start_data = mDatas.get(positions - 1).getEnd_time();
                                        Calendar cal = Calendar.getInstance();
                                        cal.setTime(start_data);
                                        cal.add(Calendar.DATE, 1);
                                        start_data = cal.getTime();
                                        mDatas.get(positions).setStart_time(start_data);
                                        Calendar cal2 = Calendar.getInstance();
                                        cal2.setTime(start_data);
                                        cal2.add(Calendar.DATE, i);
                                        Date end = cal2.getTime();
                                        mDatas.get(positions).setEnd_time(end);
                                    }
                                }

                            }
                            notifyDataSetChanged();
                        }
                        catch (NumberFormatException e1)
                        {
                            Toast.makeText(mContext, "请输入数字", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder3.show();
            }
        });
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_home, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    private void setPullLvHeight(ListView listView){
        int totalHeight = 0;
        ListAdapter adapter= listView.getAdapter();
        for (int i = 0, len = adapter.getCount(); i < len; i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0); //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listView.getCount() - 1));
        listView.setLayoutParams(params);
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView time;
        TextView start;
        TextView duan;
        ListView evety_listView;
        ListView myself_listView;
        ImageView every_jia;
        ImageView every_jian;
        ImageView myself_jia;
        ImageView myself_jian;
        TextView du;

        public MyViewHolder(View view) {
            super(view);
            time = (TextView) view.findViewById(R.id.time);
            start= (TextView) view.findViewById(R.id.duan_time);
            duan= (TextView) view.findViewById(R.id.duan);
            evety_listView =(ListView) view.findViewById(R.id.evert_listview);
            myself_listView=(ListView) view.findViewById(R.id.ziding_listview);
            every_jia=(ImageView)view.findViewById(R.id.everyday_jia);
            every_jian=(ImageView)view.findViewById(R.id.everyday_jian);
            myself_jia=(ImageView)view.findViewById(R.id.ziding_jia);
            myself_jian=(ImageView)view.findViewById(R.id.ziding_jian);
            du= (TextView) view.findViewById(R.id.du);

        }
    }
    public void addData() {
        //在list中添加数据，并通知条目加入一条
        Phase phase=new Phase();
        mDatas.add(phase);
        //添加动画
        notifyDataSetChanged();
    }

    public void moveData(int n) {
        //在list中删除一条数据
        mDatas.remove(n);
        //添加动画
        notifyDataSetChanged();
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
            string1.setText(item1.getTask_name());
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

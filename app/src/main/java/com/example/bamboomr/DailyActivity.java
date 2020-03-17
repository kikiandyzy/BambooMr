package com.example.bamboomr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.bamboomr.Daily.DailyItem;
import com.example.bamboomr.Daily.DailyItemAdapter;
import com.example.bamboomr.Daily.DefaultItemTouchHelpCallback;
import com.example.bamboomr.Daily.DefaultItemTouchHelper;
import com.example.bamboomr.Daily.DailyTaskDatabaseHelper;
import com.example.bamboomr.Daily.TaskAdapter;
import com.example.bamboomr.house.Aim;
import com.example.bamboomr.house.Phase;
import com.example.bamboomr.house.Task;
import com.example.bamboomr.house.house;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DailyActivity extends AppCompatActivity {

    //主要布局
    private RecyclerView recyclerView;
    private DailyItemAdapter dailyItemAdapter;

    //布局里面的内容
    private List<DailyItem> dailyItemList = new ArrayList<>();
    //自定义任务里面的内容
    private List<Task> mySelfTaskList = null;

    //今天允许复制的任务的ID
    private List<String> dailyTaskIdList=null;
    private List<String> customTaskIdList=null;

    //添加自定义任务的dialog
    Dialog addCustomDialog;
    TaskAdapter taskAdapter;
    //拖曳用的
    private DefaultItemTouchHelper itemTouchHelper;
    public static Context context;

    private final static int TYPE_EVERYDAY = 1;
    private final static int TYPE_MYSELFDAY = 2;
    private final static int TYPE_GETTASKID = 3;

    private List<Task> newAddTaskList = new ArrayList<>();




    //选中这个活动的当前日期
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Date dateForStartActivity = null;
    private Date dateNow = null;
    private String dateForString = "null";

    //这个用于复制
    private static List<DailyItem> dailyItemListForCopy = null;


    //三个按钮
    FloatingActionButton floatingActionButtonAdd;
    FloatingActionButton floatingActionButtonCopy;
    FloatingActionButton floatingActionButtonPaste;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        context = DailyActivity.this;


        initRecyclerView();
        setRecyclerViewTouch();
        initFAButton();


    }



    private void initRecyclerView(){
        dateForString = getIntent().getStringExtra(String.valueOf(R.string.date));
        try {
            //开启这个活动点击的日期
            dateForStartActivity = simpleDateFormat.parse(dateForString);

            Calendar calendar = Calendar.getInstance();
            //今天的日期
            dateNow = simpleDateFormat.parse(""+ calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+ calendar.get(Calendar.DAY_OF_MONTH));

        } catch (ParseException e) {
            e.printStackTrace();
        }


        recyclerView  = findViewById(R.id.recycler_view_daily);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //最原始到构造,这里只是初步构造，开一看到长度固定，以后会有设置进行改变
        for(int i=0;i<20;i++){
            dailyItemList.add(new DailyItem(DailyItem.getTimeForCount(i),"空"));
        }

        dailyItemAdapter = new DailyItemAdapter(dailyItemList);
        recyclerView.setAdapter(dailyItemAdapter);
        //一开始应该从数据库中获取
        List<Task> taskList = getTaskListFormDataBase(this,dateForString);

        if(taskList.size() == 0){
            //如果在数据库中加载不到文件，那就是没有今天的记录,这就需要从文件中获取初始信息
            taskList = getTaskListFormFile(TYPE_EVERYDAY);
            if(taskList.size() != 0 && taskList.size() <= dailyItemList.size()){
                for(int i=0;i<taskList.size();i++){
                    if(taskList.get(i).getDuration() != -1){
                        //先依次添加再调整
                        dailyItemList.get(i).setTask(taskList.get(i));
                    }
                }
                adjustRecyclerView();

            }

        }else {
            for(int i=0;i<taskList.size();i++){
                for(int j=0;j<dailyItemList.size();j++){
                    if(dailyItemList.get(j).getTime().equals(taskList.get(i).getTime())){
                        dailyItemList.get(j).setTask(taskList.get(i));
                    }
                }
            }
            /*
            之前的加载好了在数据库中记录的数据，但是还有一种可能，这个时候又添加了新的房子，
            所以仍然要在文件中读取一遍文件，把新增的加入其中
            * */
            List<Task> taskTemp = getTaskListFormFile(TYPE_EVERYDAY);
            for(int i=0;i<taskTemp.size();i++){
                int x=0;
                for(int j=0;j<taskList.size();j++){

                    if(taskTemp.get(i).getId().equals(taskList.get(j).getId())){
                        x = 1;
                    }

                }
                //遍历之后，发现这个taskTemp的这个task没有和tasklist里面的任何一个一样
                if(x == 0){
                    if(addTask(taskTemp.get(i))){
                        newAddTaskList.add(taskTemp.get(i));
                    }else {
                        Toast.makeText(this, "有每日任务添加失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }





    }

    private void initFAButton(){
        floatingActionButtonAdd = findViewById(R.id.day_time_activity_add);
        floatingActionButtonCopy = findViewById(R.id.day_time_activity_copy);
        floatingActionButtonPaste = findViewById(R.id.day_time_activity_paste);

        floatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(DailyActivity.this, "kiki", Toast.LENGTH_SHORT).show();
                //添加自定义任务到对话框
                bulidAddDialog();
            }
        });

        floatingActionButtonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击了之后复制当天的任务
                dailyItemListForCopy = new ArrayList<>(dailyItemList);
                String s = getIntent().getStringExtra("date");
                Toast.makeText(DailyActivity.this, "已复制"+s+"的任务安排", Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButtonPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //这里建立一个dialog提示一下
                AlertDialog.Builder dialog = new AlertDialog.Builder(DailyActivity.this);
                dialog.setTitle("注意");
                String s = getIntent().getStringExtra("date");
                dialog.setMessage("确认粘贴"+s+"的任务安排吗？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(dailyItemListForCopy.size() != 0){
                            dailyItemList = new ArrayList<>();
                            dailyItemList.addAll(dailyItemListForCopy);
                            for(int j = 0;j<dailyItemList.size();j++){
                                //复制了别天的内容，如果有任务就得查看合理不合理
                                if(dailyItemList.get(j).getTask() != null){
                                    //如果不合法，那就删除
                                    if(!ifLegalForTime(dailyItemList.get(j).getTask())){
                                        dailyItemList.get(j).setTask(null);
                                    }
                                }
                            }
                            //调整好了之后
                            dailyItemAdapter = new DailyItemAdapter(dailyItemList);
                            recyclerView.setAdapter(dailyItemAdapter);
                        }else {
                            Toast.makeText(DailyActivity.this, "没有进行复制", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });
    }

    //这个就是拖动回调的接口
    private DefaultItemTouchHelpCallback.OnItemTouchCallbackListener  onItemTouchCallbackListener =
            new DefaultItemTouchHelpCallback.OnItemTouchCallbackListener() {
                @Override
                public void onSwiped(int adapterPosition) {//删除操作
                    if(dailyItemList != null){
                        dailyItemList.remove(adapterPosition);
                        dailyItemAdapter.notifyItemRemoved(adapterPosition);
                        //myItemAdapter.notifyDataSetChanged();

                    }
                }

                @Override
                public boolean onMove(int srcPosition, int targetPosition) {//交换操作
                    if (dailyItemList != null) {
                        // 更换数据源中的数据Item的位置
                        Collections.swap(dailyItemList, srcPosition, targetPosition);
                        // 更新UI中的Item的位置，主要是给用户看到交互效果

                        //change(srcPosition,targetPosition);
                        dailyItemAdapter.notifyItemMoved(srcPosition, targetPosition);
                        //myItemAdapter.notifyDataSetChanged();
                        //Toast.makeText(DailyActivity.this, "ok", Toast.LENGTH_SHORT).show();

                        return true;
                    }

                    return false;
                }

                @Override
                public boolean clearView() {//这个函数是拖动结束之后调用，在里面整合数据
                    adjustRecyclerView();

                    dailyItemAdapter.notifyDataSetChanged();
                    return false;
                }


            };

    //拖动设置
    private void setRecyclerViewTouch(){
        itemTouchHelper = new DefaultItemTouchHelper(onItemTouchCallbackListener);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        if(dateNow.getTime() > dateForStartActivity.getTime()){
            //若是查看今天之前的，是不允许修改的
            itemTouchHelper.setDragEnable(false);
            Toast.makeText(this, "不允许修改", Toast.LENGTH_SHORT).show();
        }else {
            itemTouchHelper.setDragEnable(true);
        }


        itemTouchHelper.setSwipeEnable(false);
    }

    //调整recyclerview,目的是调整由于任务移动而不合理的时间表
    //这里仍然存在问题，时间会超出！！！！！！！！！！注意注意
    private void adjustRecyclerView(){
        //半小时的次数，记录新建的newDailyItems里面已经保存的用时
        int  timeCount = 0;
        List<DailyItem> newDailyItems = new ArrayList<>();
        for(int i=0;i<dailyItemList.size();i++){
            Task task = dailyItemList.get(i).getTask();
            if(task == null){
                //等于null就表明item里没有任务，照搬
                newDailyItems.add(new DailyItem(DailyItem.getTimeForCount(timeCount),"空"));
                timeCount++;

            }else {//有任务就要检查合理性


                DailyItem dailyItem = new DailyItem(DailyItem.getTimeForCount(timeCount),"空");
                dailyItem.setTask(task);
                newDailyItems.add(dailyItem);
                int temp = getCount(task.getDuration());
                timeCount += temp;


            }

        }
        dailyItemList = new ArrayList<>(newDailyItems);
        //task里面的time属性要和dailyitem里面的time属性一致
        for(int i=0;i<dailyItemList.size();i++){
            if(dailyItemList.get(i).getTask() != null){
                dailyItemList.get(i).getTask().setTime(dailyItemList.get(i).getTime());
            }
        }
        dailyItemAdapter = new DailyItemAdapter(dailyItemList);
        recyclerView.setAdapter(dailyItemAdapter);
        //Toast.makeText(this, "kiki", Toast.LENGTH_SHORT).show();
    }

    //由于任务时长可能大于半个小时，所以要看看删除几条项目
    private int getCount(double duration){
        //商和余数
        int consult = (int)duration/30;
        int remainder = (int)duration%30;
        if(remainder == 0){
            return consult;
        }else if(remainder > 0){
            return consult+1;
        }
        return 0;
    }

    private void bulidAddDialog(){

        //一开始没有加载的时候先开始加载一次
        if(mySelfTaskList == null){
            mySelfTaskList = new ArrayList<>();
            List<Task> tasks = getTaskListFormFile(TYPE_MYSELFDAY);
           for(int i=0;i<tasks.size();i++){

               if(tasks.get(i).getDuration() != -1){
                   //判断dailyitemlist里面是否已经存在这个任务
                   if(ifLegalForExist(tasks.get(i))){
                       mySelfTaskList.add(tasks.get(i));
                   }


               }
           }

        }

        if(mySelfTaskList.size() != 0){//有自定义任务
            LayoutInflater inflater=LayoutInflater.from( this );
            View myview=inflater.inflate(R.layout.dialog_custom_task,null);//引用自定义布局
            AlertDialog.Builder builder=new AlertDialog.Builder( this );
            builder.setView( myview );
            addCustomDialog = builder.create();//创建对话框
            //这里获取整个活动最大的布局的长、宽--待会用来配置对话框的大小
            ConstraintLayout constraintLayout = findViewById(R.id.constraint_layout_daily);
            int width = constraintLayout.getWidth();
            int height = constraintLayout.getHeight();




            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            RecyclerView recyclerView = myview.findViewById(R.id.recycler_view_dialog_custom_task);
            recyclerView.setLayoutManager(layoutManager);
            taskAdapter = new TaskAdapter(mySelfTaskList,addCostomListener);
            recyclerView.setAdapter(taskAdapter);


            addCustomDialog.show();
            //设置成1/2的样子
            addCustomDialog.getWindow().setLayout(width/4*3,height/5*3);
        }else {
            Toast.makeText(context, "已无自定义任务", Toast.LENGTH_SHORT).show();
        }



        //Toast.makeText(this, ""+width+"-"+height, Toast.LENGTH_SHORT).show();


    }

    //这个函数判断添加的自定义任务可不可以添加，如果可以的话，就添加到第一处合适的地方
    private boolean addTask(Task task){
        //查看需要几个半小时
        int duration = getCount(task.getDuration());
        for(int i=0;i<dailyItemList.size();i++){
            //长度要够
            if(i+duration-1 < dailyItemList.size()){


                int temp = 0;
                //接下来的几个dailyItemList的项中都不能有task
                for(int j=0;j<duration;j++){

                    if(dailyItemList.get(i+j).getTask() == null){
                        temp++;
                    }

                }

                if(temp==duration){
                    dailyItemList.get(i).setTask(task);
                    adjustRecyclerView();
                    newAddTaskList.add(task);
                    dailyItemAdapter.notifyDataSetChanged();
                    return true;
                }
            }else {
                return false;
            }


        }
        return false;
    }

    //自定义对话框如果添加成功之后，要对里面的内容进行修改
    private TaskAdapter.AddCostomListener addCostomListener = new TaskAdapter.AddCostomListener() {
        @Override
        public boolean add(int position) {
            //如果成功添加
            if(addTask(mySelfTaskList.get(position))){
                //把添加的项移除，再将dialog关闭
                mySelfTaskList.remove(position);
                taskAdapter.notifyDataSetChanged();
                addCustomDialog.dismiss();
                return true;
            }
            else {
                return false;
            }
        }
    };

    //初始化时从文件中读取
    private List<Task> getTaskListFormFile(int type){
        //把文件里面的存储的所有房子的记录加载下来
        List<house> houses = CreateFragment.serve.load();
        List<Task> taskList = new ArrayList<>();

        if(houses.size() != 0){


            //房子里面与存储任务有关的类是aim，现在先取出来
            List<Aim> aimList = new ArrayList<>();
            //第一层筛选，选出在合适时期的任务
            for(int i=0;i<houses.size();i++){
                Aim aim = houses.get(i).getAim();
                if(aim != null){

                    /*long t1 = aim.getStart_time().getTime();
                    long t2 = dateForStartActivity.getTime();
                    long t3 = aim.getEnd_time().getTime();
                    int iii =1;*/
                    //这里判断今天的日期在不在总阶段里面
                    if (aim.getStart_time().getTime() <= dateForStartActivity.getTime() && aim.getEnd_time().getTime() >= dateForStartActivity.getTime()){
                        aimList.add(aim);
                    }
                }

            }

            List<Phase> phaseList = new ArrayList<>();
            //第二次筛选，选择每个房子里面合适的阶段
            if(aimList.size() != 0){
                for(int i=0;i<aimList.size();i++){
                    //先获取配适的总阶段里面的所有的分阶段
                    List<Phase> phases = aimList.get(i).getPhase();
                    for(int j=0;j<phases.size();j++){
                        if(phases.get(j).getStart_time().getTime() <= dateForStartActivity.getTime() && phases.get(j).getEnd_time().getTime() >= dateForStartActivity.getTime()){
                            //再将配适的总阶段里面的所有的分阶段里面配饰的分阶段保存下来
                            phaseList.add(phases.get(j));
                        }
                    }
                }
            }else {
                Toast.makeText(this, "今日无任务--无总阶段配适", Toast.LENGTH_SHORT).show();
                return taskList;
            }



            if(phaseList.size() != 0){
                //保存了所以时间时候的分阶段，就可以将里面所有的任务按照要求进行添加
                if(type == TYPE_EVERYDAY){
                    for(int i=0;i<phaseList.size();i++){
                        taskList.addAll(phaseList.get(i).getEvery_tast());



                    }
                }else if(type == TYPE_MYSELFDAY){
                    for(int i=0;i<phaseList.size();i++){
                        taskList.addAll(phaseList.get(i).getMyself_tast());
                    }
                }else if(type == TYPE_GETTASKID){
                    for(int i=0;i<phaseList.size();i++){
                        dailyTaskIdList = new ArrayList<>();
                        customTaskIdList = new ArrayList<>();

                        //这两个用于判断复制的时候的任务合理不合理
                        dailyTaskIdList.addAll(phaseList.get(i).getDailyTaskIdList());
                        customTaskIdList.addAll(phaseList.get(i).getCustomTaskIdList());

                    }
                    return null;
                }
                //所有从文件获取的task都是新的，需要赋值给他日期属性
                for(int i=0;i<taskList.size();i++){
                    taskList.get(i).setDate(getIntent().getStringExtra(String.valueOf(R.string.date)));
                }
                return taskList;

            }else {
                Toast.makeText(this, "今日无任务--无分阶段配适", Toast.LENGTH_SHORT).show();
            }



        }else {
            Toast.makeText(this, "今日无任务--无房子", Toast.LENGTH_SHORT).show();
        }




        return taskList;
    }

   //复制按钮启动后，对于复制过来的task，需要检查时间上的合理与否
   private boolean ifLegalForTime(Task task){
       if(dailyTaskIdList == null){
           getTaskListFormFile(TYPE_GETTASKID);
       }

       for(int i=0;i<dailyTaskIdList.size();i++){
           if(dailyTaskIdList.get(i).equals(task.getId())){
               //找到即合法
               return true;
           }
       }
       for(int i=0;i<customTaskIdList.size();i++){
           if(customTaskIdList.get(i).equals(task.getId())){
               //找到即合法
               return true;
           }
       }
       //都找不到返回失败
       return false;
   }

    /*
    打开自定义任务的对话框时，dailyitem有可能是数据库或者是复制过来的，
    这个时候可以里面可能已经有自定义的任务了，直接显示就会有重复
    * */

    private boolean ifLegalForExist(Task task){
        if(dailyTaskIdList == null){
            getTaskListFormFile(TYPE_GETTASKID);
        }


        for(int i=0;i<dailyItemList.size();i++){
            if(dailyItemList.get(i).getTask() != null){
                if(task.getId().equals(dailyItemList.get(i).getTask().getId())){
                    return false;
                }
            }

        }

        return true;
    }
    public static List<Task> getTaskListFormDataBase(Context context,String dateForString){
        DailyTaskDatabaseHelper dailyTaskDatabaseHelper = new DailyTaskDatabaseHelper(context,DailyTaskDatabaseHelper.DATEBASE_NAME,null,1);
        SQLiteDatabase db = dailyTaskDatabaseHelper.getWritableDatabase();
        List<Task> taskList = new ArrayList<>();

        //选择出日期相同的记录
        Cursor cursor = db.rawQuery("select * from task where date = ?",new String[]{dateForString});
        if(cursor.moveToFirst()){
            do{
                double cycle = cursor.getDouble(cursor.getColumnIndex("cycle"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String id = cursor.getString(cursor.getColumnIndex("id"));

                double duration = cursor.getDouble(cursor.getColumnIndex("duration"));
                double record_duration = cursor.getDouble(cursor.getColumnIndex("record_duration"));

                Task task = new Task(name,duration,cycle);
                task.setId(id);
                task.setRecordDuration(record_duration);
                task.setTime(time);
                taskList.add(task);
            }while (cursor.moveToNext());
        }
        if(taskList.size() != 0){
            ifDateBaseExit = 1;
        }
        return taskList;
    }

    private static int ifDateBaseExit = 0;
    private void updateDatebase(){
        DailyTaskDatabaseHelper dailyTaskDatabaseHelper = new DailyTaskDatabaseHelper(this,DailyTaskDatabaseHelper.DATEBASE_NAME,null,1);
        SQLiteDatabase db = dailyTaskDatabaseHelper.getWritableDatabase();
        List<Task> tasks = new ArrayList<>();
        for(int i=0;i<dailyItemList.size();i++){
            if(dailyItemList.get(i).getTask() != null){
                tasks.add(dailyItemList.get(i).getTask());
            }
        }
        //之前没有数据
        if(ifDateBaseExit == 0){
            for(int i=0;i<tasks.size();i++){
                db.execSQL("insert into task(date,time,name,id,cycle,duration,record_duration) values(?,?,?,?,?,?,?)",
                        new String[]{dateForString,tasks.get(i).getTime(),tasks.get(i).getTask_name(),tasks.get(i).getId(),""+tasks.get(i).getCycle(),""+tasks.get(i).getDuration(),""+tasks.get(i).getRecordDuration()});
            }
            //插入操作

        }else {
            //更新操作
            for(int i=0;i<tasks.size();i++){
                db.execSQL("update task set time = ? where id = ? and date = ?",new String[]{
                        tasks.get(i).getTime(),tasks.get(i).getId(),dateForString
                });

            }
            //新增的要进行插入
            for(int i=0;i<newAddTaskList.size();i++){
                db.execSQL("insert into task(date,time,name,id,cycle,duration,record_duration) values(?,?,?,?,?,?,?)",
                        new String[]{dateForString,newAddTaskList.get(i).getTime(),newAddTaskList.get(i).getTask_name(),newAddTaskList.get(i).getId(),""+newAddTaskList.get(i).getCycle(),""+newAddTaskList.get(i).getDuration(),""+newAddTaskList.get(i).getRecordDuration()});
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭的时候保存一下数据库
        updateDatebase();
        confirm();
    }

    //为主活动的参数确认
    private void confirm(){
        if(dateForStartActivity.getTime() == dateNow.getTime()){
            MainActivity.CONFIRMTASK = true;
            MainActivity.dailyItemList = new ArrayList<>(dailyItemList);
        }
    }


}

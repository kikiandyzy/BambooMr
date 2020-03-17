package com.example.bamboomr.Daily;


import com.example.bamboomr.house.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyItem {
    private String time;
    private String task_name;
    private Task task = null;




    public DailyItem(String time,String taskName){
        this.time = time;
        this.task_name = taskName;
    }

    public String getTimeForCount() {
        return time;
    }

    public String getTask_name() {
        if(task != null){
            return task.getTask_name();
        }
        return task_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    public String getDuration(){
        if(task != null){
            return ""+task.getDuration()+"min";
        }
        return "30min";
    }


    public static int startTime = 8;

    public static String getTimeForCount(int count){
        int consult = count/2;
        int remainder = count%2;
        if(remainder == 0){
            return ""+(startTime+consult)+":00";
        }else {
            return ""+(startTime+consult)+":30";
        }
    }

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

    public Date getDate(){
        try {
            return simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


}

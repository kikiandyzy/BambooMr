package com.example.bamboomr.house;

import android.icu.text.DecimalFormat;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Task implements Serializable {
    //duration单位分钟，意思为总时长,时长为-1即为空，cycle单位天，意思为周期，如果为-1则为每天
    //id的第一位为house的ID，第二位为阶段数，第三位为每日任务或自定义任务，每日任务为0，自定义任务为1，第四位为自身编号
    private String task_name;
    private double duration;
    private double cycle;
    private String id;

    //当天记录时长的描述
    public double recordDuration;
    public String completeness = "0";//完成度的记录


    public Task(String tast_name, double duration, double cycle) {
        this.task_name = tast_name;
        this.duration = duration;
        this.cycle = cycle;
    }

    public void setId(int houseId,int stageId,int type,int id) {
        this.id = ""+houseId+"-"+stageId+"-"+type+"-"+id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getRecordDuration() {
        return recordDuration;
    }

    public void setRecordDuration(double recordDuration) {
        this.recordDuration = recordDuration;
    }

    public void setCompleteness(String completeness) {
        this.completeness = completeness;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String tast_name) {
        this.task_name = tast_name;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getCycle() {
        return cycle;
    }

    public void setCycle(double cycle) {
        this.cycle = cycle;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)

    public String getCompleteness() {
        if(duration > 0){
            completeness = ""+new DecimalFormat("#0.0%").format(recordDuration/duration);
        }
        return completeness;
    }

    private String date;
    private String time = "null";

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}


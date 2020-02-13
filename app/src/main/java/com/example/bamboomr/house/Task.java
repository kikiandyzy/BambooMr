package com.example.bamboomr.house;

import java.io.Serializable;

public class Task implements Serializable {
    //duration单位分钟，意思为总时长，cycle单位天，意思为周期，如果为-1则为每天
    private String tast_name;
    private double duration;
    private double cycle;

    public Task(String tast_name, double duration, double cycle) {
        this.tast_name = tast_name;
        this.duration = duration;
        this.cycle = cycle;
    }

    public String getTast_name() {
        return tast_name;
    }

    public void setTast_name(String tast_name) {
        this.tast_name = tast_name;
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
}

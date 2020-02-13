package com.example.bamboomr.house;

import java.io.Serializable;

public class Phase implements Serializable {
    private int phase_num;
    private String start_time;
    private String end_time;
    private double all_time;
    private double finish_time;
    private Task[] every_tast;
    private Task[] myself_tast;

    public int getPhase_num() {
        return phase_num;
    }

    public void setPhase_num(int phase_num) {
        this.phase_num = phase_num;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public double getAll_time() {
        return all_time;
    }

    public void setAll_time(double all_time) {
        this.all_time = all_time;
    }

    public double getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(double finish_time) {
        this.finish_time = finish_time;
    }

    public Task[] getEvery_tast() {
        return every_tast;
    }

    public void setEvery_tast(Task[] every_tast) {
        this.every_tast = every_tast;
    }

    public Task[] getMyself_tast() {
        return myself_tast;
    }

    public void setMyself_tast(Task[] myself_tast) {
        this.myself_tast = myself_tast;
    }
}

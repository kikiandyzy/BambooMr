package com.example.bamboomr.house;

import java.io.Serializable;
import java.util.ArrayList;

public class Phase implements Serializable {
    private boolean empty=true;
    private int phase_num;
    private int hold_time=-1;
    private double all_time;
    private double finish_time=0;
    private ArrayList<Task> every_tast;
    private ArrayList<Task> myself_tast;

    public int getHold_time() {
        return hold_time;
    }

    public void setHold_time(int hold_time) {
        this.hold_time = hold_time;
    }


    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }


    public int getPhase_num() {
        return phase_num;
    }

    public void setPhase_num(int phase_num) {
        this.phase_num = phase_num;
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

    public ArrayList<Task> getEvery_tast() {
        return every_tast;
    }

    public void setEvery_tast(ArrayList<Task> every_tast) {
        this.every_tast = every_tast;
    }

    public ArrayList<Task> getMyself_tast() {
        return myself_tast;
    }

    public void setMyself_tast(ArrayList<Task> myself_tast) {
        this.myself_tast = myself_tast;
    }
}

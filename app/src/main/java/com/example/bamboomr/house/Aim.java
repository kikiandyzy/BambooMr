package com.example.bamboomr.house;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Aim implements Serializable {
    private String big_aim;
    private Date start_time;
    private Date  end_time;
    private ArrayList<Phase> phase;

    public String getBig_aim() {
        return big_aim;
    }

    public void setBig_aim(String big_aim) {
        this.big_aim = big_aim;
    }

    public Date  getStart_time() {
        return start_time;
    }

    public void setStart_time(Date  start_time) {
        this.start_time = start_time;
    }

    public Date  getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date  end_time) {
        this.end_time = end_time;
    }

    public ArrayList<Phase> getPhase() {
        return phase;
    }

    public void setPhase(ArrayList<Phase> phase) {
        this.phase = phase;
    }
}

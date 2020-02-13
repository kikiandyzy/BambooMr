package com.example.bamboomr.house;

import java.io.Serializable;

public class Aim implements Serializable {
    private String big_aim;
    private String start_time;
    private String end_time;
    private Phase[] phase;

    public String getBig_aim() {
        return big_aim;
    }

    public void setBig_aim(String big_aim) {
        this.big_aim = big_aim;
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

    public Phase[] getPhase() {
        return phase;
    }

    public void setPhase(Phase[] phase) {
        this.phase = phase;
    }
}

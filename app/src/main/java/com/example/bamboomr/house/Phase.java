package com.example.bamboomr.house;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Phase implements Serializable {
    private boolean have_done=false;
    private boolean every_tast_empty=true;
    private boolean myself_tast_empty=true;
    private int phase_num;
    private Date start_time;
    private Date end_time;
    private double all_time;
    private double finish_time=0;
    private ArrayList<Task> every_tast;
    private ArrayList<Task> myself_tast;

    public boolean isHave_done() {
        return have_done;
    }

    public void setHave_done(boolean have_done) {
        this.have_done = have_done;
    }



    public boolean isEvery_tast_empty() {
        return every_tast_empty;
    }

    public void setEvery_tast_empty(boolean every_tast_empty) {
        this.every_tast_empty = every_tast_empty;
    }

    public boolean isMyself_tast_empty() {
        return myself_tast_empty;
    }

    public void setMyself_tast_empty(boolean myself_tast_empty) {
        this.myself_tast_empty = myself_tast_empty;
    }


    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
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

    public List<String> getDailyTaskIdList(){
        List<String> s = new ArrayList<>();
        for(int i=0;i<every_tast.size();i++){
            s.add(every_tast.get(i).getId());
        }
        return s;
    }
    public List<String> getCustomTaskIdList(){
        List<String> s = new ArrayList<>();
        for(int i=0;i<myself_tast.size();i++){
            s.add(myself_tast.get(i).getId());
        }
        return s;
    }
}

package com.example.bamboomr.Daily;

public class ScheduleItem {
    private int year = 0;
    private int month = 0;
    private int day = 0;
    private String week;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public ScheduleItem(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public ScheduleItem(String week){
        this.week = week;
    }

    public String getDate(){
        return ""+year+"-"+month+"-"+day;
    }

    public String getWeek() {
        return week;
    }
}

package com.izl0gc.ye.payclock.models;

public class Shift {
    private int id;
    private int work_id;
    private double startTime, endTime;
    private double rate;
    private long date;
    private boolean has_break;

    public Shift(int work_id, double startTime, double endTime, double rate, long date, boolean has_break){
        this.work_id = work_id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.rate = rate;
        this.date = date;
        this.has_break = has_break;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWork_id() {
        return work_id;
    }

    public void setWork_id(int work_id) {
        this.work_id = work_id;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isHas_break() {
        return has_break;
    }

    public void setHas_break(boolean has_break) {
        this.has_break = has_break;
    }
}

package com.izl0gc.ye.payclock.models;

public class Work {
    private int id;
    private String name;
    private double rate;

    public Work(String name, double rate){
        this.setName(name);
        this.setRate(rate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}

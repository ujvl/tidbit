package com.thetidbitapp.model;

import java.util.GregorianCalendar;

/**
 * Created by Ujval on 2/22/15.
 */
public class Tidbit {

    private String name;
    private GregorianCalendar datetime;
    private String location;
    private boolean hasFreeFood;
    private String food;
    private String description;

    public Tidbit(String s, GregorianCalendar g, String loc, boolean b) {
        this.name = s;
        this.location = loc;
        this.datetime = g;
        this.hasFreeFood = b;
    }

    public Tidbit(String s, GregorianCalendar g, String loc, boolean b, String f, String descr) {
        this.name = s;
        this.location = loc;
        this.datetime = g;
        this.hasFreeFood = b;
        this.food = f;
        this.description = descr;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        //TODO
        return "Feb 1st 12 - 4 pm";
    }

    public String getLocation() {
        return location;
    }

    public boolean hasFreeFood() {
        return hasFreeFood;
    }

    public String getFood() {
        return hasFreeFood? food : "";
    }

    public String getDescription() {
        return description;
    }
}


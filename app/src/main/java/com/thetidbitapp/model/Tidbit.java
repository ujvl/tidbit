package com.thetidbitapp.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ujval on 2/22/15.
 */
public class Tidbit {

    private String name;
    private Date datetime;
    private String location;
    private String food;
    private String description;
    private int attendence;
    private boolean userAttending;

//    private static SimpleDateFormat dateFormater = new SimpleDateFormat("mm dd yy at tt");

    public Tidbit(String s, Date d, String loc) {
        this.name = s;
        this.location = loc;
        this.datetime = d;
    }

    public Tidbit(String s, Date d, String loc, String f, String descr) {
        this.name = s;
        this.location = loc;
        this.datetime = d;
        this.food = f;
        this.description = descr;
    }

    public String getName() {
        return name;
    }

    public String getDate() { return "date here";/*dateFormater.format(datetime);*/ }

    public String getLocation() { return location; }

    public boolean hasFreeFood() { return food == null; }

    public String getFood() { return food; }

    public String getDescription() { return description; }

}


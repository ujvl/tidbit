package com.thetidbitapp.model;

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
    private int numAttendees;
    private boolean userAttending;

//    private static SimpleDateFormat dateFormater = new SimpleDateFormat("mm dd yy at tt");

    public Tidbit(String name, Date date, String loc, String food, int att) {
        this.name = name;
        this.location = loc;
        this.datetime = date;
        this.food = food;
        this.numAttendees = att;
    }

    public Tidbit(String s, Date d, String loc, String f, String descr) {
        this.name = s;
        this.location = loc;
        this.datetime = d;
        this.food = f;
        this.description = descr;
    }

    public String eventName() {
        return name;
    }

    public String datetime() { return "Mar 12th at 1:00 pm";/*dateFormater.format(datetime);*/ }

    public String location() { return location; }

    public boolean hasFreeFood() { return food == null; }

    public String food() { return food; }

    public String description() { return description; }

    public int numberAttending() { return numAttendees; }

}


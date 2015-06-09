package com.thetidbitapp.model;

import android.graphics.Bitmap;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ujval on 2/22/15.
 */
public class Event {

    private String mId;
    private String mName;
    private Date mDatetime;
    private String mLocation;
    private String mFood;
    private String mDescription;
    private int mNumAttendees;
    private Bitmap cover;

    private static SimpleDateFormat dateFormater;

    public Event(String id, String name, Date date, String loc, String food, int att) {
        this(id, name, date, loc, food, "", att);
    }

    public Event(String id, String name, Date date, String loc, String food, String descr) {
        this(id, name, date, loc, food, descr, 0);
    }

    public Event(String id, String name, Date date, String loc, String food, String descr, int att) {
        mId = id;
        mName = name;
        mLocation = loc;
        mDatetime = date;
        mFood = food;
        mDescription = descr;
        mNumAttendees = att;
    }

	public String id() { return mId; }

    public String eventName() { return mName; }

    public String datetime() { return "Mar 12th at 1:00 pm";/*dateFormater.format(datetime);*/ }

    public String location() { return mLocation; }

    public boolean hasFreeFood() { return mFood == null; }

    public String food() { return mFood; }

    public String description() { return mDescription; }

    public int numberAttending() { return mNumAttendees; }

    public Bitmap cover() { return cover; }

    public void setName(String name) { mName = name; }

    public void setDatetime(Date datetime) { mDatetime = datetime; }

    public void setLocation(String location) { mLocation = location; }

    public void setFood(String food) { mFood = food; }

    public void setmDescription(String mDescription) { mDescription = mDescription; }

    public void setNumAttendees(int numAttendees) { mNumAttendees = numAttendees; }

    public void setCover(Bitmap cover) { cover = cover; }

}


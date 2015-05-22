package com.thetidbitapp.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Ujval on 5/23/15.
 */
public class InterceptingViewPager extends ViewPager {

    private int childId;

    public InterceptingViewPager(Context context) {
        super(context);
    }

    public InterceptingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public void setChildId(int id) {
        childId = id;
    }
}

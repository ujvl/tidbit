package com.thetidbitapp.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Ujval on 5/23/15.
 */
public class NoSwipeViewPager extends ViewPager {

    int childId;

    public NoSwipeViewPager(Context context) {
        super(context);
    }

    public NoSwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (childId > 0) {
            ViewPager childPager = (ViewPager) findViewById(childId);

            if (childPager != null) {
                childPager.requestDisallowInterceptTouchEvent(true);
            }

        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public void setChildId(int id) {
        childId = id;
    }
}

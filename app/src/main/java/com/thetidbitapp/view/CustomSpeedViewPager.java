package com.thetidbitapp.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;

import java.lang.reflect.Field;

/**
 * Created by Ujval on 5/24/15.
 *
 * Has slower horizontal scroll than your
 * average ViewPager
 */
public class CustomSpeedViewPager extends ViewPager {

    public CustomSpeedViewPager(Context context) {
        super(context);
    }

    public CustomSpeedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Uses reflection to change the Scroller
     * @param scrollFactor factor to change scroll speed by
     *                     (the bigger the factor, the slower it is)
     */
    public void changeScrollSpeedFactor(double scrollFactor) {
        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            DecelerateInterpolator interpolator = new DecelerateInterpolator();
            CustomDurationScroller scroller = new CustomDurationScroller(getContext(), interpolator);
            scroller.setScrollFactor(scrollFactor);
            mScroller.set(this, scroller);
        }
        catch (NoSuchFieldException|IllegalArgumentException|IllegalAccessException e) { }
    }

}

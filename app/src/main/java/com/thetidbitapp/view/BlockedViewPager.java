package com.thetidbitapp.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Ujval on 5/23/15.
 */
public class BlockedViewPager extends ViewPager {

    private GestureDetector gestureDetector;

    public BlockedViewPager(Context context) {
        super(context);
        gestureDetector = new GestureDetector(context, new XScrollDetector());
        modifyScrollSpeed();
    }

    public BlockedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(context, new XScrollDetector());
        modifyScrollSpeed();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    class XScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return Math.abs(distanceY) < Math.abs(distanceX);
        }
    }

    private void modifyScrollSpeed() {
        /*try {
            Field scroller = ViewPager.class.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);
            mScroller = new CustomDurationScroller(getContext(), (Interpolator) interpolator.get(null));
            scroller.set(this, mScroller);
            mScroller.setScrollDurationFactor(2.0);
        } catch (Exception e) {  }*/
    }
}

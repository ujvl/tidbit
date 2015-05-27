package com.thetidbitapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 *
 * Created by Ujval on 5/23/15.
 *
 * Consumes horizontal scroll touch events
 * instead of letting the nested ViewPager handle it
 *
 * Also scrolls slower than a normal ViewPager
 */
public class BlockedViewPager extends CustomSpeedViewPager {

    private GestureDetector gestureDetector;

    public BlockedViewPager(Context context) {
        super(context);
        gestureDetector = new GestureDetector(context, new XScrollDetector());
    }

    public BlockedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(context, new XScrollDetector());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onInterceptTouchEvent(event);
    }

    class XScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return Math.abs(distanceY) < Math.abs(distanceX);
        }
    }
}

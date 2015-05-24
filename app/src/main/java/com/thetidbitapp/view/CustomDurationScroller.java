package com.thetidbitapp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;
/**
 * Created by Ujval on 5/23/15.
 */

public class CustomDurationScroller extends Scroller {

    private double scrollFactor;

    public CustomDurationScroller(Context context) {
        super(context);
    }

    public CustomDurationScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public CustomDurationScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    protected void setScrollFactor(double sf) {
        scrollFactor = sf;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, (int) (duration * scrollFactor));
    }

}

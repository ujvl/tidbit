package com.thetidbitapp.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

/**
 * Created by Ujval on 5/30/15.
 */
public class FixedSwipeRefreshLayout extends SwipeRefreshLayout {

    public FixedSwipeRefreshLayout(Context context) {
        super(context);
    }

    public FixedSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
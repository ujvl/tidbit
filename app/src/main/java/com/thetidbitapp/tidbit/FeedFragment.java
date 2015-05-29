package com.thetidbitapp.tidbit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.astuetz.PagerSlidingTabStrip;
import com.balysv.materialripple.MaterialRippleLayout;
import com.rey.material.widget.FloatingActionButton;
import com.thetidbitapp.view.CustomSpeedViewPager;
import com.thetidbitapp.adap.FeedPagerAdapter;

public class FeedFragment extends Fragment implements FloatingActionButton.OnClickListener,
        EventsFragment.OnEventsInteractionListener {

    private MaterialRippleLayout mMapContainer;
    private FloatingActionButton mFab;
    private PagerSlidingTabStrip mTabStrip;
    private boolean hidden = false;

    public FeedFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_feed, container, false);

        CustomSpeedViewPager pager = (CustomSpeedViewPager) root.findViewById(R.id.feed_pager);
        pager.setAdapter(new FeedPagerAdapter(getChildFragmentManager()));
        pager.changeScrollSpeedFactor(0.01); // Hacky fix to make it seem like there's no animation

        mTabStrip = (PagerSlidingTabStrip) root.findViewById(R.id.feed_tabs);
        mTabStrip.setViewPager(pager);

        mMapContainer = (MaterialRippleLayout) root.findViewById(R.id.map_button_ripple);
        mFab = (FloatingActionButton) root.findViewById(R.id.fab);
        mFab.setOnClickListener(this);

        return root;
    }

    @Override
    public void onScrollDown() {
        if (!hidden) {
            move(mMapContainer, mMapContainer.getBottom(), new AccelerateInterpolator());
            move(mFab, mFab.getBottom(), new AccelerateInterpolator());
            move(mTabStrip, -mTabStrip.getHeight(), new AccelerateInterpolator());
        }
        hidden = true;
    }

    @Override
    public void onScrollUp() {
        if (hidden) {
            move(mMapContainer, 0, new DecelerateInterpolator());
            move(mFab, 0, new DecelerateInterpolator());
            move(mTabStrip, 0, new DecelerateInterpolator());
        }
        hidden = false;
    }

    public void move(View v, float value, Interpolator interpolator) {
        v.animate().translationY(value).setInterpolator(interpolator).start();
    }

    @Override
    public void onClick(View v) {

    }

}

package com.thetidbitapp.tidbit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.astuetz.PagerSlidingTabStrip;
import com.balysv.materialripple.MaterialRippleLayout;
import com.melnykov.fab.FloatingActionButton;
import com.thetidbitapp.view.CustomSpeedViewPager;
import com.thetidbitapp.viewadap.FeedPagerAdapter;

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
            hide(mMapContainer, mMapContainer.getBottom());
            hide(mFab, mFab.getBottom());
            hide(mTabStrip, -mTabStrip.getHeight());
        }
        hidden = true;
    }

    @Override
    public void onScrollUp() {
        if (hidden) {
            show(mMapContainer);
            show(mFab);
            show(mTabStrip);
        }
        hidden = false;
    }

    public void hide(View v, float value) {
        v.animate().translationY(value).setInterpolator(new AccelerateInterpolator()).start();
    }

    public void show(View v) {
        v.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
    }

    @Override
    public void onClick(View v) {

    }

}

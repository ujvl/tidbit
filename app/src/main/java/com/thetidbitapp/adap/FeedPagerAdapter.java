package com.thetidbitapp.adap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thetidbitapp.tidbit.EventsFragment;

/**
 * Created by Ujval on 5/28/15.
 */
public class FeedPagerAdapter extends FragmentPagerAdapter {

    public FeedPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch(pos) {
            case 0:
                return EventsFragment.newInstance(EventsFragment.SortType.UPCOMING);
            case 1:
                return EventsFragment.newInstance(EventsFragment.SortType.POPULAR);
            default:
                throw new IllegalStateException("how is this possible??");
        }
    }

    @Override
    public CharSequence getPageTitle(int pos) {
        switch(pos) {
            case 0:
                return "Upcoming";
            case 1:
                return "Popular";
            default:
                throw new IllegalStateException("how is this possible??");
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

}
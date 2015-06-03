package com.thetidbitapp.adap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thetidbitapp.feed.PopularFragment;
import com.thetidbitapp.feed.SavedFragment;
import com.thetidbitapp.feed.UpcomingFragment;
import com.thetidbitapp.feed.UsersPostsFragment;

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
                return new UpcomingFragment();
            case 1:
                return new PopularFragment();
            case 2:
                return new SavedFragment();
            case 3:
                return new UsersPostsFragment();
            default:
                throw new IllegalStateException("how is this possible??");
        }
    }

    @Override
    public CharSequence getPageTitle(int pos) {
        switch(pos) {
            case 0:
                return "Upcoming".toUpperCase();
            case 1:
                return "Popular".toUpperCase();
            case 2:
                return "Saved".toUpperCase();
            case 3:
                return "Your Posts".toUpperCase();
            default:
                throw new IllegalStateException("how is this possible??");
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

}
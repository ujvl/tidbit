package com.thetidbitapp.adap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thetidbitapp.core.PopularFragment;
import com.thetidbitapp.core.SavedFragment;
import com.thetidbitapp.core.UpcomingFragment;
import com.thetidbitapp.core.UsersPostsFragment;

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
                return "Upcoming";
            case 1:
                return "Popular";
            case 2:
                return "Saved";
            case 3:
                return "Your Posts";
            default:
                throw new IllegalStateException("how is this possible??");
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

}
package com.thetidbitapp.viewadap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip;
import com.thetidbitapp.tidbit.FeedFragment;
import com.thetidbitapp.tidbit.OverflowFragment;
import com.thetidbitapp.tidbit.ProfileFragment;
import com.thetidbitapp.tidbit.R;

/**
 * Created by Ujval on 5/27/15.
 */
public class MainPagerAdapter extends FragmentPagerAdapter
        implements PagerSlidingTabStrip.IconTabProvider {

    final int[] ICONS = new int[]{
            R.drawable.ic_home_white_24dp,
            R.drawable.ic_account_circle_white_24dp,
            R.drawable.ic_view_headline_white_24dp
    };

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch(pos) {
            case 0:
                return new FeedFragment();
            case 1:
                return ProfileFragment.newInstance("ThirdFragment", "Instance 1");
            case 2:
                return new OverflowFragment();
            default:
                throw new IllegalStateException("how would this happen??");
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public int getPageIconResId(int i) {
        return ICONS[i];
    }

}
package com.thetidbitapp.model;

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
            R.drawable.ic_menu_home, R.drawable.ic_action_map,
            R.drawable.ic_action_person, R.drawable.ic_action_menu
    };

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch(pos) {
            case 0:
                return FeedFragment.newInstance("Feed fragment", "Instance 1");
            case 1:
                return ProfileFragment.newInstance("SecondFragment", "Instance 1");
            case 2:
                return ProfileFragment.newInstance("ThirdFragment", "Instance 1");
            case 3:
                return new OverflowFragment();
            default:
                throw new IllegalStateException("only 4 pages available");
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public int getPageIconResId(int i) {
        return ICONS[i];
    }

}
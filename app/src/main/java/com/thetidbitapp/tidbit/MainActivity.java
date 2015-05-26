package com.thetidbitapp.tidbit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBarActivity;

import com.astuetz.PagerSlidingTabStrip;
import com.thetidbitapp.view.BlockedViewPager;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BlockedViewPager pager = (BlockedViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));

        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pagerSlidingTabStrip.setViewPager(pager);

    }

    private class MainPagerAdapter extends FragmentPagerAdapter
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
                    return OverflowFragment.newInstance("ThirdFragment", "Instance 2");
                default:
                    throw new IllegalStateException("you done fd up -- 4 pages available");
            }
        }

        /*@Override
        public CharSequence getPageTitle(int pos) {
            switch(pos) {
                case 0:
                    return "Feed";
                case 1:
                    return "Bitmap";
                case 2:
                    return "Your Tidbits";
                case 3:
                    return "Settings";
                default:
                    throw new IllegalStateException("you done fd up -- 4 pages available");
            }
        }*/

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public int getPageIconResId(int i) {
            return ICONS[i];
        }
    }
}
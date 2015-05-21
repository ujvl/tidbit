package com.thetidbitapp.tidbit;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

/**
 * A login screen that offers login via email/password.
 */
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();
        final ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));

        // -----------------------  Add tabs  -----------------------

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                pager.setCurrentItem(tab.getPosition());
            }
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) { }
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) { }
        };

        // Add 3 tabs, specifying the tab's text and TabListener
        for (int i = 0; i < 4; i++) {
            actionBar.addTab(actionBar.newTab().setText("Tab " + (i + 1)).setTabListener(tabListener));
        }

        // Change action bar selected item on swipe
        pager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        getActionBar().setSelectedNavigationItem(position);
                    }
                }
        );

    }

    private class MainPagerAdapter extends FragmentPagerAdapter {

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0:
                    return TidbitFragment.newInstance("FirstFragment", "Instance 1");
                case 1:
                    return TidbitFragment.newInstance("SecondFragment", "Instance 1");
                case 2:
                    return TidbitFragment.newInstance("ThirdFragment", "Instance 1");
                case 3:
                    return TidbitFragment.newInstance("ThirdFragment", "Instance 2");
                default:
                    throw new IllegalStateException("you done fd up -- 4 pages available");
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
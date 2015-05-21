package com.thetidbitapp.tidbit;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

/**
 * A login screen that offers login via email/password.
 */
public class MainActivity extends ActionBarActivity {

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));

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
        public CharSequence getPageTitle(int pos) {
            switch(pos) {
                case 0:
                    return "Feed";
                case 1:
                    return "Bitmap";
                case 2:
                    return "My Tidbit";
                case 3:
                    return "Settings";
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
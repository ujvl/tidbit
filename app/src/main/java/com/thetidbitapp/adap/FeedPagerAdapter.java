package com.thetidbitapp.adap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.thetidbitapp.feed.BaseEventsFragment;
import com.thetidbitapp.feed.PopularFragment;
import com.thetidbitapp.feed.SavedFragment;
import com.thetidbitapp.feed.UpcomingFragment;
import com.thetidbitapp.feed.UsersPostsFragment;

/**
 * Created by Ujval on 5/28/15.
 */
public class FeedPagerAdapter extends FragmentPagerAdapter {

	private BaseEventsFragment[] fragments = new BaseEventsFragment[getCount()];

	public interface Reloadable {
		public void onReload();
	}

    public FeedPagerAdapter(FragmentManager fm) {
        super(fm);
		Log.e("CONSTRUCTOR CALLED", "OKAY ---------------------------");
    }

    @Override
    public Fragment getItem(int pos) {
		Log.e("ASSIGNING NEW FRAGMENT FOR " + pos, "YA OK GOTCHA");
        switch(pos) {
            case 0:
                return fragments[pos] = new UpcomingFragment();
            case 1:
                return fragments[pos] = new PopularFragment();
            case 2:
                return fragments[pos] = new SavedFragment();
            case 3:
                return fragments[pos] = new UsersPostsFragment();
            default:
                throw new IllegalStateException("how is this possible??");
        }
    }

	public BaseEventsFragment getCurrentInstance(int pos) {
		Log.e("RETURNING EVENTS FRAGMENT FOR " + pos, "YA OK GOTCHA\n");
		Log.e("RETURNING EVENTS FRAGMENT FOR " + pos, fragments[0] + " and " +fragments[1] +
				" and " + fragments[2] + " and " + fragments[3] + "\n-------------------");
		return fragments[pos];
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
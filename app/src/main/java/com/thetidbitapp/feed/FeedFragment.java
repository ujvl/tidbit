package com.thetidbitapp.feed;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.*;

import com.balysv.materialripple.MaterialRippleLayout;
import com.rey.material.widget.FloatingActionButton;
import com.rey.material.widget.TabPageIndicator;
import com.thetidbitapp.adap.FeedPagerAdapter;
import com.thetidbitapp.tidbit.R;

public class FeedFragment extends Fragment implements FloatingActionButton.OnClickListener,
        BaseEventsFragment.OnEventListInteractionListener, ViewPager.OnPageChangeListener {

    private OnFeedInteractionListener mListener;
    private FloatingActionButton mFab;
    private TabPageIndicator mTabStrip;
	private FeedPagerAdapter mAdapter;

    private boolean hidden;
	private final boolean[] needToRefresh = new boolean[4];

    public interface OnFeedInteractionListener {
        public void onFABClick();
        public void onCardClick(String id);
    }

    public FeedFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_feed, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_feed, container, false);

		mAdapter = new FeedPagerAdapter(getChildFragmentManager());
        ViewPager pager = (ViewPager) root.findViewById(R.id.feed_pager);
        pager.setAdapter(mAdapter);
		pager.setOnPageChangeListener(this);
        pager.setOffscreenPageLimit(3);

        mTabStrip = (TabPageIndicator) root.findViewById(R.id.feed_tabs);
        mTabStrip.setOnPageChangeListener(this);
        mTabStrip.setViewPager(pager);

        mFab = (FloatingActionButton) root.findViewById(R.id.fab);
        mFab.setOnClickListener(this);

        return root;
    }

    @Override
    public void onPageSelected(int position) {
		onScrollUp();
		if (needToRefresh[position]) {
			mAdapter.getCurrentInstance(position).onReload();
			needToRefresh[position] = false;
		}
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

    @Override
    public void onPageScrollStateChanged(int state) { }

	@Override
	public void onItemsChanged(int position) {
		for (int i = 0; i < needToRefresh.length; i++) {
			if (i != position && i != needToRefresh.length - 1) {
				needToRefresh[i] = true;
			}
		}
	}

	@Override
    public void onScrollDown() {
        if (!hidden) {
            move(mFab, mFab.getBottom(), new AccelerateInterpolator());
            move(mTabStrip, -mTabStrip.getHeight(), new AccelerateInterpolator());
        }
        hidden = true;
    }

    @Override
    public void onScrollUp() {
        if (hidden) {
            move(mFab, 0, new DecelerateInterpolator());
            move(mTabStrip, 0, new DecelerateInterpolator());
        }
        hidden = false;
    }

    public void move(View v, float value, Interpolator interpolator) {
        v.animate().translationY(value).setInterpolator(interpolator).start();
    }

    @Override
    public void onClick(View v) {
        mListener.onFABClick();
    }

    @Override
    public void onCardClick(CharSequence id) {
        mListener.onCardClick(id.toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.app_name);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFeedInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("parent activity must implement Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}

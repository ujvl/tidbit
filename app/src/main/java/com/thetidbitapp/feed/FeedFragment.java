package com.thetidbitapp.feed;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.*;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.rey.material.widget.FloatingActionButton;
import com.rey.material.widget.TabPageIndicator;
import com.thetidbitapp.adap.FeedPagerAdapter;
import com.thetidbitapp.model.FBEvent;
import com.thetidbitapp.model.SessionManager;
import com.thetidbitapp.tidbit.R;
import com.thetidbitapp.util.InternetUtil;
import com.thetidbitapp.util.ParseUtil;

import java.util.Map;

public class FeedFragment extends Fragment implements FloatingActionButton.OnClickListener,
        BaseEventsFragment.OnEventListInteractionListener,
        ViewPager.OnPageChangeListener, GraphRequest.Callback {

    private final static int NUM_PAGES = 3;

    private OnFeedInteractionListener mListener;
    private FloatingActionButton mFab;
    private TabPageIndicator mTabStrip;
    private ViewPager mPager;
    private View mToolbar;
    private FeedPagerAdapter mAdapter;

    private boolean hidden;
    private final boolean[] needToRefresh = new boolean[NUM_PAGES];

    public interface OnFeedInteractionListener {
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

        mToolbar = getActivity().findViewById(R.id.toolbar);
        mAdapter = new FeedPagerAdapter(getChildFragmentManager());
        mPager = (ViewPager) root.findViewById(R.id.feed_pager);
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(this);
        mPager.setOffscreenPageLimit(NUM_PAGES - 1);

        mTabStrip = (TabPageIndicator) root.findViewById(R.id.feed_tabs);
        mTabStrip.setOnPageChangeListener(this);
        mTabStrip.setViewPager(mPager);

        mFab = (FloatingActionButton) root.findViewById(R.id.fab);
        mFab.setOnClickListener(this);

        return root;
    }

    @Override
    public void onPageSelected(int pos) {
        onScrollUp();
        if (needToRefresh[pos]) {
            ((BaseEventsFragment) getChildFragmentManager().findFragmentByTag(tag(pos))).onReload();
            needToRefresh[pos] = false;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

    @Override
    public void onPageScrollStateChanged(int state) { }

    @Override
    public void onItemsChanged(int position) {
        /** TODO Need to put in a more scalable fix here later **/
        for (int i = 0; i < needToRefresh.length; i++) {
            if (position != i) {
                needToRefresh[i] = true;
            }
        }
    }

    @Override
    public void onScrollDown() {
        if (!hidden) {
            move(mFab, mFab.getBottom(), new AccelerateInterpolator());
            //move(mToolbar, -mToolbar.getHeight(), new AccelerateInterpolator());
            move(mTabStrip, -mToolbar.getHeight(), new AccelerateInterpolator());
        }
        hidden = true;
    }

    @Override
    public void onScrollUp() {
        if (hidden) {
            move(mFab, 0, new DecelerateInterpolator());
            // move(mToolbar, 0 , new DecelerateInterpolator());
            move(mTabStrip, 0, new DecelerateInterpolator());
        }
        hidden = false;
    }

    public void move(View v, float value, Interpolator interpolator) {
        v.animate().translationY(value).setInterpolator(interpolator).start();
    }

    /**
     * Behaviour for FAB button click
     */
    @Override
    public void onClick(View v) {
        getEventListFromFacebook();
    }

    @Override
    public void onCompleted(GraphResponse response) {
        final Map<String, FBEvent> events = ParseUtil.getEventMap(response);
        new MaterialDialog.Builder(getActivity())
            .title(R.string.fb_event_chooser_title)
            .items(events.keySet().toArray(new CharSequence[0]))
            .positiveText(R.string.next)
            .negativeText(R.string.cancel)
            .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                @Override
                public boolean onSelection(MaterialDialog d, View v, int which, CharSequence option) {
                    showFoodDialog(events.get(option));
                    return true;
                }
            })
            .show();
    }

    /**
     * Shows the dialog that requests the food option
     * for the event
     * @param event Event imported from Facebook
     */
    private void showFoodDialog(final FBEvent event) {
        new MaterialDialog.Builder(getActivity())
            .title(R.string.choose_food)
            .positiveText(R.string.submit)
            .negativeText(R.string.cancel)
            .customView(R.layout.select_food, true)
            .onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    TextView v = (TextView) dialog.getCustomView().findViewById(R.id.field_food);
                    String food = v.getText().toString();
                    if (food.equals("") || food.equals("None")) {
                        Toast.makeText(getContext(), "Invalid entry!", Toast.LENGTH_LONG).show();
                    } else {
                        event.setFood(food);
                        submitNewEventToServer(event);
                    }
                }
            })
            .show();
    }

    // TODO change to adding event instead of re-fetching from server

    /**
     * Submits event to server
     * @param event
     */
    private void submitNewEventToServer(FBEvent event) {
        // TODO async task to submit event to server
        boolean success = true;

        if (!success) {
            return;
        }

        int curr = mPager.getCurrentItem();
        for (int i = 0; i < needToRefresh.length; i++) {
            if (i != curr) {
                needToRefresh[i] = true;
            }
        }

        ((BaseEventsFragment) getChildFragmentManager().findFragmentByTag(tag(curr))).onReload();
        needToRefresh[curr] = false;
    }

    /**
     * Attempts to get a list of the user's visible
     * events from his/her facebook
     */
    private void getEventListFromFacebook() {
        if (InternetUtil.isOnline(getActivity())) {
            SessionManager manager = new SessionManager(getActivity());
            AccessToken token = manager.getAccessToken();
            String path = manager.getString(getString(R.string.fb_field_id)) + "/events";

            Bundle params = new Bundle();
            params.putString(getString(R.string.fb_fields_key), getString(R.string.fb_ev_fields));

            GraphRequest request = GraphRequest.newGraphPathRequest(token, path, this);
            request.setParameters(params);
            request.executeAsync();
        }
        else {
            InternetUtil.showNoInternetSnackBar(getView(), this);
        }
    }

    @Override
    public void onCardClick(CharSequence id) {
        mListener.onCardClick(id.toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.app_name);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.app_name);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFeedInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("parent activity must implement Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Hacky way to construct fragment tag for ViewPager DO NOT EDIT...
     * Get your shit together Google and give convenient access method for this
     * @param pos position of Fragment to get tag of
     * @return tag of the fragment at pos
     */
    private String tag(int pos) {
        return "android:switcher:" + R.id.feed_pager + ":" + pos;
    }
}

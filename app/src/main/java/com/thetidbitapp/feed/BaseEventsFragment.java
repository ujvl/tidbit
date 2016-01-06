package com.thetidbitapp.feed;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thetidbitapp.adap.BaseEventAdapter;
import com.thetidbitapp.adap.FeedPagerAdapter;
import com.thetidbitapp.model.Event;
import com.thetidbitapp.tidbit.R;
import com.thetidbitapp.util.InternetUtil;

import java.util.List;

public abstract class BaseEventsFragment extends Fragment implements View.OnClickListener,
													FeedPagerAdapter.Reloadable,
													SwipeRefreshLayout.OnRefreshListener,
													BaseEventAdapter.OnInteractionListener {

	public interface OnEventListInteractionListener {
		void onScrollUp();
		void onScrollDown();
		void onCardClick(CharSequence id);
		void onItemsChanged(int position);
	}

	private OnEventListInteractionListener mListener;
	private SwipeRefreshLayout mRefresher;
	private RecyclerView mEventRecycler;
	private BaseEventAdapter mEventAdapter;
	private List<Event> mEvents;

	/**
	 * Fetches events from server
	 * @return a list of events
	 */
	public abstract List<Event> getEvents();

	/**
	 * Creates and returns an instance of AbstractEventAdapter
	 * @param events a list of events
	 * @return relevant instance of AbstractEventAdapter
	 */
	public abstract BaseEventAdapter getEventAdapter(List<Event> events);

	/**
	 * Returns the Fragment's position in the parent ViewPager
	 * @return position in ViewPager
	 */
	public abstract int getViewPagerPosition();

	public BaseEventsFragment() { }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		final View root = inflater.inflate(R.layout.fragment_events, container, false);
		mEventRecycler = (RecyclerView) root.findViewById(R.id.events_recycler);
		mEventRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
		mRefresher = (SwipeRefreshLayout) root.findViewById(R.id.tidbit_list_swipe_refresh);

		setupRecycler();
		// Load content simulation
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				root.findViewById(R.id.tidbit_progress_circle).setVisibility(View.GONE);
				root.findViewById(R.id.tidbit_list_swipe_refresh).setVisibility(View.VISIBLE);
			}
		}, 2500);

		mEventRecycler.addOnScrollListener(new OnRecyclerScrollListener());
		mRefresher.setOnRefreshListener(this);
		mRefresher.setColorSchemeResources(R.color.sec_darker);
		mRefresher.setProgressViewOffset(false, 0, 250);

		return root;
	}

	@Override
	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mRefresher.setRefreshing(false);
				setupRecycler();
				onItemsChanged();
			}
		}, 2500);
	}

	@Override
	public void onReload() {
		getView().findViewById(R.id.tidbit_progress_circle).setVisibility(View.VISIBLE);
		getView().findViewById(R.id.tidbit_list_swipe_refresh).setVisibility(View.GONE);
		setupRecycler();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				getView().findViewById(R.id.tidbit_progress_circle).setVisibility(View.GONE);
				getView().findViewById(R.id.tidbit_list_swipe_refresh).setVisibility(View.VISIBLE);
			}
		}, 2500);
	}

	/**
	 * Sets up the RecyclerView of the fragment
	 */
	private void setupRecycler() {
		mEvents = getEvents();
		mEventAdapter = getEventAdapter(mEvents);
		mEventAdapter.setOnItemClickListener(this);
		mEventAdapter.setOnItemsChangeListener(this);
		mEventRecycler.setAdapter(mEventAdapter);
	}

	@Override
	public void onClick(View view) {
		int itemPosition = mEventRecycler.getChildAdapterPosition(view);
		if (itemPosition != RecyclerView.NO_POSITION) {
			mListener.onCardClick(mEvents.get(itemPosition).id());
		}
	}

	@Override
	public void onItemsChanged() {
		mListener.onItemsChanged(getViewPagerPosition());
	}

	@Override
	public void onNoConnectivityReported() {
		InternetUtil.showNoInternetSnackBar(getView(), this);
	}

	@Override
	public void onAttach(Context c) {
		super.onAttach(c);
		try {
			mListener = (OnEventListInteractionListener) getParentFragment();
		} catch (ClassCastException e) {
			throw new ClassCastException("parent fragment must implement Listener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * Custom OnRecyclerScrollListener that fires context listener callbacks
	 */
	private class OnRecyclerScrollListener extends RecyclerView.OnScrollListener {

		private final int DELTA_SCROLL = 20;

		@Override
		public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

			if (((LinearLayoutManager) recyclerView.getLayoutManager())
					.findFirstVisibleItemPosition() == 0) {
				super.onScrolled(recyclerView, dx, dy);
			}
			else if (dy <= -DELTA_SCROLL) {
				mListener.onScrollUp();
			}
			else if (dy > DELTA_SCROLL) {
				mListener.onScrollDown();
			}
			super.onScrolled(recyclerView, dx, dy);
		}

	}

}

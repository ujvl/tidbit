package com.thetidbitapp.feed;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thetidbitapp.adap.AbstractEventAdapter;
import com.thetidbitapp.model.Event;
import com.thetidbitapp.model.SessionManager;
import com.thetidbitapp.tidbit.R;
import com.thetidbitapp.view.FixedSwipeRefreshLayout;

import java.util.List;

public abstract class AbstractEventsFragment extends Fragment implements View.OnClickListener,
													 	 SwipeRefreshLayout.OnRefreshListener {

    public interface OnEventListInteractionListener {
        public void onScrollUp();
        public void onScrollDown();
        public void onCardClick(CharSequence id);
    }

	private FixedSwipeRefreshLayout mRefresher;
	private RecyclerView mEventRecycler;
    private AbstractEventAdapter mEventAdapter;
    private List<Event> mEvents;

    private OnEventListInteractionListener mListener;

	private final View.OnClickListener mOnGoingClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {

		}
	};

    public AbstractEventsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_events, container, false);
		mEventRecycler = (RecyclerView) root.findViewById(R.id.events_recycler);
		mEventRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
		mRefresher = (FixedSwipeRefreshLayout) root.findViewById(R.id.tidbit_list_swipe_refresh);

        // Load content (Simulation)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setupList();
                root.findViewById(R.id.tidbit_progress_circle).setVisibility(View.GONE);
                root.findViewById(R.id.tidbit_list_swipe_refresh).setVisibility(View.VISIBLE);
            }
        }, 2500);

		mEventRecycler.addOnScrollListener(new OnRecyclerScrollListener());
		mRefresher.setOnRefreshListener(this);
        mRefresher.setColorSchemeResources(R.color.sec_brighter);
        mRefresher.setProgressViewOffset(false, 0, 250);

        return root;
    }

    @Override
    public void onRefresh() {
        setupList();
		Log.e("EVENTSLISTFRAG", new SessionManager(getActivity()).getLocation().toString());
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mRefresher.setRefreshing(false);
			}
		}, 2500);
    }

	@Override
	public void onClick(final View view) {
		int itemPosition = mEventRecycler.getChildAdapterPosition(view);
		mListener.onCardClick(mEvents.get(itemPosition).id());
	}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
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
	 * Fetches events from server
	 * @return a list of events
	 */
	public abstract List<Event> getEvents();

	/**
	 * Creates and returns an instance of AbstractEventAdapter
	 * @param events a list of events
	 * @param context application context
	 * @return contextually relevant instance of AbstractEventAdapter
	 */
	public abstract AbstractEventAdapter getEventAdapter(List<Event> events, Context context);

	/**
	 * Sets up the RecyclerView of the fragment
	 */
	private void setupList() {
		mEvents = getEvents();
		mEventAdapter = getEventAdapter(mEvents, getActivity());
		mEventAdapter.setOnItemClickListener(this);
		mEventRecycler.setAdapter(mEventAdapter);
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
package com.thetidbitapp.feed;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.thetidbitapp.adap.EnablableCardAdapter;
import com.thetidbitapp.adap.EventAdapter;
import com.thetidbitapp.model.Tidbit;
import com.thetidbitapp.tidbit.R;
import com.thetidbitapp.view.FixedSwipeRefreshLayout;

import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;

public abstract class EventListFragment extends Fragment implements
        								SwipeRefreshLayout.OnRefreshListener,
										AdapterView.OnItemClickListener {

    public interface OnEventListInteractionListener {
        public void onScrollUp();
        public void onScrollDown();
        public void onCardClick(CharSequence id);
    }

    /*
        View objects
     */
    //private CardListView mTidbitList;
	private RecyclerView mEventRecycler;

    private EventAdapter mEventAdapter;
    private List<Tidbit> mEvents;
    private FixedSwipeRefreshLayout mRefresher;

    /*
        Control objects
     */
    private int mLastFirstVisibleItem;
    private OnEventListInteractionListener mListener;

    public abstract List<Tidbit> getCards();

    public EventListFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_events, container, false);
		mEventRecycler = (RecyclerView) root.findViewById(R.id.events_recycler);
        mRefresher = (FixedSwipeRefreshLayout) root.findViewById(R.id.tidbit_list_swipe_refresh);

		mEventRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
		//mEventRecycler.addOnScrollListener(this);

        // Load content (Simulation)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setupList();
                root.findViewById(R.id.tidbit_progress_circle).setVisibility(View.GONE);
                root.findViewById(R.id.tidbit_list_swipe_refresh).setVisibility(View.VISIBLE);
            }
        }, 2500);

//        mTidbitList.setOnItemClickListener(this);
//        mTidbitList.setOnScrollListener(this);
		mRefresher.setOnRefreshListener(this);

        mRefresher.setColorSchemeResources(R.color.sec_brighter);
        mRefresher.setProgressViewOffset(false, 0, 250);

        return root;
    }

    private void setupList() {
        mEvents = getCards();
        mEventAdapter = new EventAdapter(mEvents, getActivity());
		mEventRecycler.setAdapter(mEventAdapter);
    }

    /*@Override
    public void onScrollStateChanged(AbsListView view, int scrollState) { }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        final int currentFirstVisibleItem = mTidbitList.getFirstVisiblePosition();

        if (currentFirstVisibleItem > mLastFirstVisibleItem) {
            mListener.onScrollDown();
        } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
            mListener.onScrollUp();
        }

        mLastFirstVisibleItem = currentFirstVisibleItem;
    }*/

    @Override
    public void onRefresh() {
		mEventAdapter.setAllItemsEnabled(false);
        setupList();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefresher.setRefreshing(false);
				mEventAdapter.setAllItemsEnabled(true);
            }
        }, 2500);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mListener.onCardClick(((TextView) view.findViewById(R.id.card_id)).getText());
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

}

package com.thetidbitapp.core;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.thetidbitapp.adap.EnablableCardAdapter;
import com.thetidbitapp.tidbit.R;
import com.thetidbitapp.view.FixedSwipeRefreshLayout;

import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardListView;

public abstract class EventListFragment extends Fragment implements AbsListView.OnScrollListener,
        SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    public interface OnEventListInteractionListener {
        public void onScrollUp();
        public void onScrollDown();
        public void onCardClick(CharSequence id);
    }

    /*
        View objects
     */
    private CardListView mTidbitList;
    private EnablableCardAdapter mCardAdapter;
    private List<Card> mCards;
    private FixedSwipeRefreshLayout mRefresher;

    /*
        Control objects
     */
    private int mLastFirstVisibleItem;
    private OnEventListInteractionListener mListener;
    private static final String SORT_PARAM = "sort_type";

    public abstract List<Card> getCards();

    public EventListFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i("HELLO ONCREATEVIEW", "ASDASDASD");

        final View root = inflater.inflate(R.layout.fragment_events, container, false);
        mTidbitList = (CardListView) root.findViewById(R.id.tidbits_list);
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

        mTidbitList.setOnItemClickListener(this);
        mTidbitList.setOnScrollListener(this);
        mRefresher.setOnRefreshListener(this);

        mRefresher.setColorSchemeResources(R.color.sec_brighter);
        mRefresher.setProgressViewOffset(false, 0, 250);

        return root;
    }

    private void setupList() {
        mCards = getCards();
        mCardAdapter = new EnablableCardAdapter(getActivity(), mCards);
        mTidbitList.setAdapter(mCardAdapter);
    }

    @Override
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
    }

    @Override
    public void onRefresh() {
        mCardAdapter.setAllItemsEnabled(false);
        setupList();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefresher.setRefreshing(false);
                mCardAdapter.setAllItemsEnabled(true);
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

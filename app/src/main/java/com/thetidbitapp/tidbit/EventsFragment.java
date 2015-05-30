package com.thetidbitapp.tidbit;

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

import com.thetidbitapp.adap.EnablableCardAdapter;
import com.thetidbitapp.model.Tidbit;
import com.thetidbitapp.adap.TidbitCard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardListView;

public class EventsFragment extends Fragment implements AbsListView.OnScrollListener,
        SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private static final int[] REFRESH_COLORS = new int[] {
            R.color.sec_brighter, R.color.sec_bright, R.color.secondary
    };

    /*
        View objects
     */
    private CardListView mTidbitList;
    private EnablableCardAdapter mCardAdapter;
    private List<Card> mCards;
    private SwipeRefreshLayout mRefresher;

    /*
        Control objects
     */
    private SortType mSortType;
    private int mLastFirstVisibleItem;
    private OnEventsInteractionListener mListener;
    private static final String SORT_PARAM = "sort_type";

    public enum SortType { UPCOMING, POPULAR; }

    public interface OnEventsInteractionListener {
        public void onScrollUp();
        public void onScrollDown();
    }

    public static EventsFragment newInstance(SortType sortType) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putSerializable(SORT_PARAM, sortType);
        fragment.setArguments(args);
        return fragment;
    }

    public EventsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mSortType = (SortType) getArguments().getSerializable(SORT_PARAM);
            mCards = new ArrayList<>();
            if (mSortType == SortType.POPULAR) {
                for (int i = 0; i < 15; i++)
                    mCards.add(new TidbitCard(getActivity(),
                            new Tidbit(0, "TEDxBerkeley", new Date(), "Evans hall, UC Berkeley, CA", "Sliver", 123)));
            }
            else {
                for (int i = 0; i < 15; i++)
                    mCards.add(new TidbitCard(getActivity(),
                            new Tidbit(0, "Engineering Week", new Date(), "Doe Library, VA", "Sushi", 293)));
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_events, container, false);
        mTidbitList = (CardListView) root.findViewById(R.id.tidbits_list);
        mRefresher = (SwipeRefreshLayout) root.findViewById(R.id.tidbit_list_swipe_refresh);

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

        mRefresher.setColorSchemeResources(REFRESH_COLORS);
        mRefresher.setProgressViewOffset(false, 0, 250);

        return root;
    }

    private void setupList() {
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
        // TODO go to tidbit
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnEventsInteractionListener) getParentFragment();
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

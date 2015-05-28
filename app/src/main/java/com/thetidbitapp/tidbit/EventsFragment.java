package com.thetidbitapp.tidbit;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.thetidbitapp.model.Tidbit;
import com.thetidbitapp.model.TidbitAdapter;

import java.util.ArrayList;
import java.util.Date;

public class EventsFragment extends Fragment implements AbsListView.OnScrollListener,
        SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private ListView mTidbitList;
    private TidbitAdapter mTidbitAdapter;
    private ArrayList<Tidbit> mTidbits;
    private SwipeRefreshLayout mRefresher;

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
            mTidbits = new ArrayList<>();
            if (mSortType == SortType.POPULAR) {
                mTidbits.add(new Tidbit("Ma burfday", new Date(), "Evans hall, UC Berkeley, CA", "Sliver", 123));
                mTidbits.add(new Tidbit("TEDxBerkeley", new Date(), "Soda hall, UC Berkeley, CA", "Top Dog", 234));
                for (int i = 0; i < 15; i++)
                    mTidbits.add(new Tidbit("Lol free food", new Date(), "Wheeler hall, UC Berkeley, CA", "Other", 53));
            }
            else {
                mTidbits.add(new Tidbit("Google Tech talk", new Date(), "Wheeler hall, UC Berkeley, CA", "Top Dog", 63));
                mTidbits.add(new Tidbit("420 free dope", new Date(), "Memorial Glade, My ass, CA", "Sliver", 27));
                mTidbits.add(new Tidbit("Engineering Week", new Date(), "Dope hall, VA", "Sushi", 293));
                for (int i = 0; i < 15; i++)
                    mTidbits.add(new Tidbit("Free ReDbUlL", new Date(), "Cory hall, Moon", "Pizza", 46));
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_events, container, false);
        mTidbitList = (ListView) root.findViewById(R.id.tidbits_list);
        mRefresher = (SwipeRefreshLayout) root.findViewById(R.id.tidbit_list_swipe_refresh);

        // Load content
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

        mRefresher.setColorSchemeResources(R.color.sec_bright, R.color.secondary, R.color.sec_darker);
        mRefresher.setProgressViewOffset(false, 0, 250);

        return root;
    }

    private void setupList() {
        mTidbitAdapter = new TidbitAdapter(getActivity(), mTidbits);
        mTidbitList.setAdapter(mTidbitAdapter);
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
        mTidbitAdapter.setAllItemsEnabled(false);
        setupList();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefresher.setRefreshing(false);
                mTidbitAdapter.setAllItemsEnabled(true);
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

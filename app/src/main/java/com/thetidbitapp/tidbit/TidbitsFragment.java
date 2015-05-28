package com.thetidbitapp.tidbit;

import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;
import com.thetidbitapp.model.Tidbit;
import com.thetidbitapp.model.TidbitAdapter;

import java.util.ArrayList;
import java.util.Date;

public class TidbitsFragment extends Fragment implements AbsListView.OnScrollListener,
        SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener,
        FloatingActionButton.OnClickListener {

    private View mRoot;
    private ListView mTidbitList;
    private TidbitAdapter mTidbitAdapter;
    private ArrayList<Tidbit> mTidbits;
    private FloatingActionButton mFab;
    private SwipeRefreshLayout mRefresher;

    private int mLastFirstVisibleItem;
    private OnFragmentInteractionListener mListener;
    private static final String SORT_PARAM = "sort_type";
    private SortType mSortType;

    public enum SortType { UPCOMING, POPULAR; }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    public static TidbitsFragment newInstance(SortType sortType) {
        TidbitsFragment fragment = new TidbitsFragment();
        Bundle args = new Bundle();
        args.putSerializable(SORT_PARAM, sortType);
        fragment.setArguments(args);
        return fragment;
    }

    public TidbitsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSortType = (SortType) getArguments().getSerializable(SORT_PARAM);
            mTidbits = new ArrayList<>();
            mTidbits.add(new Tidbit("Ma burfday", new Date(), "Evans hall, UC Berkeley, CA", "Sliver", 123));
            mTidbits.add(new Tidbit("TEDxBerkeley", new Date(), "Soda hall, UC Berkeley, CA", "Top Dog", 234));
            mTidbits.add(new Tidbit("Lol free food", new Date(), "Wheeler hall, UC Berkeley, CA", "Other", 53));
            mTidbits.add(new Tidbit("Google Tech talk", new Date(), "Wheeler hall, UC Berkeley, CA", "Top Dog", 63));
            mTidbits.add(new Tidbit("420 free dope", new Date(), "Memorial Glade, My ass, CA", "Sliver", 27));
            mTidbits.add(new Tidbit("Engineering Week", new Date(), "Dope hall, VA", "Sushi", 293));
            mTidbits.add(new Tidbit("Free ReDbUlL", new Date(), "Cory hall, Moon", "Pizza", 46));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_tidbits, container, false);
        mTidbitList = (ListView) mRoot.findViewById(R.id.tidbits_list);
        mRefresher = (SwipeRefreshLayout) mRoot.findViewById(R.id.tidbit_list_swipe_refresh);
        mFab = (FloatingActionButton) mRoot.findViewById(R.id.fab);

        // Load content
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setupList();
                mRoot.findViewById(R.id.tidbit_progress_circle).setVisibility(View.GONE);
                mRoot.findViewById(R.id.tidbit_list_swipe_refresh).setVisibility(View.VISIBLE);
            }
        }, 2500);

        mTidbitList.setOnItemClickListener(this);
        mTidbitList.setOnScrollListener(this);
        mRefresher.setOnRefreshListener(this);
        mFab.setOnClickListener(this);

        mRefresher.setColorSchemeResources(R.color.theme_sec_bright, R.color.theme_sec, R.color.theme_sec_darker);

        return mRoot;
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
        View button = mRoot.findViewById(R.id.map_button_ripple);

        if (currentFirstVisibleItem > mLastFirstVisibleItem) {
            button.animate().translationY(button.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
            mFab.animate().translationY(mFab.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
        } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
            button.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
            mFab.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
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
    public void onClick(View v) {

    }

    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    /*@Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

}

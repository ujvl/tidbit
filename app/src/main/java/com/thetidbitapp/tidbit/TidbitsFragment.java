package com.thetidbitapp.tidbit;

import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;
import com.thetidbitapp.model.Tidbit;
import com.thetidbitapp.model.TidbitAdapter;

import java.util.ArrayList;
import java.util.Date;

public class TidbitsFragment extends Fragment {

    private ListView mTidbitList;

    public enum SortType {
        UPCOMING, POPULAR;
    }

    private static final String SORT_PARAM = "sort_type";

    private OnFragmentInteractionListener mListener;
    private ArrayList<Tidbit> mTidbits;

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
            SortType howToSort = (SortType) getArguments().getSerializable(SORT_PARAM);
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

        final View root = inflater.inflate(R.layout.fragment_tidbits, container, false);

        // Handle item clicks
        mTidbitList = (ListView) root.findViewById(R.id.tidbits_list);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setupList();
                root.findViewById(R.id.tidbit_progress_circle).setVisibility(View.GONE);
                root.findViewById(R.id.tidbit_list_container).setVisibility(View.VISIBLE);
            }
        }, 2500);

        mTidbitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO go to tidbit
            }
        });

        // Set up FAB
        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.attachToListView(mTidbitList);
        fab.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Set up Swipe to Refresh
        final SwipeRefreshLayout refresher = (SwipeRefreshLayout) root.findViewById(R.id.tidbit_list_swipe_refresh);
        refresher.setColorSchemeResources(R.color.theme_sec_bright, R.color.theme_sec, R.color.theme_sec_dark);
        refresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setupList();
                        refresher.setRefreshing(false);
                    }
                }, 2500);
            }
        });

        return root;
    }

    private void setupList() {
        mTidbitList.setAdapter(new TidbitAdapter(getActivity(), mTidbits));
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

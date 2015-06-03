package com.thetidbitapp.tidbit;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class EventMoreInfoFragment extends Fragment {

    private static final String EVENT_ID = "event id";

    private String mEventId;

    private OnEventInteractionListener mListener;

    public static EventMoreInfoFragment newInstance(String id) {
        EventMoreInfoFragment fragment = new EventMoreInfoFragment();
        Bundle args = new Bundle();
        args.putString(EVENT_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    public EventMoreInfoFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEventId = getArguments().getString(EVENT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_event_more_info, container, false);
        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnEventInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("activity must implement listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}

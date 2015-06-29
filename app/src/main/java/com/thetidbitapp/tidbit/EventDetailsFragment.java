package com.thetidbitapp.tidbit;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.thetidbitapp.state.OnEventInteractionListener;


public class EventDetailsFragment extends Fragment {

    private static final String EVENT_ID = "event id";

    private String mEventId;

    private OnEventInteractionListener mListener;

    public static EventDetailsFragment newInstance(String id) {
        EventDetailsFragment fragment = new EventDetailsFragment();
        Bundle args = new Bundle();
        args.putString(EVENT_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    public EventDetailsFragment() { }

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
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_event_details, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_going) {
			mListener.onChooseGoing(mEventId);
			return true;
		}
		if (item.getItemId() == R.id.action_not_going) {
			mListener.onChooseNotGoing(mEventId);
			return true;
		}
		return super.onOptionsItemSelected(item);
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
	public void onResume() {
		super.onResume();
		getActivity().setTitle(R.string.event_details);
	}

	@Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}

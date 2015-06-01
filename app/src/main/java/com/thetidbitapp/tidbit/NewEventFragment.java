package com.thetidbitapp.tidbit;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewEventFragment extends Fragment {

    private OnSubmitListener mListener;

    public NewEventFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_event, container, false);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_new_event, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onSubmit();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSubmitListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Parent must implement listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSubmitListener {
        public void onSubmit();
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("New Event");
    }
}

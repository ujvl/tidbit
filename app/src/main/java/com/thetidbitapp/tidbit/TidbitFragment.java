package com.thetidbitapp.tidbit;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.thetidbitapp.model.Tidbit;
import com.thetidbitapp.model.TidbitAdapter;

import java.util.ArrayList;
import java.util.Date;

public class TidbitFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ArrayList<Tidbit> tidbits;

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    // TODO: Rename and change types of parameters
    public static TidbitFragment newInstance(String param1, String param2) {
        TidbitFragment fragment = new TidbitFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    public TidbitFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {

//        ((MainActivity) getActivity()).getSupportActionBar().show();
        super.onCreate(savedInstanceState);

        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/

        tidbits = new ArrayList<>();
        tidbits.add(new Tidbit("Ma burfday", new Date(), "Evans hall"));
        tidbits.add(new Tidbit("TEDxBerkeley", new Date(), "Soda hall"));
        tidbits.add(new Tidbit("Lol free food", new Date(), "Wheeler hall"));
        tidbits.add(new Tidbit("Google Tech talk", new Date(), "Wheeler hall"));
        tidbits.add(new Tidbit("Facebook tech talk", new Date(), "Memorial Glade"));
        tidbits.add(new Tidbit("Engineering Week", new Date(), "Dope hall"));
        tidbits.add(new Tidbit("Free ReDbUlL", new Date(), "Cory hall"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tidbit, container, false);
        ListView tidbitListView = (ListView) rootView.findViewById(R.id.tidbits_list);
        tidbitListView.setAdapter(new TidbitAdapter(getActivity(), tidbits));
        return rootView;
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

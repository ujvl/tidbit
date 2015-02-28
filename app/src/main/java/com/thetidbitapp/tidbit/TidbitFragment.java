package com.thetidbitapp.tidbit;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.thetidbitapp.model.Tidbit;
import com.thetidbitapp.model.TidbitAdapter;
import com.thetidbitapp.tidbit.dummy.DummyContent;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class TidbitFragment extends ListFragment {

    public interface OnFragmentInteractionListener {

        public void onTidBitClicked(String id);

    }

    /*// TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;*/

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types of parameters
    public static TidbitFragment newInstance(String param1, String param2) {
        TidbitFragment fragment = new TidbitFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TidbitFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        ((MainActivity)getActivity()).getSupportActionBar().show();
        super.onCreate(savedInstanceState);

        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/

        ArrayList<Tidbit> tidbits = new ArrayList<Tidbit>();
        tidbits.add(new Tidbit("Ma burfday", new GregorianCalendar(), "Evans hall", true));
        tidbits.add(new Tidbit("TEDxBerkeley", new GregorianCalendar(), "Soda hall", false));
        tidbits.add(new Tidbit("Lol free food", new GregorianCalendar(), "Wheeler hall", true));
        tidbits.add(new Tidbit("Google Tech talk", new GregorianCalendar(), "Wheeler hall", true));
        tidbits.add(new Tidbit("Facebook tech talk", new GregorianCalendar(), "Memorial Glade", true));
        tidbits.add(new Tidbit("Engineering Week", new GregorianCalendar(), "Dope hall", true));
        tidbits.add(new Tidbit("Free ReDbUlL", new GregorianCalendar(), "Cory hall", true));
        setListAdapter(new TidbitAdapter(getActivity(), tidbits));

    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        return rootView;

    }*/

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


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onTidBitClicked(DummyContent.ITEMS.get(position).id);
        }
    }
}

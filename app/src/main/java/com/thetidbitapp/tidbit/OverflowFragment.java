package com.thetidbitapp.tidbit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OverflowFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OverflowFragment() { }

    public static OverflowFragment newInstance(String param1, String param2) {
        OverflowFragment fragment = new OverflowFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_overflow, container, false);

        setText(rootView, R.id.ov_item_profile, R.string.profile_setting);
        setText(rootView, R.id.ov_item_notif, R.string.notif_setting);
        setText(rootView, R.id.ov_item_share, R.string.share);
        setText(rootView, R.id.ov_item_rate, R.string.rate);
        setText(rootView, R.id.ov_item_fb_like, R.string.like);
        setText(rootView, R.id.ov_item_feedback, R.string.feedback);
        setText(rootView, R.id.ov_item_about, R.string.about);
        setText(rootView, R.id.ov_item_rules, R.string.rules);
        setText(rootView, R.id.ov_item_contact, R.string.contact);
        setText(rootView, R.id.ov_item_sign_out, R.string.sign_out);

        return rootView;
    }

    private void setText(View root, int viewId, int strId) {
        ((TextView) (root.findViewById(viewId)).findViewById(R.id.overflow_item_text))
                .setText(getActivity().getText(strId));
    }

    private void setListener(View root, int viewId, View.OnClickListener l) {
        root.findViewById(viewId).setOnClickListener(l);
    }


}
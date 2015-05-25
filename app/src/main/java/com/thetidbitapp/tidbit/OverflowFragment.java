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

        setText(rootView, R.id.ov_item_profile, "Profile");
        setText(rootView, R.id.ov_item_notif, "Notifications");
        setText(rootView, R.id.ov_item_share, "Share Tidbit");
        setText(rootView, R.id.ov_item_rate, "Rate Tidbit");
        setText(rootView, R.id.ov_item_fb_like, "Like us on Facebook");
        setText(rootView, R.id.ov_item_feedback, "Give us feedback");
        setText(rootView, R.id.ov_item_about, "About");
        setText(rootView, R.id.ov_item_rules, "Rules");
        setText(rootView, R.id.ov_item_contact, "Contact us");

        return rootView;
    }

    private static void setText(View root, int viewId, String text) {
        ((TextView) (root.findViewById(viewId)).findViewById(R.id.overflow_item_text)).setText(text);
    }

    private static void setListener(View root, int viewId, View.OnClickListener l) {
        root.findViewById(viewId).setOnClickListener(l);
    }


}

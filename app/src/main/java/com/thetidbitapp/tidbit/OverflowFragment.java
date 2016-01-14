package com.thetidbitapp.tidbit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.thetidbitapp.model.SessionManager;
import com.thetidbitapp.state.OnLogoutListener;

public class OverflowFragment extends Fragment {

    public OverflowFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_overflow, container, false);

        setText(root, R.id.ov_item_profile, R.string.profile_setting);
        setText(root, R.id.ov_item_notif, R.string.notif_setting);
        setText(root, R.id.ov_item_share, R.string.share);
        setText(root, R.id.ov_item_rate, R.string.rate);
        setText(root, R.id.ov_item_fb_like, R.string.like);
        setText(root, R.id.ov_item_feedback, R.string.feedback);
        setText(root, R.id.ov_item_about, R.string.about);
        setText(root, R.id.ov_item_rules, R.string.rules);
        setText(root, R.id.ov_item_contact, R.string.contact);
        setText(root, R.id.ov_item_sign_out, R.string.sign_out);

        setListener(root, R.id.ov_item_sign_out, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                new SessionManager(getActivity()).setLoggedIn(false);
                ((OnLogoutListener) getActivity()).onLogout();
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.settings_heading);
    }

    private void setText(View root, int viewId, int strId) {
        ((TextView) (root.findViewById(viewId)).findViewById(R.id.overflow_item_text))
                .setText(getText(strId));
    }

    private static void setListener(View root, int viewId, View.OnClickListener l) {
        root.findViewById(viewId).setOnClickListener(l);
    }

}

package com.thetidbitapp.tidbit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.thetidbitapp.model.SessionManager;

import java.util.Arrays;

public class FBLoginFragment extends Fragment {

    private OnLoginListener mListener;
    private CallbackManager callbackManager;

    public FBLoginFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_fblogin, container, false);
        final LoginManager manager = LoginManager.getInstance();

        Button loginButton = (Button) rootView.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                manager.logInWithReadPermissions(FBLoginFragment.this, Arrays.asList("public_profile"));
            }
        });

        callbackManager = CallbackManager.Factory.create();
        manager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                rootView.findViewById(R.id.login_layout).setVisibility(View.GONE);
                new SessionManager(getActivity()).setLoggedIn(true);
                mListener.onLogin();

            }

            @Override
            public void onCancel() { }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getActivity(), "There was an error logging in", Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnLoginListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnLoginListener {
        public void onLogin();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

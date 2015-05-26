package com.thetidbitapp.tidbit;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class FBLoginFragment extends Fragment {

    private OnLoginListener mListener;

    public FBLoginFragment() { }

    public static FBLoginFragment newInstance() {
        FBLoginFragment fragment = new FBLoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fblogin, container, false);

        LoginButton loginButton = (LoginButton) rootView.findViewById(R.id.login_button);
        loginButton.setFragment(this);

        Log.e("","???????????????????");

        CallbackManager callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getActivity(), "YOLO", Toast.LENGTH_LONG).show();

                Log.e("", "FUCKFUCKFUCK");
                mListener.onLogin();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getActivity(), "NO YOLO", Toast.LENGTH_LONG).show();
                Log.e("", "FUCKFUCKFUCKasdfasdf");
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                Log.e("", "FUCKFUCKFUCKasdfasdfasdfasdfsdf");
            }
        });

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(getActivity(), "YOLO", Toast.LENGTH_LONG).show();

                        Log.e("", "FUCKFUCKFUCK");
                        mListener.onLogin();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getActivity(), "NO YOLO", Toast.LENGTH_LONG).show();
                        Log.e("", "FUCKFUCKFUCKasdfasdf");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                        Log.e("", "FUCKFUCKFUCKasdfasdfasdfasdfsdf");
                    }
                });

        Log.e("","ok");

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

}

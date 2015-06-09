package com.thetidbitapp.tidbit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.thetidbitapp.model.SessionManager;

import org.json.JSONObject;

import java.util.Arrays;

public class FBLoginFragment extends Fragment implements FacebookCallback<LoginResult>,
												GraphRequest.GraphJSONObjectCallback,
												View.OnClickListener {

    private OnLoginListener mListener;
    private CallbackManager callbackManager;
	private LoginManager mLoginManager;
	private View mRootView;

	private static final String FIELDS_KEY = "fields";

    public FBLoginFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_fblogin, container, false);
        mLoginManager = LoginManager.getInstance();

        Button loginButton = (Button) mRootView.findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);

        callbackManager = CallbackManager.Factory.create();
		mLoginManager.registerCallback(callbackManager, this);

        return mRootView;
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

	@Override
	public void onSuccess(LoginResult loginResult) {

		Bundle params = new Bundle();
		GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), this);
		params.putString(FIELDS_KEY, getActivity().getString(R.string.fb_fields));
		request.setParameters(params);
		request.executeAsync();

	}

	@Override
	public void onCancel() {

	}

	@Override
	public void onError(FacebookException e) {

	}


	@Override
	public void onCompleted(JSONObject result, GraphResponse response) {

		mRootView.findViewById(R.id.login_layout).setVisibility(View.GONE);
		new SessionManager(getActivity()).setLoggedIn(true);
		mListener.onLogin(result);

	}

	@Override
	public void onClick(View v) {
		mLoginManager.logInWithReadPermissions(
				FBLoginFragment.this,
				Arrays.asList("public_profile", "email"));
	}

	public interface OnLoginListener {
        public void onLogin(JSONObject result);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

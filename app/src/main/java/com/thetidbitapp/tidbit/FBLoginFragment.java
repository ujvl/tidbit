package com.thetidbitapp.tidbit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

	public interface OnLoginListener {
		public void onLogin(JSONObject result);
	}

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

	/**
	 * Fired on completion of login request,
	 * subsequently calls Graph API
	 * @param loginResult result containing access token
	 */
	@Override
	public void onSuccess(LoginResult loginResult) {
		Bundle params = new Bundle();
		new SessionManager(getActivity()).setAccessToken(loginResult.getAccessToken());
		GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), this);
		params.putString(FIELDS_KEY, getActivity().getString(R.string.fb_field_result_keys));
		request.setParameters(params);
		request.executeAsync();
	}

	@Override
	public void onCancel() {
		Snackbar.make(getView(), getString(R.string.connect_error), Snackbar.LENGTH_SHORT).show();
	}

	@Override
	public void onError(FacebookException e) {
		e.printStackTrace();
	}

	/**
	 * Fired on completion of the graph API request
	 * @param result result fetched from facebook
	 * @param response associated response
	 */
	@Override
	public void onCompleted(JSONObject result, GraphResponse response) {
		mRootView.findViewById(R.id.login_layout).setVisibility(View.GONE);
		new SessionManager(getActivity()).setLoggedIn(true);
		mListener.onLogin(result);
	}


	/**
	 * Fired on click of the Login with facebook button
	 * @param v view clicked
	 */
	@Override
	public void onClick(View v) {
		mLoginManager.logInWithReadPermissions(
			FBLoginFragment.this,
			Arrays.asList(getString(R.string.fb_field_permissions).split(","))
		);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
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
}

package com.thetidbitapp.tidbit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.thetidbitapp.model.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

public class InitialActivity extends AppCompatActivity implements FBLoginFragment.OnLoginListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_initial);
		FacebookSdk.sdkInitialize(getApplicationContext());

		if (new SessionManager(this).isLoggedIn()) {
			proceedToApp();
		}

		FBLoginFragment login = (savedInstanceState == null) ? new FBLoginFragment() :
				(FBLoginFragment) getSupportFragmentManager().findFragmentById(R.id.container_initial);
		getSupportFragmentManager().beginTransaction().replace(R.id.container_initial, login).commit();

	}

	@Override
	public void onLogin(JSONObject response) {

		try {
			SessionManager sessionManager = new SessionManager(this);
			String[] fields = getString(R.string.fb_field_result_keys).split(",");
			for (String field : fields) {
				sessionManager.editor().putString(field, response.getString(field)).apply();
			}
			proceedToApp();
		}
		catch (JSONException e) {
			Log.e("InitialActivity", "Error finding fields");
		}
	}

	private void proceedToApp() {
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}

}

package com.thetidbitapp.tidbit;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;

public class InitialActivity extends ActionBarActivity implements FBLoginFragment.OnLoginListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        FacebookSdk.sdkInitialize(getApplicationContext());

        // Checks if user is already logged in -- then proceed to app directly
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                if (newAccessToken != null) {
                    onLogin();
                }
            }
        };

        // Launch the login fragment otherwise
        FBLoginFragment login = (savedInstanceState == null)? new FBLoginFragment() :
                (FBLoginFragment) getSupportFragmentManager().findFragmentById(R.id.container_initial);
        getSupportFragmentManager().beginTransaction().add(R.id.container_initial, login).commit();

    }

    @Override
    public void onLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}

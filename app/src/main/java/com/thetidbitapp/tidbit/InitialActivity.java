package com.thetidbitapp.tidbit;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.thetidbitapp.model.SessionManager;

public class InitialActivity extends ActionBarActivity implements FBLoginFragment.OnLoginListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        FacebookSdk.sdkInitialize(getApplicationContext());

        Log.e("initial", new SessionManager(this).isLoggedIn() + " ----");
        if (new SessionManager(this).isLoggedIn()) {
            onLogin();
        }

        FBLoginFragment login = (savedInstanceState == null) ? new FBLoginFragment() :
                (FBLoginFragment) getSupportFragmentManager().findFragmentById(R.id.container_initial);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_initial, login).commit();

    }

    @Override
    public void onLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}

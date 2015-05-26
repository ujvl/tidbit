package com.thetidbitapp.tidbit;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


public class InitialActivity extends ActionBarActivity implements FBLoginFragment.OnLoginListener {

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.fragment_fblogin);

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);

        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("", "FUCKFUCKFUCK");
                onLogin();
            }

            @Override
            public void onCancel() {
                Log.e("", "FUCKFUCKFUCKasdfasdf");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e("", "FUCKFUCKFUCKasdfasdfasdfasdfsdf");
            }
        });

        // check if logged in -- go to main; otherwise go to login TODO

        FBLoginFragment login;
        if (savedInstanceState == null) {
            login = new FBLoginFragment();
        }
        else {
            login = (FBLoginFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.container_initial);
        }
        //getSupportFragmentManager().beginTransaction().add(R.id.container_initial, login).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_initial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

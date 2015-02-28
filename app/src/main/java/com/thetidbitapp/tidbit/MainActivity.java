package com.thetidbitapp.tidbit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

/**
 * A login screen that offers login via email/password.
 */
public class MainActivity extends ActionBarActivity implements LoginFragment.OnLoginListener {

    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            loginFragment = new LoginFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, loginFragment)
                    .commit();
        }
        else {
            loginFragment = (LoginFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.container);
        }
    }

    @Override
    public void onLogin() {
        Fragment tidbitFragment = new TidbitFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, tidbitFragment, null);
        ft.commit();
    }
}
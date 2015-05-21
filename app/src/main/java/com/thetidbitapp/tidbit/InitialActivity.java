package com.thetidbitapp.tidbit;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class InitialActivity extends ActionBarActivity implements LoginFragment.OnLoginListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        // check if logged in -- go to main; otherwise go to login TODO

        LoginFragment login;
        if (savedInstanceState == null) {
            login = new LoginFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_initial, login).commit();
        }
        else {
            login = (LoginFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.container_initial);
        }
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
}

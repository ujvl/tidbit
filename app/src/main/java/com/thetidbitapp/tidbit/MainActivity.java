package com.thetidbitapp.tidbit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.thetidbitapp.feed.FeedFragment;

public class MainActivity extends ActionBarActivity implements OnLogoutListener,
                            FragmentManager.OnBackStackChangedListener,
                            FeedFragment.OnFeedInteractionListener,
                            NewEventFragment.OnSubmitListener,
                            OnEventInteractionListener {

    private static String FRAG_TAG = "current fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment feed;
        if (savedInstanceState == null) {
            feed = new FeedFragment();
        }
        else {
            feed = getSupportFragmentManager().findFragmentById(R.id.container_main);
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.container_main, feed, FRAG_TAG)
        .commit();

        shouldDisplayHomeUp();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                addAndCommit(new OverflowFragment());
                break;
            case R.id.action_notif:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLogout() {
        Intent intent = new Intent(this, InitialActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
        Fragment curr = getSupportFragmentManager().findFragmentByTag(FRAG_TAG);
        curr.onResume();
    }

    @Override
    public void onFABClick() {
        addAndCommit(new NewEventFragment());
    }

    @Override
    public void onCardClick(String id) {
        addAndCommit(EventMoreInfoFragment.newInstance(id));
    }

    @Override
    public void onSubmit() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onChooseGoing() {

    }

    @Override
    public void onChooseNotGoing() {

    }

    private void addAndCommit(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
        .addToBackStack(null).replace(R.id.container_main, fragment, FRAG_TAG).commit();
    }

    private void shouldDisplayHomeUp() {
        boolean canGoBack = getSupportFragmentManager().getBackStackEntryCount() > 0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canGoBack);
        getSupportActionBar().setDisplayShowHomeEnabled(canGoBack);
    }
}
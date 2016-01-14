package com.thetidbitapp.tidbit;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.thetidbitapp.feed.FeedFragment;
import com.thetidbitapp.model.SessionManager;
import com.thetidbitapp.state.OnEventInteractionListener;
import com.thetidbitapp.state.OnLogoutListener;

public class MainActivity extends AppCompatActivity implements OnConnectionFailedListener,
                            LocationListener, ConnectionCallbacks, OnEventInteractionListener,
                            OnLogoutListener, FragmentManager.OnBackStackChangedListener,
                            FeedFragment.OnFeedInteractionListener,
                            NewEventFragment.OnSubmitListener {

    private static final String FRAG_TAG = "current fragment";
    private static final int LOC_UPDATE_FASTEST_INTERVAL_MILLIS = 1800000; // 30 mins
    private static final int LOC_UPDATE_INTERVAL_MILLIS = 7200000; // 2 hours

    private SessionManager mSessionManager;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocRequest;
    private boolean mShouldRequestLoc;
    private Location mLastLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSessionManager = new SessionManager(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_main);

        Fragment feed;
        if (savedInstanceState == null) {
            feed = new FeedFragment();
        }
        else {
            feed = getSupportFragmentManager().findFragmentById(R.id.container_main);
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        getSupportFragmentManager().beginTransaction()
        .replace(R.id.container_main, feed, FRAG_TAG).commit();

        shouldDisplayHomeUp();
        buildGoogleApiClient();
        createLocationRequest();

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
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
     public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && !mShouldRequestLoc) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    /**
     *
     * Non lifecycle callbacks
     *
     */

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
    public void onCardClick(String id) {
        addAndCommit(EventDetailsFragment.newInstance(id));
    }

    @Override
    public void onSubmit() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onChooseGoing(String eventId) {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onChooseNotGoing(String eventId) {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLoc = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        mSessionManager.updateLocation(mLastLoc);
        if (mShouldRequestLoc) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Location API connection suspended!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Location API connection failed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLoc = location;
        mSessionManager.updateLocation(mLastLoc);
    }

    /**
     *
     * Helper methods
     *
     */

    private void addAndCommit(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null).add(R.id.container_main, fragment, FRAG_TAG).commit();
    }

    private void shouldDisplayHomeUp() {
        boolean canGoBack = getSupportFragmentManager().getBackStackEntryCount() > 0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canGoBack);
        getSupportActionBar().setDisplayShowHomeEnabled(canGoBack);
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void createLocationRequest() {
        mLocRequest = LocationRequest.create();
        mLocRequest.setInterval(LOC_UPDATE_INTERVAL_MILLIS);
        mLocRequest.setFastestInterval(LOC_UPDATE_FASTEST_INTERVAL_MILLIS);
        mLocRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        mShouldRequestLoc = false;
    }

    private void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocRequest, this);
        mShouldRequestLoc = true;
    }

}
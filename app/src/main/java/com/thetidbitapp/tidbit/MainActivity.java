package com.thetidbitapp.tidbit;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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

    private static String FRAG_TAG = "current fragment";

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
                replaceAndCommit(new OverflowFragment());
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
    public void onFABClick() {
        replaceAndCommit(new NewEventFragment());
    }

    @Override
    public void onCardClick(String id) {
        replaceAndCommit(EventDetailsFragment.newInstance(id));
    }

    @Override
    public void onSubmit() {
        getSupportFragmentManager().popBackStack();
    }

	@Override
	public void onChooseGoing(String eventId) {

	}

	@Override
	public void onChooseNotGoing(String eventId) {

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
		Log.e("ON CONNECTION SUSPENDED", "NOOOO :(");
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		Log.e("ON CONNECTION FAILED", "EVEN WORSE :((((");
	}

	@Override
	public void onLocationChanged(Location location) {
		mLastLoc = location;
		Log.e("ON LOCATION CHANGED", mLastLoc.toString());
		mSessionManager.updateLocation(mLastLoc);
	}

	/**
	 *
	 * Helper methods
	 *
	 */

	private void replaceAndCommit(Fragment fragment) {
		getSupportFragmentManager().beginTransaction()
				.addToBackStack(null).replace(R.id.container_main, fragment, FRAG_TAG).commit();
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
		mLocRequest = new LocationRequest();
		mLocRequest.setInterval(1000000).setFastestInterval(1000000);
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
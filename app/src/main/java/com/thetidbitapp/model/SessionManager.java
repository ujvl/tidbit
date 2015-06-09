package com.thetidbitapp.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;
import com.thetidbitapp.tidbit.R;

/**
 * Created by Ujval on 5/28/15.
 */
public class SessionManager {

    private final SharedPreferences mPrefs;
	private final Gson mJsonConverter;
    private final Context mContext;

    public SessionManager(Context context) {
        mPrefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        mContext = context;
		mJsonConverter = new Gson();
    }

    public SharedPreferences.Editor editor() {
        return mPrefs.edit();
    }

    public void setLoggedIn(boolean isLoggedIn) {
        editor().putBoolean(mContext.getString(R.string.prefs_is_logged_in), isLoggedIn).apply();
    }

    public boolean isLoggedIn() {
        return mPrefs.getBoolean(mContext.getString(R.string.prefs_is_logged_in), false);
    }

	public void updateLocation(Location location) {
		editor().putString(mContext.getString(R.string.prefs_loc), mJsonConverter.toJson(location)).apply();
	}

	public Location getLocation() {
		String loc = mPrefs.getString(mContext.getString(R.string.prefs_loc), null);
		return loc != null ? mJsonConverter.fromJson(loc, Location.class) : null;
	}

}

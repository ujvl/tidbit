package com.thetidbitapp.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

import com.facebook.AccessToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
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
        editor().putBoolean(mContext.getString(R.string.prefs_logged_in_key), isLoggedIn).apply();
    }

	public void setAccessToken(AccessToken token) {
		String tokenStr = mJsonConverter.toJson(token);
		editor().putString(mContext.getString(R.string.prefs_access_token_key), tokenStr).apply();
	}

    public boolean isLoggedIn() {
        return mPrefs.getBoolean(mContext.getString(R.string.prefs_logged_in_key), false);
    }

	public void updateLocation(Location location) {
		String locStr = mJsonConverter.toJson(location);
		editor().putString(mContext.getString(R.string.prefs_loc_key), locStr).apply();
	}

	public String getString(String key) {
		return mPrefs.getString(key, null);
	}

	public Location getLocation() {
		String loc = mPrefs.getString(mContext.getString(R.string.prefs_loc_key), null);
		return loc != null ? mJsonConverter.fromJson(loc, Location.class) : null;
	}

	public AccessToken getAccessToken() {
		String token = mPrefs.getString(mContext.getString(R.string.prefs_access_token_key), null);
		return token != null ? mJsonConverter.fromJson(token, AccessToken.class) : null;
	}

}

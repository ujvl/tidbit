package com.thetidbitapp.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.thetidbitapp.tidbit.R;

/**
 * Created by Ujval on 5/28/15.
 */
public class SessionManager {

    private final SharedPreferences mPrefs;
    private final Context mContext;

    public SessionManager(Context context) {
        mPrefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        mContext = context;
    }

    public SharedPreferences prefs() {
        return mPrefs;
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

}

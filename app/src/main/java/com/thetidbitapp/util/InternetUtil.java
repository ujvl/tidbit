package com.thetidbitapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Ujval on 6/11/15
 */
public final class InternetUtil {

	private InternetUtil() { }

	/**
	 * Checks if phone is connected to the internet
	 * @param context current context
	 * @return true if phone is connected to internet, else false
	 */
	public static boolean isOnline(Context context) {
		ConnectivityManager cm =
				(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}

}

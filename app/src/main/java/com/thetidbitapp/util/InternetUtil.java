package com.thetidbitapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import com.thetidbitapp.tidbit.R;

/**
 * Created by Ujval on 6/11/15
 */
public final class InternetUtil {

	private InternetUtil() { }

	/**
	 * Checks if phone is connected to the internet
	 * @param context current context
	 * @return whether device is connected to internet
	 */
	public static boolean isOnline(Context context) {
		ConnectivityManager cm =
				(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}

	/**
	 * Shows a snackbar indicating no internet connection
	 * @param v View of fragment
	 * @param f Fragment
	 */
	public static void showNoInternetSnackBar(View v, Fragment f) {
		Snackbar.make(v, f.getString(R.string.connect_error), Snackbar.LENGTH_SHORT).show();
	}

}

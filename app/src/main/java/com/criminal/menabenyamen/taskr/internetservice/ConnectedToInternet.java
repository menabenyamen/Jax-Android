package com.criminal.menabenyamen.taskr.internetservice;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by menabenyamen on 2017-06-13.
 */

public class ConnectedToInternet {

    Context context;

    public ConnectedToInternet(Context context) {
        this.context = context;
    }

    public static boolean isOnline(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();

        } catch (Exception e) {
            Log.e("", "CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return false;
    }

}

package com.mindmade.mcom.utilclasses;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Mindmade technologies on 06-05-2017.
 */
public class NetworkConnectionManager {
    private Context context;

    public NetworkConnectionManager(Context context) {
        this.context = context;
    }


    //method is to check the internet connectivity
    public boolean isConnectingToInternet() {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null) { // connected to the internet
            return true;
        } else {  // not connected to the internet
            return false;
        }
    }

    //this method used to encode the string values before sending the values to server
    public String urlencoder(String data) {
        try {
            String returnData = URLEncoder.encode(data, "utf-8");
            return returnData;
        } catch (UnsupportedEncodingException ex) {
            return null;
        }
    }
}

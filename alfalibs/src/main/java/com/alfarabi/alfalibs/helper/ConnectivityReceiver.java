package com.alfarabi.alfalibs.helper;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.HashSet;

public class ConnectivityReceiver extends BroadcastReceiver {
 
    public static HashSet<ConnectivityReceiverListener> connectivityReceiverListeners = new HashSet<>();
 
    public ConnectivityReceiver() {
        super();
    }
 
    @Override
    public void onReceive(Context context, Intent arg1) {

        if (connectivityReceiverListeners != null) {
            for (ConnectivityReceiverListener listener : connectivityReceiverListeners) {
                listener.onNetworkConnectionChanged(connected(context));
            }
        }
    }

    public static boolean connected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected ;
    }

    public static boolean addConnectionListener(ConnectivityReceiverListener listener){
        connectivityReceiverListeners.add(listener);
        return false ;
    }


    public static <ACT extends Activity & ConnectivityReceiverListener> boolean addConnectionListener(ACT listener){
        boolean connected = connected(listener);
        connectivityReceiverListeners.add(listener);
        return connected ;
    }

    public static <ACT extends Activity & ConnectivityReceiverListener> boolean removeConnectionListener(ACT listener){
        boolean connected = connected(listener);
        connectivityReceiverListeners.remove(listener);
        return connected ;
    }

    public static void removeConnectionListener(){
        connectivityReceiverListeners.clear();
    }


    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }
}
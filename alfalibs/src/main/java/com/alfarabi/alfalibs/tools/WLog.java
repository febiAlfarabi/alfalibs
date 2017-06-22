package com.alfarabi.alfalibs.tools;

import android.util.Log;

import com.alfarabi.alfalibs.BaseApplication;

/**
 * Created by Alfarabi on 6/15/17.
 */

public class WLog {

    public static void i(String TAG, String message, Throwable... ts){
        if(!BaseApplication.DEBUG)
            return;
        if (ts!=null && ts.length>0){
            Log.i(TAG, message, ts[0]);
        }else{
            Log.i(TAG, message);
        }
    }

    public static void d(String TAG, String message, Throwable... ts){
        if(!BaseApplication.DEBUG)
            return;
        if (ts!=null && ts.length>0){
            Log.d(TAG, message, ts[0]);
        }else{
            Log.d(TAG, message);
        }
    }

    public static void w(String TAG, String message, Throwable... ts){
        if(!BaseApplication.DEBUG)
            return;
        if (ts!=null && ts.length>0){
            Log.w(TAG, message, ts[0]);
        }else{
            Log.w(TAG, message);
        }
    }

    public static void e(String TAG, String message, Throwable... ts){
        if(!BaseApplication.DEBUG)
            return;
        if (ts!=null && ts.length>0){
            Log.e(TAG, message, ts[0]);
        }else{
            Log.e(TAG, message);
        }
    }
    public static void v(String TAG, String message, Throwable... ts){
        if(!BaseApplication.DEBUG)
            return;
        if (ts!=null && ts.length>0){
            Log.v(TAG, message, ts[0]);
        }else{
            Log.v(TAG, message);
        }
    }
}

package com.alfarabi.alfalibs.tools;

import android.util.Log;

import com.alfarabi.alfalibs.BaseApplication;

/**
 * Created by Alfarabi on 6/15/17.
 */

public class WLog {

    public static final int chunkSize = 2048;


    public static void i(String TAG, String message, Throwable... ts){
        if(!BaseApplication.DEBUG)
            return;
        if (ts!=null && ts.length>0){
            for (int i = 0; i < message.length(); i += chunkSize) {
                Log.i(TAG, message.substring(i, Math.min(message.length(), i + chunkSize)), ts[0]);
            }
        }else{
            for (int i = 0; i < message.length(); i += chunkSize) {
                Log.i(TAG, message.substring(i, Math.min(message.length(), i + chunkSize)));
            }
        }
    }

    public static void d(String TAG, String message, Throwable... ts){
        if(!BaseApplication.DEBUG)
            return;
        if (ts!=null && ts.length>0){
            for (int i = 0; i < message.length(); i += chunkSize) {
                Log.d(TAG, message.substring(i, Math.min(message.length(), i + chunkSize)), ts[0]);
            }
        }else{
            for (int i = 0; i < message.length(); i += chunkSize) {
                Log.d(TAG, message.substring(i, Math.min(message.length(), i + chunkSize)));
            }
        }
    }

    public static void w(String TAG, String message, Throwable... ts){
        if(!BaseApplication.DEBUG)
            return;
        if (ts!=null && ts.length>0){
            for (int i = 0; i < message.length(); i += chunkSize) {
                Log.w(TAG, message.substring(i, Math.min(message.length(), i + chunkSize)), ts[0]);
            }
        }else{
            for (int i = 0; i < message.length(); i += chunkSize) {
                Log.w(TAG, message.substring(i, Math.min(message.length(), i + chunkSize)));
            }
        }
    }

    public static void e(String TAG, String message, Throwable... ts){
        if(!BaseApplication.DEBUG)
            return;
        if (ts!=null && ts.length>0){
            for (int i = 0; i < message.length(); i += chunkSize) {
                Log.e(TAG, message.substring(i, Math.min(message.length(), i + chunkSize)), ts[0]);
            }
        }else{
            for (int i = 0; i < message.length(); i += chunkSize) {
                Log.e(TAG, message.substring(i, Math.min(message.length(), i + chunkSize)));
            }
        }
    }
    public static void v(String TAG, String message, Throwable... ts){
        if(!BaseApplication.DEBUG)
            return;
        if (ts!=null && ts.length>0){
            for (int i = 0; i < message.length(); i += chunkSize) {
                Log.v(TAG, message.substring(i, Math.min(message.length(), i + chunkSize)), ts[0]);
            }
        }else{
            for (int i = 0; i < message.length(); i += chunkSize) {
                Log.v(TAG, message.substring(i, Math.min(message.length(), i + chunkSize)));
            }
        }
    }
}

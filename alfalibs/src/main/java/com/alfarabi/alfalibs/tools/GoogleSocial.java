package com.alfarabi.alfalibs.tools;

import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import lombok.Getter;

/**
 * Created by alfarabi on 7/23/17.
 */

public class GoogleSocial {

    public static final String TAG = GoogleSocial.class.getName();

    GoogleSignInOptions gso ;
    @Getter GoogleApiClient apiClient ;
    static GoogleSocial googleSocial;


    public static final GoogleSocial instance(FragmentActivity context){
        if(googleSocial ==null){
            googleSocial = new GoogleSocial(context);
        }
        return googleSocial;
    }

    public GoogleSocial(FragmentActivity context){
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        apiClient = new GoogleApiClient.Builder(context).enableAutoManage(context, connectionResult -> {
            WLog.i(TAG, "CONNECTED");
        }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

    }


}

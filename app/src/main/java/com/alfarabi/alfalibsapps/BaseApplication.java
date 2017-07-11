package com.alfarabi.alfalibsapps;

import android.app.Application;

import com.alfarabi.alfalibs.http.HttpInstance;


/**
 * Created by Alfarabi on 6/30/17.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpInstance.init("https://api.github.com/");
    }
}

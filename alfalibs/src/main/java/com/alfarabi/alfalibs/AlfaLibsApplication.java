package com.alfarabi.alfalibs;

import android.app.Application;

/**
 * Created by Alfarabi on 6/15/17.
 */

public abstract class AlfaLibsApplication extends Application {

    public static boolean DEBUG = true;
    public static String VERSION_NAME;

    protected abstract boolean getBuildConfigDebug();
    protected abstract String getBuildConfigVersionName();

}

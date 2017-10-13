package com.alfarabi.alfalibs.tools;

import android.app.Activity;

import com.google.gson.internal.Primitives;

/**
 * Created by Alfarabi on 8/24/17.
 */

public class Caster {


    public static <T extends Activity> T activity(Activity activity, Class<T> tClass){
        return Primitives.wrap(tClass).cast(activity);
    }


}

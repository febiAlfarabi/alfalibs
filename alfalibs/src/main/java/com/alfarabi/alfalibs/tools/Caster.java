package com.alfarabi.alfalibs.tools;


import com.google.gson.internal.Primitives;

//import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Alfarabi on 8/24/17.
 */

public class Caster {


    public static <T extends AppCompatActivity> T activity(AppCompatActivity activity, Class<T> tClass){
        return Primitives.wrap(tClass).cast(activity);
    }


}

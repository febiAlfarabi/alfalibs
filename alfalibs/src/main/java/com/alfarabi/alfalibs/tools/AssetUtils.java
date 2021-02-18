package com.alfarabi.alfalibs.tools;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.InputStream;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Alfarabi on 9/5/17.
 */

public class AssetUtils {


    public static String readHtmlFile(AppCompatActivity activity, String pathname){
        InputStream is = null;
        try {
            is = activity.getAssets().open(pathname);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String str = new String(buffer);
//            str = str.replace("old string", "new string");
            return str ;
        } catch (Exception e) {
            e.printStackTrace();
            return ExceptionUtils.getStackTrace(e);
        }
    }

}

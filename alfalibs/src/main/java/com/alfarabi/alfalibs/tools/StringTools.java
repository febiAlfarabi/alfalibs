package com.alfarabi.alfalibs.tools;

/**
 * Created by alfarabi on 7/23/17.
 */

public class StringTools {

    public static String limit(String in, int length){
        if (in == null){
            throw new RuntimeException("String in is null cant be limited");
        }
        if(in.length()<=length){
            return in ;
        }
        String out = in.substring(0, length)+"...";
        return out ;
    }
}

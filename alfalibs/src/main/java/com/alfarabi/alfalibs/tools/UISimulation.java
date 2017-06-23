package com.alfarabi.alfalibs.tools;

import java.util.List;

/**
 * Created by Alfarabi on 6/19/17.
 */

public class UISimulation {

    public static final int DEV_PROTOTYPE = 1;
    public static final int DEV_REALDATA = 2;
    public static int mode = DEV_PROTOTYPE;

    public static final int size(List objects){
        if(mode==DEV_PROTOTYPE){
            return Demo.LENGTH_DEMO_1;
        }
        if(objects!=null){
            return objects.size();
        }
        return 0;
    }
}

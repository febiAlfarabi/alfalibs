package com.alfarabi.alfalibs.tools;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Alfarabi on 9/18/17.
 */

public class AppProgress {

    static ProgressDialog progressBasic ;
    static Context context ;


    /*
    * indcancel is 2 boolean where ind is indeterminate
    * cancel is cancelable
    * */
    public static void showBasic(Context context, int message, boolean... indCancel){
        if(progressBasic!=null && progressBasic.isShowing()){
            try{
                progressBasic.dismiss();
                progressBasic = null ;
            }catch (Exception e){ }
        }
        AppProgress.context = context ;
        if(indCancel.length==0){
            progressBasic = ProgressDialog.show(context,"", context.getString(message));
        }else if(indCancel.length==1){
            progressBasic = ProgressDialog.show(context, "", context.getString(message), indCancel[0]);
        }else if(indCancel.length==2){
            progressBasic = ProgressDialog.show(context, "", context.getString(message), indCancel[0], indCancel[1]);
        }

        progressBasic.show();
    }
    public static void showBasic(Context context, int title, int message, boolean... indCancel){
        if(progressBasic!=null && progressBasic.isShowing()){
            try{
                progressBasic.dismiss();
                progressBasic = null ;
            }catch (Exception e){ }
        }
        AppProgress.context = context ;
        if(indCancel.length==0){
            progressBasic = ProgressDialog.show(context, context.getString(title), context.getString(message));
        }else if(indCancel.length==1){
            progressBasic = ProgressDialog.show(context, context.getString(title), context.getString(message), indCancel[0]);
        }else if(indCancel.length==2){
            progressBasic = ProgressDialog.show(context, context.getString(title), context.getString(message), indCancel[0], indCancel[1]);
        }

        progressBasic.show();
    }

    public static void dismissBasic(){
        if(progressBasic!=null && progressBasic.isShowing()){
            try{
                progressBasic.dismiss();
                progressBasic = null ;
            }catch (Exception e){ }
        }
    }
}

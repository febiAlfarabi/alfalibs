//package com.alfarabi.alfalibs.views;
//
//import android.content.Context;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AlertDialog;
//import android.widget.Toast;
//
//import com.alfarabi.alfalibs.AlfaLibsApplication;
//
//import org.apache.commons.lang.exception.ExceptionUtils;
//
///**
// * Created by Alfarabi on 7/17/17.
// */
//
//public class ThrowableDialog extends Throwable{
//
//    public void  show(Context context, String title, String message, int length){
//        new AlertDialog.Builder(context).setMessage(message).setTitle(title).setPositiveButton()
//        Toast.makeText(context, s, length).show();
//    }
//
//    public void show(Fragment fragment, String s, int length){
//        Toast.makeText(fragment.getActivity(), s, length).show();
//    }
//
//    public void show(Context context, Throwable throwable, int length){
//        if(AlfaLibsApplication.DEBUG){
//            throwable.printStackTrace();
//        }
//        Toast.makeText(context, ExceptionUtils.getMessage(throwable), length).show();
//    }
//
//    public void show(Fragment fragment, Throwable throwable, int length){
//        if(AlfaLibsApplication.DEBUG){
//            throwable.printStackTrace();
//        }
//        Toast.makeText(fragment.getActivity(), ExceptionUtils.getMessage(throwable), length).show();
//    }
//
//}

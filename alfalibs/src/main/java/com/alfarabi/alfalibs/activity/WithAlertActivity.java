package com.alfarabi.alfalibs.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
//import android.support.design.widget.Snackbar;
import android.text.Html;
import android.view.Display;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.alfarabi.alfalibs.R;

import java.io.EOFException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import android.support.v7.app.AppCompatActivity;
import retrofit2.HttpException;


/**
 * Created by alfarabi on 3/19/18.
 */

public abstract class WithAlertActivity extends SimpleBaseActivity {



    public int[] getDimension(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        return new int[]{width, height};
    }

//    protected Snackbar snackbar ;
    public void showSnackbar(String message, View.OnClickListener onClickListener){
//        if(snackbar!=null && snackbar.isShown()){
//            snackbar.dismiss();
//            snackbar = null ;
//        }
//        snackbar = Snackbar
//                .make(getWindow().getDecorView(), Html.fromHtml("<font color=\"#ffffff\">"+message+"</font>"), Snackbar.LENGTH_LONG).setActionTextColor(Color.WHITE).setDuration(5000)
//                .setAction(getString(R.string.close), v -> {
//                    snackbar.dismiss();
//                    onClickListener.onClick(v);
//                });
//        snackbar.show();
    }

    public <ACT extends AppCompatActivity> void showSnackbar(Throwable throwable, View.OnClickListener onClickListener, Class<ACT>... actClass){
        String message = "" ;
        throwable.printStackTrace();
        if(throwable instanceof EOFException){
            message = getString(R.string.server_error);
        }else if(throwable instanceof UnknownHostException){
            message = getString(R.string.connection_timeout);
        }else if(throwable instanceof HttpException){
            if(throwable.getMessage().trim().contains("HTTP 503")){
                if(actClass!=null && actClass.length>0) {
                    PackageManager pm = getPackageManager();
                    Intent intent = new Intent(this, actClass[0]);
                    intent.putExtra("error_code", String.valueOf("503"));
                    getWindow().getDecorView().postDelayed(() -> {
                        finish();
                        startActivity(intent);
                    }, 2000);
                    message = throwable.getMessage();
                }else {
                    message = getString(R.string.connection_timeout);
                }
            }else{
                message = getString(R.string.connection_timeout);
            }
        }else if(throwable instanceof TimeoutException){
            message = getString(R.string.connection_timeout);
        }else if(throwable instanceof ConnectException){
            message = getString(R.string.connection_timeout);
        }else if(throwable instanceof EOFException){
            message = getString(R.string.connection_timeout);
        }else if(throwable instanceof java.net.SocketTimeoutException){
            message = getString(R.string.connection_timeout);
        }else if(throwable instanceof java.net.SocketException){
            message = getString(R.string.connection_timeout);
        } else{
            message = throwable.getLocalizedMessage();
        }
        showSnackbar(message, onClickListener);
    }

    protected MaterialDialog materialDialog ;


    public void showDialog(String message){
        if(materialDialog!=null && materialDialog.isShowing()){
            materialDialog.dismiss();
            materialDialog = null ;
        }
        materialDialog = new MaterialDialog.Builder(this).title(R.string.alert).backgroundColorRes(R.color.white)
                .content(message).contentColor(Color.BLACK).titleColor(Color.BLACK).positiveColor(Color.BLUE)
                .positiveText(R.string.ok).theme(Theme.LIGHT).backgroundColor(Color.WHITE)
                .onPositive((dialog, which) -> {})
                .show();
    }

    public void showDialog(Throwable throwable, MaterialDialog.SingleButtonCallback singleButtonCallback){
        if(materialDialog!=null && materialDialog.isShowing()){
            materialDialog.dismiss();
            materialDialog = null ;
        }
        materialDialog = new MaterialDialog.Builder(this).title(R.string.alert).backgroundColorRes(R.color.white)
                .content(throwable.getMessage()).contentColor(Color.BLACK).titleColor(Color.BLACK).positiveColor(Color.BLUE)
                .positiveText(R.string.ok).theme(Theme.LIGHT).backgroundColor(Color.WHITE)
                .onPositive(singleButtonCallback)
                .show();
    }


    public void showDialog(String message, MaterialDialog.SingleButtonCallback singleButtonCallback){
        if(materialDialog!=null && materialDialog.isShowing()){
            materialDialog.dismiss();
            materialDialog = null ;
        }
        materialDialog = new MaterialDialog.Builder(this).title(R.string.alert).backgroundColorRes(R.color.white)
                .content(message).contentColor(Color.BLACK).titleColor(Color.BLACK).positiveColor(Color.BLUE)
                .positiveText(R.string.ok).theme(Theme.LIGHT).backgroundColor(Color.WHITE)
                .onPositive(singleButtonCallback)
                .show();
    }
    public void showDialog(String title, String message){
        if(materialDialog!=null && materialDialog.isShowing()){
            materialDialog.dismiss();
            materialDialog = null ;
        }
        materialDialog = new MaterialDialog.Builder(this).title(title).backgroundColorRes(R.color.white)
                .content(message).contentColor(Color.BLACK).titleColor(Color.BLACK).positiveColor(Color.BLUE)
                .positiveText(R.string.ok).theme(Theme.LIGHT).backgroundColor(Color.WHITE)
                .onPositive((dialog, which) -> {})
                .show();
    }
    public void showDialog(String title, String message, MaterialDialog.SingleButtonCallback singleButtonCallback){
        if(materialDialog!=null && materialDialog.isShowing()){
            materialDialog.dismiss();
            materialDialog = null ;
        }
        materialDialog = new MaterialDialog.Builder(this).title(title).backgroundColorRes(R.color.white)
                .content(message).contentColor(Color.BLACK).titleColor(Color.BLACK).positiveColor(Color.BLUE)
                .positiveText(R.string.ok).theme(Theme.LIGHT).backgroundColor(Color.WHITE)
                .onPositive(singleButtonCallback)
                .show();
    }


    public void showDialog(String message, MaterialDialog.SingleButtonCallback okButtonCallback,MaterialDialog.SingleButtonCallback cancelButtonCallback) {
        if(materialDialog!=null && materialDialog.isShowing()){
            materialDialog.dismiss();
            materialDialog = null ;
        }
        materialDialog = new MaterialDialog.Builder(this).title(R.string.alert).backgroundColorRes(R.color.white)
                .content(message).contentColor(Color.BLACK).titleColor(Color.BLACK).positiveColor(Color.BLUE)
                .positiveText(R.string.ok).negativeText(R.string.cancel).theme(Theme.LIGHT).backgroundColor(Color.WHITE)
                .onPositive(okButtonCallback).onNegative(cancelButtonCallback)
                .show();
    }

    public void showDialog(String title, String message, MaterialDialog.SingleButtonCallback okButtonCallback,MaterialDialog.SingleButtonCallback cancelButtonCallback) {
        if(materialDialog!=null && materialDialog.isShowing()){
            materialDialog.dismiss();
            materialDialog = null ;
        }
        materialDialog = new MaterialDialog.Builder(this).title(title).backgroundColorRes(R.color.white)
                .content(message).contentColor(Color.BLACK).titleColor(Color.BLACK).positiveColor(Color.BLUE)
                .positiveText(R.string.ok).negativeText(R.string.cancel).theme(Theme.LIGHT).backgroundColor(Color.WHITE)
                .onPositive(okButtonCallback).onNegative(cancelButtonCallback)
                .show();
    }

    public <ACT extends AppCompatActivity> void showDialog(Throwable throwable, MaterialDialog.SingleButtonCallback singleButtonCallback, Class<ACT>... actClass){
        String message = "" ;
        throwable.printStackTrace();
        if(throwable instanceof EOFException){
            message = getString(R.string.server_error);
        }else if(throwable instanceof UnknownHostException){
            message = getString(R.string.connection_timeout);
        }else if(throwable instanceof HttpException){
            if(throwable.getMessage().trim().contains("HTTP 503")){
                if(actClass!=null && actClass.length>0) {
                    PackageManager pm = getPackageManager();
                    Intent intent = new Intent(this, actClass[0]);
                    intent.putExtra("error_code", String.valueOf("503"));
                    getWindow().getDecorView().postDelayed(() -> {
                        finish();
                        startActivity(intent);
                    }, 2000);
                    message = throwable.getMessage();
                }else {
                    message = getString(R.string.connection_timeout);
                }
            }else{
                message = getString(R.string.connection_timeout);
            }
        }else if(throwable instanceof TimeoutException){
            message = getString(R.string.connection_timeout);
        }else if(throwable instanceof ConnectException){
            message = getString(R.string.connection_timeout);
        }else if(throwable instanceof EOFException){
            message = getString(R.string.connection_timeout);
        }else if(throwable instanceof java.net.SocketTimeoutException){
            message = getString(R.string.connection_timeout);
        }else if(throwable instanceof java.net.SocketException){
            message = getString(R.string.connection_timeout);
        } else{
            message = throwable.getLocalizedMessage();
        }
        showDialog(message, singleButtonCallback);
    }

    public <ACT extends AppCompatActivity> void showDialog(Throwable throwable, MaterialDialog.SingleButtonCallback okButtonCallback, MaterialDialog.SingleButtonCallback cancelButtonCallback, Class<ACT>... actClass){
        String message = "" ;
        throwable.printStackTrace();
        if(throwable instanceof EOFException){
            message = getString(R.string.server_error);
        }else if(throwable instanceof UnknownHostException){
            message = getString(R.string.connection_timeout);
        }else if(throwable instanceof HttpException){
            if(throwable.getMessage().trim().contains("HTTP 503")){
                if(actClass!=null && actClass.length>0) {
                    PackageManager pm = getPackageManager();
                    Intent intent = new Intent(this, actClass[0]);
                    intent.putExtra("error_code", String.valueOf("503"));
                    getWindow().getDecorView().postDelayed(() -> {
                        finish();
                        startActivity(intent);
                    }, 2000);
                    message = throwable.getMessage();
                }else {
                    message = getString(R.string.connection_timeout);
                }
            }else{
                message = getString(R.string.connection_timeout);
            }
        }else if(throwable instanceof TimeoutException){
            message = getString(R.string.connection_timeout);
        }else if(throwable instanceof ConnectException){
            message = getString(R.string.connection_timeout);
        }else if(throwable instanceof EOFException){
            message = getString(R.string.connection_timeout);
        }else if(throwable instanceof java.net.SocketTimeoutException){
            message = getString(R.string.connection_timeout);
        }else if(throwable instanceof java.net.SocketException){
            message = getString(R.string.connection_timeout);
        } else{
            message = throwable.getLocalizedMessage();
        }
        showDialog(message, okButtonCallback, cancelButtonCallback);
    }

}



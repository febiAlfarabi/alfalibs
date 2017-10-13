package com.alfarabi.alfalibs.tools;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.alfarabi.alfalibs.R;

/**
 * Created by Alfarabi on 10/10/17.
 */

public class PermissionTools {

    public static final int PERMISSION_CODE = 1 ;

    public static void permissionsDialog(Activity activity, final boolean makeSystemRequest, DialogInterface.OnClickListener okClickListener, DialogInterface.OnClickListener cancelClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.permission_required); // Your own title
        builder.setMessage(R.string.sms_permission_required); // Your own message

//        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                // Display system runtime permission request?
//                if (makeSystemRequest) {
//                    requestReadAndSendSmsPermission(activity);
//                }
//            }
//        });

        builder.setPositiveButton(R.string.ok, okClickListener).setNegativeButton(R.string.cancel, cancelClickListener);

        builder.setCancelable(false);
        builder.show();
    }

    public static  boolean isSmsPermissionGranted(Activity activity) {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Request runtime SMS permission
     */
    public static void requestReadAndSendSmsPermission(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_SMS)) {
            // You may display a non-blocking explanation here, read more in the documentation:
            // https://developer.android.com/training/permissions/requesting.html
        }
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_SMS}, PERMISSION_CODE);
    }

    public static void requestPermission(Activity activity, String[] permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_SMS)) {
            // You may display a non-blocking explanation here, read more in the documentation:
            // https://developer.android.com/training/permissions/requesting.html
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECEIVE_SMS)) {
            // You may display a non-blocking explanation here, read more in the documentation:
            // https://developer.android.com/training/permissions/requesting.html
        }
        ActivityCompat.requestPermissions(activity, permission, PERMISSION_CODE);
    }

}

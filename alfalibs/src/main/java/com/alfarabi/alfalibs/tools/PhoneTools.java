package com.alfarabi.alfalibs.tools;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.provider.Telephony;
import android.support.annotation.NonNull;

import java.util.List;

public class PhoneTools {

    public static String getDefaultSmsAppPackageName(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            return Telephony.Sms.getDefaultSmsPackage(context);
        else {
            Intent intent = new Intent(Intent.ACTION_VIEW)
                    .addCategory(Intent.CATEGORY_DEFAULT).setType("vnd.android-dir/mms-sms");
            final List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(intent, 0);
            if (resolveInfos != null && !resolveInfos.isEmpty())
                return resolveInfos.get(0).activityInfo.packageName;
            return null;
        }
    }
}
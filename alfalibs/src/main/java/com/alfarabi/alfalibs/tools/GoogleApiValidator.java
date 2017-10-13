package com.alfarabi.alfalibs.tools;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.Random;

/**
 * Checks availability of Google API within client device.
 *
 * @author Hendra Anggrian (hendra@ahloo.com)
 * @see //com.google.android.gms.common.GoogleApiAvailability
 */
public final class GoogleApiValidator {

    @NonNull private final GoogleApiAvailability googleApi;
    @NonNull private final Activity activity;
    @Nullable private OnAvailable onAvailable;
    @Nullable private OnUnavailable onUnavailable;

    private GoogleApiValidator(@NonNull Activity activity) {
        this.googleApi = GoogleApiAvailability.getInstance();
        this.activity = activity;
    }

    @NonNull
    public GoogleApiValidator onAvailable(@NonNull OnAvailable onAvailable) {
        this.onAvailable = onAvailable;
        return this;
    }

    @NonNull
    public GoogleApiValidator onUnavailable(@NonNull OnUnavailable onUnavailable) {
        this.onUnavailable = onUnavailable;
        return this;
    }

    public void validate() {
        int result = googleApi.isGooglePlayServicesAvailable(activity);
        if (result == ConnectionResult.SUCCESS) {
            if (onAvailable != null)
                onAvailable.onAvailable();
        } else if (googleApi.isUserResolvableError(result)) {
            if (onUnavailable != null)
                onUnavailable.onUnavailableResolvable(googleApi.getErrorDialog(activity, result, new Random().nextInt(255)));
        } else {
            if (onUnavailable != null)
                onUnavailable.onUnavailableUnresolvable();
        }
    }

    @NonNull
    public static GoogleApiValidator getInstance(@NonNull Activity activity) {
        return new GoogleApiValidator(activity);
    }

    public interface OnAvailable {
        /**
         * Indicates that Google API is present and ready to go.
         */
        void onAvailable();
    }

    public interface OnUnavailable {
        /**
         * Indicates that Google API is outdated and needs to be updated to run correctly.
         *
         * @param dialog dialog to go to Google Play Store to update Google API.
         */
        void onUnavailableResolvable(@NonNull Dialog dialog);

        /**
         * Indicates that device is too old and cannot update Google API.
         */
        void onUnavailableUnresolvable();
    }
}
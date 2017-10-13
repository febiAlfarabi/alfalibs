package com.alfarabi.alfalibs.tools;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.webkit.MimeTypeMap;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Represents standard media type as documented in http://www.iana.org/assignments/media-types/media-types.xhtml.
 */
public final class MimeType {

    @NonNull private final String value;

    private MimeType(@NonNull String value) {
        this.value = value.toLowerCase();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        else if (obj instanceof MimeType)
            return ((MimeType) obj).value.equals(obj);
        else
            return obj instanceof String && obj.equals(value);
    }

    @Override
    public String toString() {
        return value;
    }

    public boolean startsWith(@NonNull String prefix) {
        return value.startsWith(prefix.toLowerCase());
    }

    public boolean endsWith(@NonNull String suffix) {
        return value.endsWith(suffix);
    }

    @NonNull
    public RequestBody createRequestBody(@NonNull Uri uri) {
        return createRequestBody(new File(uri.getPath()));
    }

    @NonNull
    public RequestBody createRequestBody(@NonNull File file) {
        return RequestBody.create(MediaType.parse(value), file);
    }

    @NonNull
    public RequestBody createRequestBody(@NonNull String s) {
        return RequestBody.create(MediaType.parse(value), s);
    }

    /**
     * Creates MimeType from mime type string.
     *
     * @param mimeType e.g.: image/*, video/*, etc.
     * @return MimeType.
     */
    @NonNull
    public static MimeType valueOf(@NonNull String mimeType) {
        return new MimeType(mimeType);
    }

    /**
     * Guess MimeType from uri located at local storage (gallery).
     *
     * @param context active Context.
     * @param uri     path of file.
     * @return MimeType.
     */
    @NonNull
    public static MimeType guessUri(@NonNull Context context, @NonNull Uri uri) {
        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            return new MimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            return guessURL(Uri.fromFile(new File(uri.getPath())).toString());
        }
    }

    /**
     * Guess MimeType from URL.
     *
     * @param url e.g.: http://www.somewebsite.com.
     * @return MimeType.
     */
    @NonNull
    public static MimeType guessURL(@NonNull String url) {
        return guessExtension(MimeTypeMap.getFileExtensionFromUrl(url));
    }

    /**
     * Guess MimeType from file extension.
     *
     * @param extension e.g.: jpg, mp4, etc.
     * @return MimeType.
     */
    @NonNull
    public static MimeType guessExtension(@NonNull String extension) {
        return new MimeType(MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension));
    }
}
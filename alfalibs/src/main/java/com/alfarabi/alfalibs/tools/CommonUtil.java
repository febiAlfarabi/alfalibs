package com.alfarabi.alfalibs.tools;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class CommonUtil {
    
    public static final String TAG = CommonUtil.class.getName();

    /**
     * disable manual input for edit text
     *
     * @param editText edit text that want to disabled
     */
    public static void disableKeyboard(@NonNull EditText editText) {
        editText.setOnTouchListener((v, event) -> {
            v.onTouchEvent(event);
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return true;
        });
    }


    /**
     * disable manual input for many edit text
     *
     * @param //editText edit texts those want to be disabled
     */
//    public static void disableKeyboard(@NonNull android.widget.EditText... editTexts) {
//        if (editTexts != null && editTexts.length > 0) {
//            for (android.widget.EditText editText : editTexts) {
//                if (editText != null) {
//                    editText.setOnTouchListener((v, event) -> {
//                        v.onTouchEvent(event);
//                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                        if (imm != null) {
//                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                        }
//                        return true;
//                    });
//                }
//            }
//        }
//
//    }

    public static void disableKeyboard(@NonNull EditText... editTexts) {
        if (editTexts != null && editTexts.length > 0) {
            for (EditText editText : editTexts) {
                if (editText != null) {
                    editText.setOnTouchListener((v, event) -> {
                        v.onTouchEvent(event);
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                        return true;
                    });
                }
            }
        }

    }

    /**
     * load color resource from theme
     *
     * @param context       context to used
     * @param colorIntId    color id resource for android API >= 21 (e.g. android.R.colorPrimary)
     * @param colorStringId color id resource for android API < 21 (e.g. colorPrimary)
     * @return The color value as integer
     */
    public static int getThemeColor(@NonNull Context context, int colorIntId, @NonNull String colorStringId) {
        return getThemeProperty(context, colorIntId, colorStringId).data;
    }

    /**
     * load color resource from theme"
     *
     * @param context          context to used
     * @param resourceIdInt    resource id as integer for android API >= 21 (e.g. android.R.colorPrimary)
     * @param resourceIdString resource id as string for android API < 21 (e.g. colorPrimary)
     * @return Theme property value holder
     */
    private static TypedValue getThemeProperty(@NonNull Context context, int resourceIdInt, @NonNull String resourceIdString) {
        TypedValue outValue = new TypedValue();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            resourceIdInt = context.getResources().getIdentifier(resourceIdString, "attr", context.getPackageName());
        }

        context.getTheme().resolveAttribute(resourceIdInt, outValue, true);

        return outValue;
    }

    /**
     * Generates the file multipart body.
     *
     * @param context Android context object
     * @param key     part name
     * @param uri     file uri
     * @return The request body
     */
    public static MultipartBody.Part generateMultiPart(@NonNull Context context,
                                                       @NonNull String key,
                                                       @NonNull Uri uri) {
        /** Get the actual file by uri */
        File file = new File(uri.getPath());

        /** Get the mime type */
        String mimeType = context.getContentResolver().getType(uri);
        /** Sometimes it returns as a null */
        if (mimeType == null) {
            mimeType = URLConnection.guessContentTypeFromName(uri.getPath());
        }

        /** Create RequestBody instance from file */
        if (mimeType != null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), file);
            return MultipartBody.Part.createFormData(key, file.getName(), requestFile);
        } else {
            Log.w(CommonUtil.class.getSimpleName(), "Incorrect file type, unable to determine the mimetype");
            return null;
        }
    }

    public static File storeImage(Activity activity, Bitmap imageData) throws Exception{
        File photoFile = createImageFile(activity);
        String filePath = photoFile.toString();
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
        //choose another format if PNG doesn't suit you
        imageData.compress(Bitmap.CompressFormat.JPEG, 90, bos);
        bos.flush();
        bos.close();
        return photoFile ;
    }
    public static File storeImage(Activity activity, Bitmap imageData, String filename) throws Exception{
        File photoFile = createImageFile(activity, filename);
        String filePath = photoFile.toString();
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
        //choose another format if PNG doesn't suit you
        imageData.compress(Bitmap.CompressFormat.JPEG, 90, bos);
        bos.flush();
        bos.close();
        return photoFile ;
    }


    public static File createImageFile(Activity activity) throws IOException, java.io.IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = getAppName(activity)+ timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName.split("\\.")[0].replace("/", ""),  /* prefix */".jpg",/* suffix */storageDir/* directory */);
        // Save a file: path for use cook ACTION_VIEW intents
        String mCurrentPhotoPath = image.getAbsolutePath();
        WLog.i(TAG, "PATH "+mCurrentPhotoPath);
        return image;
    }


    public static File createImageFile(Activity activity, String filename) throws IOException, java.io.IOException {
        WLog.i(TAG, "FILENAME $$$$ "+filename);
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        if(!activity.getCacheDir().exists()){
//            activity.getCacheDir().createNewFile();
//        }
        File image = new File(storageDir, filename);
        if(image.exists()){
            image.delete();
        }
        image.createNewFile();
//        File image = File.createTempFile(filename.split("\\.")[0].replace("/", ""),  "."+filename.split("\\.")[1] /* suffix */, storageDir/* directory */);
        String mCurrentPhotoPath = image.getAbsolutePath();
        WLog.i(TAG, "PATH "+mCurrentPhotoPath);
        return image;
    }




    public static void deleteImageFile(Activity activity) throws IOException, java.io.IOException {
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] files = storageDir.listFiles();
        if(files!=null){
            for (File file :files) {
                if(file.exists()){
                    file.delete();
                }
            }
        }
    }

    public static File openFile(Activity activity, String filename){
        WLog.i(TAG, "FILE NAME "+filename);
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = new File(storageDir, filename);
        WLog.i(TAG, "FILE PATH "+file.getPath());
        return file ;
    }


    public static String getAppName(Activity activity) {
        try {
            return activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).applicationInfo.name;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static final File getFile(Uri uri) throws Exception{
        return new File(uri.getPath());
    }

    public static File writeResponseBodyToDisk(Activity activity, ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File file = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator +timeStamp+".pdf");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return file;
            } catch (IOException e) {
                e.printStackTrace();
            }  finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                }

                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

package com.alfarabi.alfalibs.tools;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Alfarabi on 6/23/17.
 */

@GlideModule
public class MyGlideAppModule extends AppGlideModule{

//    @Override
//    public void registerComponents(Context context, Glide glide, Registry registry) {
//        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.readTimeout(60, TimeUnit.SECONDS);
//        builder.writeTimeout(30, TimeUnit.SECONDS);
//        builder.connectTimeout(30, TimeUnit.SECONDS);
//        registry.append(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(builder.build()));
//    }


//    @Override
//    public void registerComponents(Context context, Registry registry) {
//        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.readTimeout(60, TimeUnit.SECONDS);
//        builder.writeTimeout(30, TimeUnit.SECONDS);
//        builder.connectTimeout(30, TimeUnit.SECONDS);
//        registry.append(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(builder.build()));
//    }

//    @Override
//    public void registerComponents(Context context, Glide glide, Registry registry) {
//        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.readTimeout(60, TimeUnit.SECONDS);
//        builder.writeTimeout(30, TimeUnit.SECONDS);
//        builder.connectTimeout(30, TimeUnit.SECONDS);
//        registry.append(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(builder.build()));
//    }
}

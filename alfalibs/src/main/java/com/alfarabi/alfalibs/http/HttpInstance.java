package com.alfarabi.alfalibs.http;

import android.content.Context;
import android.support.v4.app.Fragment;


import com.alfarabi.alfalibs.AlfaLibsApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ir.mirrajabi.okhttpjsonmock.interceptors.OkHttpMockInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 02/07/2017.
 */

public abstract class HttpInstance {

    public static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    private static String baseUrl;
    private static String otherUrl;
    private static final HashMap<String, Retrofit> retroMaps = new HashMap();

    private static final Gson gson = new GsonBuilder().setLenient().create();

    public HttpInstance() {
    }

    public static <HTTP> HTTP create(Class<HTTP> clazz, String... otherUrl) {
        if(retroMaps == null) {
            throw new NullPointerException("RETROFIT == null");
        } else if(baseUrl == null && otherUrl == null) {
            throw new NullPointerException("URL == null");
        } else if(otherUrl != null && otherUrl.length > 0) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            if(AlfaLibsApplication.DEBUG){
                httpClient.addInterceptor(logging);
            }
            HttpInstance.otherUrl = otherUrl[0];
            retroMaps.put(HttpInstance.otherUrl, (new Retrofit.Builder()).baseUrl(
                    HttpInstance.otherUrl).client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build());
            return ((Retrofit)retroMaps.get(HttpInstance.otherUrl)).create(clazz);
        } else {
            return ((Retrofit)retroMaps.get(HttpInstance.baseUrl)).create(clazz);
        }
    }
    public static <HTTP> HTTP create(Context context, Class<HTTP> clazz, String... otherUrl) {
        if(retroMaps == null) {
            throw new NullPointerException("RETROFIT == null");
        } else if(baseUrl == null && otherUrl == null) {
            throw new NullPointerException("URL == null");
        } else if(otherUrl != null && otherUrl.length > 0) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            if(AlfaLibsApplication.DEBUG){
                httpClient.addInterceptor(logging);
            }
            HttpInstance.otherUrl = otherUrl[0];
            retroMaps.put(HttpInstance.otherUrl, (new Retrofit.Builder()).baseUrl(
                    HttpInstance.otherUrl).client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build());
            return ((Retrofit)retroMaps.get(HttpInstance.otherUrl)).create(clazz);
        } else {
            return ((Retrofit)retroMaps.get(HttpInstance.baseUrl)).create(clazz);
        }
    }

    public static <HTTP> HTTP mock(Context context, Class<HTTP> clazz) {
        if(!retroMaps.containsKey(Initial.DOMAIN_MOCK)) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder mOkHttpClient = new OkHttpClient.Builder().addInterceptor(new OkHttpMockInterceptor(context, 5));
            if(AlfaLibsApplication.DEBUG){
                mOkHttpClient.addInterceptor(logging);
            }
            retroMaps.put(Initial.DOMAIN_MOCK,
                    new Retrofit.Builder().client(mOkHttpClient.build())
//                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl(Initial.DOMAIN_MOCK)
                    .build());
        }
        return retroMaps.get(Initial.DOMAIN_MOCK).create(clazz);
    }
    public static <HTTP, F extends Fragment> HTTP mock(F fragment, Class<HTTP> clazz) {
        return mock(fragment.getContext(), clazz);
    }

    public static <O extends Observable<?>> O with(final Observable<?> observable){
        return (O)observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public static final <T> Disposable call(Observable<T> observable){
        observable = with(observable);
        return observable.subscribe();
    }
    public static final <T> Disposable call(Observable<T> observable, Consumer<? super T> onAny){
        observable = with(observable);
        return observable.subscribe(onAny);
    }
    public static final <T> Disposable call(Observable<T> observable, Consumer<? super T> onAny, Consumer<? super Throwable> onError){
        observable = with(observable);
        return observable.subscribe(onAny, onError);
    }

    public static final void init(String baseUrl) {
        HttpInstance.baseUrl = baseUrl;
        retroMaps.put(HttpInstance.baseUrl, (new Retrofit.Builder()).baseUrl(HttpInstance.baseUrl).addConverterFactory(GsonConverterFactory.create()).build());
    }


}

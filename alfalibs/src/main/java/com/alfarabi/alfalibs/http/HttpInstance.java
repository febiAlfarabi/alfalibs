package com.alfarabi.alfalibs.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;


import com.alfarabi.alfalibs.AlfaLibsApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.schedulers.SchedulerWhen;
import io.reactivex.internal.schedulers.SingleScheduler;
import io.reactivex.schedulers.Schedulers;
import ir.mirrajabi.okhttpjsonmock.interceptors.OkHttpMockInterceptor;
import lombok.Getter;
import lombok.Setter;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 02/07/2017.
 */

public class HttpInstance {

    public static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    static @Getter@Setter int readTimeout ;
    static @Getter@Setter int conTimeout ;
    static @Getter@Setter int writeTimeout ;
    private static String baseUrl;
    private static String otherUrl;
    private static  Disposable disposable ;

    private static Subscription subscription ;

    private static final HashMap<String, Retrofit> retroMaps = new HashMap();
    static @Getter@Setter Gson gson = new GsonBuilder().setLenient().create();
    static @Getter@Setter HashMap<String, String> headerMap = new HashMap<>();

    @Getter@Setter public ProgressDialog progressDialog ;

    public HttpInstance() {}


    /**
     * This method created for actual request http, this request will call base url which you defined on previous initialization using HttpInstance.init(...)
     * but you can pass another endpoint in the last of param by example HttpInstance.crteate(Context, ServiceClass.class, "http://wwww.google.com")
     //     * @param context
     //     * @param clazz
     * @param otherUrl
     * @param <HTTP>
     * @return
     */
    public static <HTTP> HTTP create(Class<HTTP> clazz, String... otherUrl) {
        if(retroMaps == null) {
            throw new NullPointerException("RETROFIT == null");
        } else if(baseUrl == null && otherUrl == null) {
            throw new NullPointerException("URL == null");
        } else if(otherUrl != null && otherUrl.length > 0) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .connectTimeout(conTimeout, TimeUnit.SECONDS).readTimeout(readTimeout, TimeUnit.SECONDS).writeTimeout(writeTimeout, TimeUnit.SECONDS);
            if(AlfaLibsApplication.DEBUG){
                httpClient.addInterceptor(logging);
            }
            if(headerMap!=null && headerMap.size()>0){
                Interceptor interceptor = new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder request = chain.request().newBuilder();
                        Iterator<Map.Entry<String, String>> iterator = headerMap.entrySet().iterator();
                        while (iterator.hasNext()){
                            Map.Entry<String, String> next = iterator.next();
                            request.addHeader(next.getKey(), next.getValue());
                        }
                        return chain.proceed(request.build());
                    }
                };
                httpClient.addInterceptor(interceptor);
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

    /**
     * This method created for actual request http, this request will call base url which you defined on previous initialization using HttpInstance.init(...)
     * but you can pass another endpoint in the last of param by example HttpInstance.crteate(Context, ServiceClass.class, "http://wwww.google.com")
     * @param context
     * @param clazz
     * @param otherUrl
     * @param <HTTP>
     * @return
     */
    public static <HTTP> HTTP create(Context context, Class<HTTP> clazz, String... otherUrl) {
        if(retroMaps == null) {
            throw new NullPointerException("RETROFIT == null");
        } else if(baseUrl == null && otherUrl == null) {
            throw new NullPointerException("URL == null");
        } else if(otherUrl != null && otherUrl.length > 0) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder().connectTimeout(conTimeout, TimeUnit.SECONDS).readTimeout(readTimeout, TimeUnit.SECONDS).writeTimeout(writeTimeout, TimeUnit.SECONDS);
            if(AlfaLibsApplication.DEBUG){
                httpClient.addInterceptor(logging);
            }

            if(headerMap!=null && headerMap.size()>0){
                Interceptor interceptor = chain -> {
                    Request.Builder request = chain.request().newBuilder();
                    Iterator<Map.Entry<String, String>> iterator = headerMap.entrySet().iterator();
                    while (iterator.hasNext()){
                        Map.Entry<String, String> next = iterator.next();
                        request.addHeader(next.getKey(), next.getValue());
                    }
                    return chain.proceed(request.build());
                };
                httpClient.addInterceptor(interceptor);
            }
            HttpInstance.otherUrl = otherUrl[0];
            retroMaps.put(HttpInstance.otherUrl, (new Retrofit.Builder()).baseUrl(
                    HttpInstance.otherUrl).client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build());
            ((Retrofit)retroMaps.get(HttpInstance.otherUrl)).create(clazz);
            return ((Retrofit)retroMaps.get(HttpInstance.otherUrl)).create(clazz);
        } else {
            return ((Retrofit)retroMaps.get(HttpInstance.baseUrl)).create(clazz);
        }
    }

    /**
     * This method especially created for mocking json only, put your file inside assets/api/
     * and define your service path user Class Service by example ExampleService.class
     *
     * @param context
     * @param clazz
     * @param <HTTP>
     * @return
     */
    public static <HTTP> HTTP mock(Context context, Class<HTTP> clazz) {
        if(!retroMaps.containsKey(Initial.DOMAIN_MOCK)) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(new OkHttpMockInterceptor(context, 5))
                    .connectTimeout(conTimeout, TimeUnit.SECONDS).readTimeout(readTimeout, TimeUnit.SECONDS).writeTimeout(writeTimeout, TimeUnit.SECONDS);
            if(AlfaLibsApplication.DEBUG){
                httpClient.addInterceptor(logging);
            }
            if(headerMap!=null && headerMap.size()>0){
                Interceptor interceptor = new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder request = chain.request().newBuilder();
                        Iterator<Map.Entry<String, String>> iterator = headerMap.entrySet().iterator();
                        while (iterator.hasNext()){
                            Map.Entry<String, String> next = iterator.next();
                            request.addHeader(next.getKey(), next.getValue());
                        }
                        return chain.proceed(request.build());
                    }
                };
                httpClient.addInterceptor(interceptor);
            }
            retroMaps.put(Initial.DOMAIN_MOCK,
                    new Retrofit.Builder().client(httpClient.build())
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

    /**
     * This method used for base url define, its better to define on Application
     *
     * @param baseUrl is a string url which you choosed as endpoint,
     *                by example http://www.google.com
     */
    public static final void init(String baseUrl, int conTimeout, int readTimeout, int writeTimeout) {
        HttpInstance.baseUrl = baseUrl;
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(conTimeout, TimeUnit.SECONDS).readTimeout(readTimeout, TimeUnit.SECONDS).writeTimeout(writeTimeout, TimeUnit.SECONDS);
        if(AlfaLibsApplication.DEBUG){
            httpClient.addInterceptor(logging);
        }
        if(headerMap!=null && headerMap.size()>0){
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder request = chain.request().newBuilder();
                    Iterator<Map.Entry<String, String>> iterator = headerMap.entrySet().iterator();
                    while (iterator.hasNext()){
                        Map.Entry<String, String> next = iterator.next();
                        request.addHeader(next.getKey(), next.getValue());
                    }
                    return chain.proceed(request.build());
                }
            };
            httpClient.addInterceptor(interceptor);
        }
        retroMaps.put(HttpInstance.baseUrl, (new Retrofit.Builder()).baseUrl(HttpInstance.baseUrl).client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build());
    }

    public static void putHeaderMap(String key, String value) {
        headerMap.put(key, value);
        if(baseUrl!=null){
            init(HttpInstance.baseUrl, conTimeout, readTimeout, writeTimeout);
        }
    }

    public static HttpInstance withProgress(Context context, int... customlayoutProgress){
        HttpInstance httpInstance = new HttpInstance();
        if(customlayoutProgress==null || customlayoutProgress.length==0){
            httpInstance.progressDialog = ProgressDialog.show(context, "", "Loading...");
            httpInstance.progressDialog.getWindow().getDecorView().setBackgroundColor(Color.WHITE);
//            httpInstance.progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_DARK);
//            // set indeterminate style
//            httpInstance.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            httpInstance.progressDialog.getWindow().getDecorView().setBackgroundColor(Color.WHITE);
//            // set title and message
//            httpInstance.progressDialog.setTitle("");
//            httpInstance.progressDialog.setMessage("Loading...");
        }else{
            httpInstance.progressDialog = new ProgressDialog(context);
            httpInstance.progressDialog.getWindow().setContentView(customlayoutProgress[0]);
            httpInstance.progressDialog.getWindow().getDecorView().setBackgroundColor(Color.WHITE);
            httpInstance.progressDialog.setMessage("Loading...");
            httpInstance.progressDialog.show();
        }
        return  httpInstance ;
    }

    public <T> Disposable observe(Observable<T> observable){
        if(disposable!=null && !disposable.isDisposed()){
            observable.unsubscribeOn(Schedulers.io());
            disposable.dispose();
            if(progressDialog!=null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
        observable = with(observable);
        return disposable = observable.subscribe();
    }
    public <T> Disposable observe(Observable<T> observable, Consumer<? super T> onAny){
        if(disposable!=null && !disposable.isDisposed()){
            observable.unsubscribeOn(Schedulers.io());
            disposable.dispose();
            if(progressDialog!=null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
        observable = with(observable);
        return disposable = observable.subscribe(t -> {
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
            onAny.accept(t);
        });
    }
    public <T> Disposable observe(Observable<T> observable, Consumer<? super T> onAny, Consumer<? super Throwable> onError){
        if(disposable!=null && !disposable.isDisposed()){
            observable.unsubscribeOn(Schedulers.io());
            disposable.dispose();
            if(progressDialog!=null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
        observable = with(observable);
        return disposable = observable.subscribe(t -> {
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
            onAny.accept(t);
        },throwable -> {
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
            onError.accept(throwable);
        });
    }
    public <T> Disposable observe(Observable<T> observable, Consumer<? super T> onAny, Consumer<? super Throwable> onError, boolean disposePrevious){
        if(disposable!=null && !disposable.isDisposed() && disposePrevious){
            observable.unsubscribeOn(Schedulers.io());
            disposable.dispose();
            if(progressDialog!=null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
        observable = with(observable);
        return disposable = observable.subscribe(t -> {
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
            onAny.accept(t);
        },throwable -> {
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
            onError.accept(throwable);
        });
    }


    public static <T> Disposable call(Observable<T> observable){
        if(disposable!=null && !disposable.isDisposed()){
            observable.unsubscribeOn(Schedulers.io());
            disposable.dispose();
        }
        HttpInstance httpInstance = new HttpInstance();
        return disposable = httpInstance.observe(observable);
    }
    public static <T> Disposable call(Observable<T> observable, Consumer<? super T> onAny){
        if(disposable!=null && !disposable.isDisposed()){
            observable.unsubscribeOn(Schedulers.io());
            disposable.dispose();
        }
        HttpInstance httpInstance = new HttpInstance();
        return disposable = httpInstance.observe(observable, onAny);
    }
    /**
     *
     * @param observable its used to passing your request as observable, use this by example HttpInstance.mock(....) or HttpInstance.create(.....)
     * @param onAny this will be called after request finished and response get already from your request
     * @param onError On error triggerd
     * @param <T>
     * @return
     */
    public static <T> Disposable call(Observable<T> observable, Consumer<? super T> onAny, Consumer<? super Throwable> onError){
        if(disposable!=null && !disposable.isDisposed()){
            observable.unsubscribeOn(Schedulers.io());
            disposable.dispose();
        }
        HttpInstance httpInstance = new HttpInstance();
        return disposable = httpInstance.observe(observable, onAny, onError);
    }

    public static <T> Disposable call(Observable<T> observable, Consumer<? super T> onAny, Consumer<? super Throwable> onError, boolean disposePrevious){
        if(disposable!=null && !disposable.isDisposed() && disposePrevious){
            observable.unsubscribeOn(Schedulers.io());
            disposable.dispose();
        }
        HttpInstance httpInstance = new HttpInstance();
        return disposable = httpInstance.observe(observable, onAny, onError, disposePrevious);
    }


}

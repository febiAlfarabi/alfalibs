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
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
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


/*
* Now this class is become deprecated, use Kitchen to integrate cook Alfalibs library
*
* */
public class Kitchen {

    public static final String TAG = Kitchen.class.getName();

    public static final int LIVE_MODE = 1 ;
    public static final int MOCK_MODE = 0 ;


    public static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    static @Getter@Setter int readTimeout ;
    static @Getter@Setter int conTimeout ;
    static @Getter@Setter int writeTimeout ;
    private static String prodUrl;
    private static String passedUrl;
    private static  Disposable disposable ;


    private static final HashMap<String, Retrofit> retroMaps = new HashMap();
    static @Getter@Setter Gson gson = new GsonBuilder().setLenient().create();
    static @Getter@Setter HashMap<String, String> headerMap = new HashMap<>();

    @Getter@Setter public ProgressDialog progressDialog ;

    private int kitchenMode = LIVE_MODE;

    public Kitchen() {

    }


    /**
     * This method created for actual request http, this request will sink base url which you defined on previous initialization using kitchen.preparation(...)
     * but you can pass another endpoint in the last of param by example kitchen.create(Context, ServiceClass.class, "http://wwww.google.com")
     //     * @param context
     //     * @param clazz
     * @param passedUrl
     * @param <HTTP>
     * @return
     */
    private static <HTTP> HTTP arange(Class<HTTP> clazz, String... passedUrl) {
        if(retroMaps == null) {
            throw new NullPointerException("RETROFIT == null");
        } else if(prodUrl == null && passedUrl == null) {
            throw new NullPointerException("URL == null");
        } else if(passedUrl != null && passedUrl.length > 0) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .connectTimeout(conTimeout, TimeUnit.SECONDS).readTimeout(readTimeout, TimeUnit.SECONDS).writeTimeout(writeTimeout, TimeUnit.SECONDS);
            if(AlfaLibsApplication.DEBUG){
                httpClient.addInterceptor(logging);
            }
            if(headerMap!=null && headerMap.size()>0){

                CustomInterceptor interceptor = new CustomInterceptor() {
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
            Kitchen.passedUrl = passedUrl[0];
            retroMaps.put(Kitchen.passedUrl, (new Retrofit.Builder()).baseUrl(
                    Kitchen.passedUrl).client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build());
            return ((Retrofit)retroMaps.get(Kitchen.passedUrl)).create(clazz);
        } else {
            return ((Retrofit)retroMaps.get(Kitchen.prodUrl)).create(clazz);
        }
    }

    /**
     * This method created for actual request http, this request will sink base url which you defined on previous initialization using kitchen.preparation(...)
     * but you can pass another endpoint in the last of param by example kitchen.crteate(Context, ServiceClass.class, "http://wwww.google.com")
     * @param context
     * @param clazz
     * @param passedUrl
     * @param <HTTP>
     * @return
     */
    public static <HTTP> HTTP arange(Context context, Class<HTTP> clazz, String... passedUrl) {
        if(retroMaps == null) {
            throw new NullPointerException("RETROFIT == null");
        } else if(prodUrl == null && passedUrl == null) {
            throw new NullPointerException("URL == null");
        } else if(passedUrl != null && passedUrl.length > 0) {
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
            Kitchen.passedUrl = passedUrl[0];
            retroMaps.put(Kitchen.passedUrl, (new Retrofit.Builder()).baseUrl(
                    Kitchen.passedUrl).client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build());
            ((Retrofit)retroMaps.get(Kitchen.passedUrl)).create(clazz);
            return ((Retrofit)retroMaps.get(Kitchen.passedUrl)).create(clazz);
        } else {
            return ((Retrofit)retroMaps.get(Kitchen.prodUrl)).create(clazz);
        }
    }

    public static <HTTP> HTTP arange(Context context, Class<HTTP> clazz, int kitchenMode, String... passedUrl) {
        if(retroMaps == null) {
            throw new NullPointerException("RETROFIT == null");
        } else if(prodUrl == null && passedUrl == null) {
            throw new NullPointerException("URL == null");
        } else if(passedUrl != null && passedUrl.length > 0) {
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
            Kitchen.passedUrl = passedUrl[0];
            retroMaps.put(Kitchen.passedUrl, (new Retrofit.Builder()).baseUrl(
                    Kitchen.passedUrl).client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build());
            ((Retrofit)retroMaps.get(Kitchen.passedUrl)).create(clazz);
            return ((Retrofit)retroMaps.get(Kitchen.passedUrl)).create(clazz);
        } else {
            if(kitchenMode==MOCK_MODE){
                return  mock(context, clazz);
            }
            return ((Retrofit) retroMaps.get(Kitchen.prodUrl)).create(clazz);
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
    private static <HTTP> HTTP mock(Context context, Class<HTTP> clazz) {
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
//                            .addConverterFactory(ScalarsConverterFactory.spoon())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl(Initial.DOMAIN_MOCK)
                            .build());
        }
        return retroMaps.get(Initial.DOMAIN_MOCK).create(clazz);
    }

    private static <HTTP, F extends Fragment> HTTP mock(F fragment, Class<HTTP> clazz) {
        return mock(fragment.getContext(), clazz);
    }

    private static <O extends Observable<?>> O with(final Observable<?> observable){
        return (O)observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * This method used for base url define, its better to define on Application
     *
     * @param baseUrl is a string url which you choosed as endpoint,
     *                by example http://www.google.com
     */
    public static final void preparation(String baseUrl, int conTimeout, int readTimeout, int writeTimeout) {
        Kitchen.prodUrl = baseUrl;
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
        retroMaps.put(Kitchen.prodUrl, (new Retrofit.Builder()).baseUrl(Kitchen.prodUrl).client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build());
    }

    public static void putHanger(String key, String value) {
        headerMap.put(key, value);
        if(prodUrl!=null){
            preparation(Kitchen.prodUrl, conTimeout, readTimeout, writeTimeout);
        }
    }

    public static Kitchen usePan(Context context, int... customlayoutProgress){
        Kitchen kitchen = new Kitchen();
        if(customlayoutProgress==null || customlayoutProgress.length==0){
            kitchen.progressDialog = ProgressDialog.show(context, "", "Loading...");
            kitchen.progressDialog.getWindow().getDecorView().setBackgroundColor(Color.WHITE);
//            kitchen.progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_DARK);
//            // set indeterminate style
//            kitchen.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            kitchen.progressDialog.getWindow().getDecorView().setBackgroundColor(Color.WHITE);
//            // set title and message
//            kitchen.progressDialog.setTitle("");
//            kitchen.progressDialog.setMessage("Loading...");
        }else{
            kitchen.progressDialog = new ProgressDialog(context);
            kitchen.progressDialog.getWindow().setContentView(customlayoutProgress[0]);
            kitchen.progressDialog.getWindow().getDecorView().setBackgroundColor(Color.WHITE);
            kitchen.progressDialog.setMessage("Loading...");
            kitchen.progressDialog.show();
        }
        return  kitchen ;
    }

    public <T> Disposable pour(Observable<T> observable){
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
    public <T> Disposable pour(Observable<T> observable, Consumer<? super T> onAny){
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
    public <T> Disposable pour(Observable<T> observable, Consumer<? super T> onAny, Consumer<? super Throwable> onError){
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
    public <T> Disposable pour(Observable<T> observable, Consumer<? super T> onAny, Consumer<? super Throwable> onError, boolean disposePrevious){
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

    public static <T> Disposable cook(Observable<T> observable){
        if(disposable!=null && !disposable.isDisposed()){
            observable.unsubscribeOn(Schedulers.io());
            disposable.dispose();
        }
        Kitchen kitchen = new Kitchen();
        return disposable = kitchen.pour(observable);
    }

    public static <T> Disposable cook(Observable<T> observable, Consumer<? super T> onAny){
        if(disposable!=null && !disposable.isDisposed()){
            observable.unsubscribeOn(Schedulers.io());
            disposable.dispose();
        }
        Kitchen kitchen = new Kitchen();
        return disposable = kitchen.pour(observable, onAny);
    }
    /**
     *
     * @param observable its used to passing your request as observable, use this by example kitchen.fork(....) or kitchen.spoon(.....)
     * @param onAny this will be called after request finished and response get already from your request
     * @param onError On error triggerd
     * @param <T>
     * @return
     */
    public static <T> Disposable cook(Observable<T> observable, Consumer<? super T> onAny, Consumer<? super Throwable> onError){
        if(disposable!=null && !disposable.isDisposed()){
            observable.unsubscribeOn(Schedulers.io());
            disposable.dispose();
        }
        Kitchen kitchen = new Kitchen();
        return disposable = kitchen.pour(observable, onAny, onError);
    }

    public static <T> Disposable cook(Observable<T> observable, Consumer<? super T> onAny, Consumer<? super Throwable> onError, boolean disposePrevious){
        if(disposable!=null && !disposable.isDisposed() && disposePrevious){
            observable.unsubscribeOn(Schedulers.io());
            disposable.dispose();
        }
        Kitchen kitchen = new Kitchen();
        return disposable = kitchen.pour(observable, onAny, onError, disposePrevious);
    }

}

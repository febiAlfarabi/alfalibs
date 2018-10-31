package com.alfarabi.alfalibs.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;

import com.alfarabi.alfalibs.AlfaLibsApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhy.http.okhttp.https.HttpsUtils;

import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.github.lizhangqu.coreprogress.ProgressHelper;
import io.github.lizhangqu.coreprogress.ProgressUIListener;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ir.mirrajabi.okhttpjsonmock.interceptors.OkHttpMockInterceptor;
import lombok.Getter;
import lombok.Setter;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.functions.Action;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Action3;
import rx.functions.Func2;

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
    private static boolean usingHttps;
    private static  Disposable disposable ;


    public static boolean connected = true ;

    private static final HashMap<String, Retrofit> retroMaps = new HashMap();
    static @Getter@Setter Gson gson = new GsonBuilder().setLenient().create();
    static @Getter@Setter HashMap<String, String> headerMap = new HashMap<>();

    private int kitchenMode = LIVE_MODE;


    public static Response currentResponse ;

    public Kitchen() {}


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
        Kitchen.currentResponse = null ;
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
            if(Kitchen.usingHttps){
                HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
                httpClient.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
                ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .cipherSuites(
                                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                                CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                        .build();
                httpClient.connectionSpecs(Collections.singletonList(spec));
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
                        Kitchen.currentResponse = chain.proceed(request.build());
                        return Kitchen.currentResponse;
                    }
                };
                httpClient.addInterceptor(interceptor);
            }
            if(Kitchen.progressUIListener!=null){
                Interceptor progressInterceptor = chain -> {
                    Request request = chain.request();
                    ProgressHelper.withProgress(request.body(), Kitchen.progressUIListener);
                    Kitchen.currentResponse = chain.proceed(request);
                    return Kitchen.currentResponse ;
                };
                httpClient.addInterceptor(progressInterceptor);
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
        Kitchen.currentResponse = null ;
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
            if(Kitchen.usingHttps){
                HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
                httpClient.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
                ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .cipherSuites(
                                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                                CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                        .build();
                httpClient.connectionSpecs(Collections.singletonList(spec));
            }

            if(headerMap!=null && headerMap.size()>0){
                Interceptor interceptor = chain -> {
                    Request.Builder request = chain.request().newBuilder();
                    Iterator<Map.Entry<String, String>> iterator = headerMap.entrySet().iterator();
                    while (iterator.hasNext()){
                        Map.Entry<String, String> next = iterator.next();
                        request.addHeader(next.getKey(), next.getValue());
                    }
                    Kitchen.currentResponse = chain.proceed(request.build());
                    return Kitchen.currentResponse;
                };
                httpClient.addInterceptor(interceptor);
            }
            if(Kitchen.progressUIListener!=null){
                Interceptor progressInterceptor = chain -> {
                    Request request = chain.request();
                    ProgressHelper.withProgress(request.body(), Kitchen.progressUIListener);
                    Kitchen.currentResponse = chain.proceed(request);
                    return Kitchen.currentResponse ;
                };
                httpClient.addInterceptor(progressInterceptor);
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

    public static <HTTP> HTTP arange(Context context, Class<HTTP> clazz, int kitchenMode, String... passedUrl) {
        Kitchen.currentResponse = null ;
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
            if(Kitchen.usingHttps){
                HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
                httpClient.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
                ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .cipherSuites(
                                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                                CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                        .build();
                httpClient.connectionSpecs(Collections.singletonList(spec));
            }
            if(headerMap!=null && headerMap.size()>0){
                Interceptor interceptor = chain -> {
                    Request.Builder request = chain.request().newBuilder();
                    Iterator<Map.Entry<String, String>> iterator = headerMap.entrySet().iterator();
                    while (iterator.hasNext()){
                        Map.Entry<String, String> next = iterator.next();
                        request.addHeader(next.getKey(), next.getValue());
                    }
                    Kitchen.currentResponse = chain.proceed(request.build());
                    return Kitchen.currentResponse;
                };
            }
            if(Kitchen.progressUIListener!=null){
                Interceptor progressInterceptor = chain -> {
                    Request request = chain.request();
                    ProgressHelper.withProgress(request.body(), Kitchen.progressUIListener);
                    Kitchen.currentResponse = chain.proceed(request);
                    return Kitchen.currentResponse ;
                };
                httpClient.addInterceptor(progressInterceptor);
            }
            Kitchen.passedUrl = passedUrl[0];
            retroMaps.put(Kitchen.passedUrl, (new Retrofit.Builder()).baseUrl(
                    Kitchen.passedUrl).client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build());
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
        Kitchen.currentResponse = null ;
        if(!retroMaps.containsKey(Initial.DOMAIN_MOCK)) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(new OkHttpMockInterceptor(context, 5))
                    .connectTimeout(conTimeout, TimeUnit.SECONDS).readTimeout(readTimeout, TimeUnit.SECONDS).writeTimeout(writeTimeout, TimeUnit.SECONDS);
            if(AlfaLibsApplication.DEBUG){
                httpClient.addInterceptor(logging);
            }
            if(Kitchen.usingHttps){
                HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
                httpClient.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
                ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .cipherSuites(
                                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                                CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                        .build();
                httpClient.connectionSpecs(Collections.singletonList(spec));
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
                        Kitchen.currentResponse = chain.proceed(request.build());
                        return Kitchen.currentResponse;
                    }
                };
                httpClient.addInterceptor(interceptor);
            }
            if(Kitchen.progressUIListener!=null){
                Interceptor progressInterceptor = chain -> {
                    Request request = chain.request();
                    ProgressHelper.withProgress(request.body(), Kitchen.progressUIListener);
                    Kitchen.currentResponse = chain.proceed(request);
                    return Kitchen.currentResponse ;
                };
                httpClient.addInterceptor(progressInterceptor);
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
    public static final void preparation(String baseUrl, int conTimeout, int readTimeout, int writeTimeout, boolean usingHttps) {
        Kitchen.currentResponse = null ;
        Kitchen.prodUrl = baseUrl;
        Kitchen.usingHttps = usingHttps ;
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(conTimeout, TimeUnit.SECONDS).readTimeout(readTimeout, TimeUnit.SECONDS).writeTimeout(writeTimeout, TimeUnit.SECONDS);
        if(Kitchen.usingHttps){
            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
            httpClient.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
            ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2)
                    .cipherSuites(
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                    .build();
            httpClient.connectionSpecs(Collections.singletonList(spec));
        }
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
                    Kitchen.currentResponse = chain.proceed(request.build());
                    return Kitchen.currentResponse;
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
            preparation(Kitchen.prodUrl, Kitchen.conTimeout, Kitchen.readTimeout, Kitchen.writeTimeout, Kitchen.usingHttps);
        }
    }


    static ProgressUIListener progressUIListener ;
    public static Kitchen usePan(ProgressUIListener progressUIListener){
        Kitchen kitchen = new Kitchen();
        kitchen.progressUIListener = progressUIListener;
        return  kitchen ;
    }

    public <T> Disposable pour(Observable<T> observable){
        if(disposable!=null && !disposable.isDisposed()){
            observable.unsubscribeOn(Schedulers.io());
            disposable.dispose();
        }
        observable = with(observable);
        return disposable = observable.subscribe();
    }
    public <T> Disposable pour(Observable<T> observable, Consumer<? super T> onAny){
        if(disposable!=null && !disposable.isDisposed()){
            observable.unsubscribeOn(Schedulers.io());
            disposable.dispose();
        }
        observable = with(observable);
        return disposable = observable.subscribe(t -> {
            onAny.accept(t);
        });
    }
    public <T> Disposable pour(Observable<T> observable, Consumer<? super T> onAny, Consumer<? super Throwable> onError){
        if(disposable!=null && !disposable.isDisposed()){
            observable.unsubscribeOn(Schedulers.io());
            disposable.dispose();
        }
        observable = with(observable);
        return disposable = observable.subscribe(t -> {
            onAny.accept(t);
        },throwable -> {
            onError.accept(throwable);
        });
    }

    public <T> Disposable pour(Observable<T> observable, Action2<? super T, Response> onAny, Action3<Boolean, ? super Throwable, Response> onError){
        if(disposable!=null && !disposable.isDisposed()){
            observable.unsubscribeOn(Schedulers.io());
            disposable.dispose();
        }
        if(connected){
            observable = with(observable);
            return disposable = observable.subscribe(t -> {
                onAny.call(t, Kitchen.currentResponse);
            },throwable -> {
                onError.call(connected, throwable, Kitchen.currentResponse);
            });
        }else{
            return disposable = observable.subscribe(t -> {
                new Exception("No Connection Available, Please Check Your Device Connection");
            },throwable -> {
                onError.call(connected, throwable, null);
            });
        }
    }


    public <T> Disposable pour(Observable<T> observable, Consumer<? super T> onAny, Consumer<? super Throwable> onError, boolean disposePrevious){
        if(disposable!=null && !disposable.isDisposed() && disposePrevious){
            observable.unsubscribeOn(Schedulers.io());
            disposable.dispose();
        }
        observable = with(observable);
        return disposable = observable.subscribe(t -> {
            onAny.accept(t);
        },throwable -> {
            onError.accept(throwable);
        });
    }

    public <T> Disposable pour(Observable<T> observable, Action2<? super T, Response> onAny, Action3<Boolean, ? super Throwable, Response> onError, boolean disposePrevious){
        if(disposable!=null && !disposable.isDisposed() && disposePrevious){
            observable.unsubscribeOn(Schedulers.io());
            disposable.dispose();
        }
        if(connected){
            observable = with(observable);
            return disposable = observable.subscribe(t -> {
                onAny.call(t, Kitchen.currentResponse);
            },throwable -> {
                onError.call(connected, throwable, Kitchen.currentResponse);
            });
        }else{
            return disposable = observable.subscribe(t -> {
                new Exception("No Connection Available, Please Check Your Device Connection");
            },throwable -> {
                onError.call(connected, throwable, null);
            });
        }
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

    public static <T> Disposable cook(Observable<T> observable, Action2<? super T, Response> onAny, Action3<Boolean, ? super Throwable, Response> onError){
        if(disposable!=null && !disposable.isDisposed()){
            observable.unsubscribeOn(Schedulers.io());
            disposable.dispose();
        }
        Kitchen kitchen = new Kitchen();
        return disposable = kitchen.pour(observable, onAny, onError);
    }

    public static <T> Disposable cook(Observable<T> observable, Action2<? super T, Response> onAny, Action3<Boolean, ? super Throwable, Response> onError, boolean disposePrevious){
        if(disposable!=null && !disposable.isDisposed() && disposePrevious){
            observable.unsubscribeOn(Schedulers.io());
            disposable.dispose();
        }
        Kitchen kitchen = new Kitchen();
        return disposable = kitchen.pour(observable, onAny, onError, disposePrevious);
    }


}

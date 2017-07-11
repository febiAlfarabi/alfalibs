//package com.alfarabi.alfalibs.http;
//
//import android.content.Context;
//
//import java.util.HashMap;
//
//import ir.mirrajabi.okhttpjsonmock.interceptors.OkHttpMockInterceptor;
//import okhttp3.OkHttpClient;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
///**
// * Created by Alfarabi on 6/30/17.
// */
//
//public abstract class HttpService {
//
//    private static String baseUrl ;
//    private static String otherUrl ;
//
//
//    private static final HashMap<String,Retrofit> retroMaps = new HashMap<>() ;
//
//    public static <HTTP> HTTP create(Class<HTTP> clazz, String... otherUrl){
//        if(retroMaps== null){
//            throw new NullPointerException("RETROFIT == null");
//        }
//        if(HttpService.baseUrl==null && otherUrl==null){
//            throw new NullPointerException("URL == null");
//        }
//        if(otherUrl!=null && otherUrl.length>0){
//            HttpService.otherUrl = otherUrl[0] ;
//            retroMaps.put(HttpService.otherUrl, new Retrofit.Builder().baseUrl(HttpService.otherUrl).addConverterFactory(GsonConverterFactory.create()).build());
//            return retroMaps.get(HttpService.otherUrl).create(clazz);
//        }else{
//            return retroMaps.get(HttpService.baseUrl).create(clazz);
//        }
//    }
//
//    public static <HTTP> HTTP createMock(Context context, Class<HTTP> clazz, String... otherUrl){
//
//        OkHttpClient mOkHttpClient = new OkHttpClient.Builder().addInterceptor(new OkHttpMockInterceptor(context, 5)).build();
//        if(retroMaps== null){
//            throw new NullPointerException("RETROFIT == null");
//        }
//        if(HttpService.baseUrl==null && otherUrl==null){
//            throw new NullPointerException("URL == null");
//        }
//        if(otherUrl!=null && otherUrl.length>0){
//            HttpService.otherUrl = otherUrl[0] ;
//            retroMaps.put(HttpService.otherUrl, new Retrofit.Builder()
//                    .baseUrl(HttpService.otherUrl).addConverterFactory(GsonConverterFactory.create())
//                    .client(mOkHttpClient)
//                    .build());
//            return retroMaps.get(HttpService.otherUrl).create(clazz);
//        }else{
//            throw new NullPointerException("URL FOR YOUR MOCK == null");
////            return retroMaps.get(HttpService.baseUrl).create(clazz);
//        }
//    }
//
//
//    public static final void init(String baseUrl){
//        HttpService.baseUrl = baseUrl ;
//        retroMaps.put(HttpService.baseUrl, new Retrofit.Builder().baseUrl(HttpService.baseUrl).addConverterFactory(GsonConverterFactory.create()).build());
//    }
//}

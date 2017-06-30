package com.alfarabi.alfalibs.http;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alfarabi on 6/30/17.
 */

public abstract class HttpService {

    private static String baseUrl ;

    private static final HashMap<String,Retrofit> retroMaps = new HashMap<>() ;

    public static <HTTP> HTTP create(Class<HTTP> clazz, String... baseUrl){
        if(retroMaps== null){
            throw new NullPointerException("RETROFIT == null");
        }
        if(HttpService.baseUrl==null && baseUrl==null){
            throw new NullPointerException("URL == null");
        }
        if(baseUrl!=null && baseUrl.length>0){
            HttpService.baseUrl = baseUrl[0] ;
            retroMaps.put(HttpService.baseUrl, new Retrofit.Builder().baseUrl(HttpService.baseUrl).addConverterFactory(GsonConverterFactory.create()).build());
        }
        return retroMaps.get(HttpService.baseUrl).create(clazz);
    }

    public static final void init(String baseUrl){
        HttpService.baseUrl = baseUrl ;
        retroMaps.put(HttpService.baseUrl, new Retrofit.Builder().baseUrl(HttpService.baseUrl).addConverterFactory(GsonConverterFactory.create()).build());
    }
}

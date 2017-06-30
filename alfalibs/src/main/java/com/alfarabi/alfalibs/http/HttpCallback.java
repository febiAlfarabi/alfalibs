package com.alfarabi.alfalibs.http;

import com.alfarabi.alfalibs.tools.WLog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alfarabi on 6/30/17.
 */

public abstract class HttpCallback<CR> implements CallBackInterface<ContentResponse>{

    public static final String TAG = HttpCallback.class.getName();

    public HttpCallback(){

    }

    @Override
    public void onResponse(Call<ContentResponse> call, Response<ContentResponse> response) {
        getResponse(call, (CR) response.body());
    }

    @Override
    public void onFailure(Call<ContentResponse> call, Throwable t) {

    }

    protected abstract void getResponse(Call<ContentResponse> call, CR response);




}

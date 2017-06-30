package com.alfarabi.alfalibs.http;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Alfarabi on 6/30/17.
 */

public interface HttpCall<C> extends Call<C> {

    void enqueue(HttpCallback<C> callback);
}

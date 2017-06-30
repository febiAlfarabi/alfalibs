package com.alfarabi.alfalibsapps;

import android.app.Activity;
import android.os.Bundle;
import com.alfarabi.alfalibs.http.HttpService;
import com.alfarabi.alfalibs.http.mock.MockService;
import com.alfarabi.alfalibs.model.UserModel;
import com.alfarabi.alfalibs.tools.WLog;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {
    public static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        HttpService.create(GithubService.class).getSingleObjects().enqueue(new Callback<GithubResponse>() {
//            @Override
//            public void onResponse(Call<GithubResponse> call, Response<GithubResponse> response) {
//                WLog.i(TAG, response.headers().toString());
//                WLog.i(TAG, new Gson().toJson(response));
//            }
//
//            @Override
//            public void onFailure(Call<GithubResponse> call, Throwable t) {
//
//            }
//        });
        HttpService.createMock(this, MockService.class, "http://example.com").getUser(1).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                WLog.i(TAG, response.headers().toString());
                WLog.i(TAG, new Gson().toJson(response));
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }

}

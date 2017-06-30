package com.alfarabi.alfalibs.http.mock;

import com.alfarabi.alfalibs.model.UserModel;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Alfarabi on 6/30/17.
 */

public interface MockService {

    public static final String API_VERSION = "api/v1";

    //usage example /users/page=phoneNumbers.json
    @GET(API_VERSION + "/users")
    Call<ArrayList<UserModel>> getUsers(@Query("page") int page);

    //usage example /users/page=1&secondParameter=phoneNumbers.json
    @GET(API_VERSION + "/users")
    Call<ArrayList<UserModel>> getUsers(@Query("page") int page,
                                              @Query("name") String name);

    //usage example /users/1.json
    @GET(API_VERSION + "/users/{userId}")
    Call<UserModel> getUser(@Path("userId") int userId);

    //usage example /users/1/phoneNumbers.json
    @GET(API_VERSION + "/users/{userId}/phoneNumbers")
    Call<ArrayList<String>> getUserNumbers(@Path("userId") int userId);
}

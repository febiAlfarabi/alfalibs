package com.alfarabi.alfalibsapps;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Alfarabi on 6/30/17.
 */


public interface GithubService{


    public static final String GET_KEY = "username";
    public static final String DELETE_KEY = "id";
    public static final String BASE_PATH = "users";

    @GET(BASE_PATH+"/{"+GET_KEY+"}")
    Call<GithubResponse> getObject(@Path(GET_KEY) Object object);

    @GET(BASE_PATH+"/list")
    Call<List<GithubResponse>> getObjects();

    @GET(BASE_PATH+"/list")
    Call<GithubResponse> getSingleObjects();

    @GET(BASE_PATH+"/delete")
    Call<GithubResponse> deleteObjects(@Query(DELETE_KEY) Object object);


    @POST(BASE_PATH+"/update")
    Call<GithubResponse> updateObject(@Body Object o);


}

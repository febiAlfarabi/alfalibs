package com.alfarabi.alfalibs.http.mock;

import com.alfarabi.alfalibs.model.ExampleModel;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Alfarabi on 7/10/17.
 */

public interface ExampleService {



    @GET("/")
    Observable<ExampleModel> simpleResponse();

}

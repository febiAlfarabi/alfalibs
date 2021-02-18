package com.alfarabi.alfalibs.http.mock;

import com.alfarabi.alfalibs.model.ExampleModel;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Alfarabi on 7/10/17.
 */

public interface ExampleService {



    @GET("/")
    Observable<ExampleModel> simpleResponse();

}

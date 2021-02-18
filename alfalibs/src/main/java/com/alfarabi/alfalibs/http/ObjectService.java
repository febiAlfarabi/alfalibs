package com.alfarabi.alfalibs.http;


import java.util.List;

import retrofit2.Call;

/**
 * Created by Alfarabi on 6/30/17.
 */

public abstract interface ObjectService<O>{

    abstract Call<O> getObject(Object o);
    abstract Call<List<O>> getObjects();
    abstract Call<O> deleteObjects(Object o);
    abstract Call<O> updateObject(Object o);

}

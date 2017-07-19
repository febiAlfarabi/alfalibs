package com.alfarabi.alfalibs.views.interfaze;

import com.alfarabi.alfalibs.http.HttpInstance;
import com.alfarabi.alfalibs.views.AlfaRecyclerView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Alfarabi on 7/19/17.
 */

public interface LoadingInterface {

    public <T> void setOnRefreshListener(final Observable<T> observable, final Consumer<? super T> onAny);

    public <T> void setOnRefreshListener(final Observable<T> observable, final Consumer<? super T> onAny, final Consumer<? super Throwable> onError);

    public  <T> void loadFirst(final Observable<T> observable, final Consumer<? super T> onAny);

    public <T> Disposable loadFirst(final Observable<T> observable, final Consumer<? super T> onAny, final Consumer<? super Throwable> onError);

    public <T> Disposable load(final Observable<T> observable, final Consumer<? super T> onAny, final Consumer<? super Throwable> onError);

}

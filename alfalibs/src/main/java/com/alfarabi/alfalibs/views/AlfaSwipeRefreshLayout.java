package com.alfarabi.alfalibs.views;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Toast;


import com.alfarabi.alfalibs.R;
import com.alfarabi.alfalibs.http.HttpInstance;
import com.alfarabi.alfalibs.views.AlfaRecyclerView;
import com.alfarabi.alfalibs.views.interfaze.LoadingInterface;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import lombok.Getter;
import lombok.Setter;


public final class AlfaSwipeRefreshLayout extends android.support.v4.widget.SwipeRefreshLayout
        implements android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener, LoadingInterface {

    @Nullable private OnRefreshListener listener;
    @Getter@Setter boolean refreshable = true ;

    public AlfaSwipeRefreshLayout(Context context) {
        this(context, null);
        if(refreshable){
            setOnRefreshListener(() -> {
                if(getChildCount()>0){
                    if(getChildAt(0) instanceof AlfaRecyclerView){
                        ((AlfaRecyclerView) getChildAt(0)).checkIfEmpty();
                    }
                }
                postDelayed(() -> {
                    Toast.makeText(getContext(), R.string.swiperecyclerview_has_no_any_action, Toast.LENGTH_SHORT).show();
                    setRefreshing(false);
                }, 500);
            });
        }
    }

    public AlfaSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeResources(R.color.blue_A700);
        if(refreshable){
            setOnRefreshListener(() -> {
                if(getChildCount()>0){
                    if(getChildAt(0) instanceof AlfaRecyclerView){
                        ((AlfaRecyclerView) getChildAt(0)).checkIfEmpty();
                    }
                }
                postDelayed(() -> {
                    Toast.makeText(getContext(), R.string.swiperecyclerview_has_no_any_action, Toast.LENGTH_SHORT).show();
                    setRefreshing(false);
                }, 500);
            });
        }
    }

    @Override
    public void setOnRefreshListener(OnRefreshListener listener) {
        super.setOnRefreshListener(listener);
        this.listener = listener;
    }

    @Override
    public final <T> void setOnRefreshListener(final Observable<T> observable, final Consumer<? super T> onAny) {
        setOnRefreshListener(() -> {
            if(getChildCount()>0){
                if(getChildAt(0) instanceof AlfaRecyclerView){
                    ((AlfaRecyclerView) getChildAt(0)).checkIfEmpty();
                }
            }
            Observable<T> observation = HttpInstance.with(observable);
            observation.subscribe(t -> {
                setRefreshing(false);
                onAny.accept(t);
            });
        });
    }

    @Override
    public final <T> void setOnRefreshListener(final Observable<T> observable, final Consumer<? super T> onAny, final Consumer<? super Throwable> onError) {
        setOnRefreshListener(() -> {
            if(getChildCount()>0){
                if(getChildAt(0) instanceof AlfaRecyclerView){
                    ((AlfaRecyclerView) getChildAt(0)).checkIfEmpty();
                }
            }
            Observable<T> observation = HttpInstance.with(observable);
            observation.subscribe(t -> {
                setRefreshing(false);
                onAny.accept(t);
            }, throwable -> {
                setRefreshing(false);
                onError.accept(throwable);
            });
        });
    }

    @Override
    public final <T> void loadFirst(final Observable<T> observable, final Consumer<? super T> onAny) {
        setRefreshable(true);
        setRefreshing(true);
        if(getChildCount()>0){
            if(getChildAt(0) instanceof AlfaRecyclerView){
                ((AlfaRecyclerView) getChildAt(0)).refresh();
            }
        }
        Observable<T> observation = HttpInstance.with(observable);
        observation.subscribe(t -> {
            setRefreshing(false);
            onAny.accept(t);
        });
    }

    @Override
    public final <T> Disposable loadFirst(final Observable<T> observable, final Consumer<? super T> onAny, final Consumer<? super Throwable> onError) {
        setRefreshable(true);
        setRefreshing(true);
        if(getChildCount()>0){
            if(getChildAt(0) instanceof AlfaRecyclerView){
                ((AlfaRecyclerView) getChildAt(0)).refresh();
            }
        }
        Observable<T> observation = HttpInstance.with(observable);
        return observation.subscribe(t -> {
            setRefreshing(false);
            onAny.accept(t);
        }, throwable -> {
            setRefreshing(false);
            onError.accept(throwable);
        });
    }

    @Override
    public final <T> Disposable load(final Observable<T> observable, final Consumer<? super T> onAny, final Consumer<? super Throwable> onError) {
        setRefreshable(true);
        setRefreshing(true);
        if(getChildCount()>0){
            if(getChildAt(0) instanceof AlfaRecyclerView){
                ((AlfaRecyclerView) getChildAt(0)).refresh();
            }
        }
        Observable<T> observation = HttpInstance.with(observable);
        return observation.subscribe(t -> {
            setRefreshing(false);
            onAny.accept(t);
        }, throwable -> {
            setRefreshing(false);
            onError.accept(throwable);
        });
    }

    @Override
    public void onRefresh() {
        if (listener != null) {
            listener.onRefresh();
        }
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
    }
}
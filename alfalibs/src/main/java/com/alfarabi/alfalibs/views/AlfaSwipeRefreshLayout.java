package com.alfarabi.alfalibs.views;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Toast;


import com.alfarabi.alfalibs.R;
import com.alfarabi.alfalibs.helper.SwipeCallback;
import com.alfarabi.alfalibs.http.HttpInstance;
import com.alfarabi.alfalibs.tools.WindowFlow;
import com.alfarabi.alfalibs.views.interfaze.LoadingInterface;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import lombok.Getter;
import lombok.Setter;


public class AlfaSwipeRefreshLayout extends MaterialRefreshLayout implements LoadingInterface {

//    @Nullable private OnRefreshListener listener;
    @Getter@Setter boolean refreshable = true ;
    private SwipeCallback swipeCallback ;
    @Getter int page = 0;

    public int startPage(){
        page = 1 ;
        return page ;
    }
    public int restartPage(){
        return startPage() ;
    }
    public int currentPage(){
        return page ;
    }
    public int increasePage(){
        page++ ;
        return page ;
    }
    public int decreasePage(){
        page-- ;
        return page ;
    }
    public int zeroPage(){
        page = 0  ;
        return page ;
    }


    public AlfaSwipeRefreshLayout(Context context) {
        this(context, null);
        if(refreshable){
            setMaterialRefreshListener(new MaterialRefreshListener() {
                @Override
                public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                    if(getChildCount()>0){
                        if(getChildAt(0) instanceof AlfaRecyclerView){
                            ((AlfaRecyclerView) getChildAt(0)).checkIfEmpty();
                        }
                    }
                    postDelayed(() -> {
                        Toast.makeText(getContext(), R.string.swiperecyclerview_has_no_any_action, Toast.LENGTH_SHORT).show();
                        finishRefresh();
                    }, 500);
                }
            });
        }
    }

    public AlfaSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(refreshable){
            setMaterialRefreshListener(new MaterialRefreshListener() {
                @Override
                public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                    if(getChildCount()>0){
                        if(getChildAt(0) instanceof AlfaRecyclerView){
                            ((AlfaRecyclerView) getChildAt(0)).checkIfEmpty();
                        }
                    }
                    postDelayed(() -> {
                        Toast.makeText(getContext(), R.string.swiperecyclerview_has_no_any_action, Toast.LENGTH_SHORT).show();
                        finishRefresh();
                    }, 500);
                }
            });
        }
    }


    @Override
    public final <T> void setOnRefreshListener(final Observable<T> observable, final Consumer<? super T> onAny) {
        setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                if(getChildCount()>0){
                    if(getChildAt(0) instanceof AlfaRecyclerView){
                        ((AlfaRecyclerView) getChildAt(0)).checkIfEmpty();
                    }
                }
                Observable<T> observation = HttpInstance.with(observable);
                observation.subscribe(t -> {
                    finishRefresh();
                    onAny.accept(t);
                });
            }
        });
    }

    @Override
    public final <T> void setOnRefreshListener(final Observable<T> observable, final Consumer<? super T> onAny, final Consumer<? super Throwable> onError) {
        setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                if(getChildCount()>0){
                    if(getChildAt(0) instanceof AlfaRecyclerView){
                        ((AlfaRecyclerView) getChildAt(0)).checkIfEmpty();
                    }
                }
                Observable<T> observation = HttpInstance.with(observable);
                observation.subscribe(t -> {
                    finishRefresh();
                    onAny.accept(t);
                }, throwable -> {
                    finishRefresh();
                    onError.accept(throwable);
                });
            }
        });
    }

    @Override
    public final <T> void loadFirst(final Observable<T> observable, final Consumer<? super T> onAny) {
        setRefreshable(true);
        autoRefresh();
        if(getChildCount()>0){
            if(getChildAt(0) instanceof AlfaRecyclerView){
                ((AlfaRecyclerView) getChildAt(0)).refresh();
            }
        }
        Observable<T> observation = HttpInstance.with(observable);
        observation.subscribe(t -> {
            finishRefresh();
            onAny.accept(t);
        });
    }

    @Override
    public final <T> Disposable loadFirst(final Observable<T> observable, final Consumer<? super T> onAny, final Consumer<? super Throwable> onError) {
        setRefreshable(true);
        autoRefresh();
        if(getChildCount()>0){
            if(getChildAt(0) instanceof AlfaRecyclerView){
                ((AlfaRecyclerView) getChildAt(0)).refresh();
            }
        }
        Observable<T> observation = HttpInstance.with(observable);
        return observation.subscribe(t -> {
            finishRefresh();
            onAny.accept(t);
        }, throwable -> {
            finishRefresh();
            onError.accept(throwable);
        });
    }

    @Override
    public final <T> Disposable load(final Observable<T> observable, final Consumer<? super T> onAny, final Consumer<? super Throwable> onError) {
        setRefreshable(true);
        autoRefresh();
        if(getChildCount()>0){
            if(getChildAt(0) instanceof AlfaRecyclerView){
                ((AlfaRecyclerView) getChildAt(0)).refresh();
            }
        }
        Observable<T> observation = HttpInstance.with(observable);
        return observation.subscribe(t -> {
            finishRefresh();
            onAny.accept(t);
        }, throwable -> {
            finishRefresh();
            onError.accept(throwable);
        });
    }




    public void setOnSwipeListener(SwipeCallback swipeCallback){
//        setDirection(swipyRefreshLayoutDirection);
        this.swipeCallback = swipeCallback ;
        setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                startPage();
                swipeCallback.onSwipeFromTop();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                swipeCallback.onSwipeFromBottom();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setNestedScrollingEnabled(true);
        }

    }
}
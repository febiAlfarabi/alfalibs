package com.alfarabi.alfalibs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Toast;

import com.alfarabi.alfalibs.R;
import com.alfarabi.alfalibs.helper.SwipeCallback;
import com.alfarabi.alfalibs.http.HttpInstance;
import com.alfarabi.alfalibs.views.interfaze.LoadingInterface;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import lombok.Getter;
import lombok.Setter;


@Deprecated
public class AlfaSwipeRefreshLayoutBACKUP extends SwipyRefreshLayout
        implements SwipyRefreshLayout.OnRefreshListener, LoadingInterface {

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


    public AlfaSwipeRefreshLayoutBACKUP(Context context) {
        this(context, null);
        if(refreshable){
            setOnRefreshListener((swipyRefreshLayoutDirection) -> {
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

    public AlfaSwipeRefreshLayoutBACKUP(Context context, AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeResources(R.color.blue_A700);
        if(refreshable){
            setOnRefreshListener((swipyRefreshLayoutDirection) -> {
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
    public final <T> void setOnRefreshListener(final Observable<T> observable, final Consumer<? super T> onAny) {
        setOnRefreshListener((swipyRefreshLayoutDirection) -> {
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
        setOnRefreshListener((swipyRefreshLayoutDirection) -> {
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
    public void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
    }


    public void setOnSwipeListener(SwipeCallback swipeCallback, SwipyRefreshLayoutDirection swipyRefreshLayoutDirection){
        setDirection(swipyRefreshLayoutDirection);
        this.swipeCallback = swipeCallback ;
        setOnRefreshListener(this);
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if(this.swipeCallback!=null){
            if(direction==SwipyRefreshLayoutDirection.BOTTOM){
                swipeCallback.onSwipeFromBottom();
            }else if(direction==SwipyRefreshLayoutDirection.TOP){
                startPage();
                swipeCallback.onSwipeFromTop();
            }
        }
    }
}
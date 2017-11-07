package com.alfarabi.alfalibs.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.alfarabi.alfalibs.R;
import com.alfarabi.alfalibs.helper.PaginationCallback;
import com.alfarabi.alfalibs.views.interfaze.EmptyLayoutListener;
import com.alfarabi.alfalibs.views.interfaze.LoadingInterface;
import com.paginate.Paginate;
import com.paginate.recycler.LoadingListItemSpanLookup;
import com.paginate.recycler.RecyclerPaginate;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import lombok.Getter;
import lombok.Setter;


public final class AlfaRecyclerView<E> extends android.support.v7.widget.RecyclerView implements LoadingInterface {

    private String TAG = AlfaRecyclerView.class.getName();

    @Getter List<E> objects ;
    private EmptyLayoutListener emptyLayoutListener ;
    @Getter@Setter View emptyView ;
    @Getter@Setter View loadingView ;
    private Context context ;
    private AttributeSet attrs ;


    public AlfaRecyclerView(Context context) {
        super(context);
    }

    public AlfaRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        try {
            this.context = context ;
            this.attrs = attrs ;
            setEmptyView(context, attrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AlfaRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        try {
            this.context = context ;
            this.attrs = attrs ;
            setEmptyView(context, attrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setEmptyView(Context context, @Nullable AttributeSet attrs) throws Exception{
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AlfaRecyclerView);
        if(a.getResourceId(R.styleable.AlfaRecyclerView_empty_view, 0)!=0){
            emptyView = LayoutInflater.from(context).inflate(a.getResourceId(R.styleable.AlfaRecyclerView_empty_view, 0), null);
            setEmptyView(emptyView);
        }
        if(a.getResourceId(R.styleable.AlfaRecyclerView_loading_view, 0)!=0){
            loadingView = ((Activity)context).findViewById(a.getResourceId(R.styleable.AlfaRecyclerView_loading_view, 0));
            setLoadingView(loadingView);
        }

    }

    public void setEmptyView(View view){
        this.emptyView = view ;
    }

    final private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            checkIfEmpty();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            checkIfEmpty();
        }
    };

    public void checkIfEmpty() {
        if(getParent()!=null && emptyView!=null && emptyView.getParent()==null){
            if(getParent() instanceof RelativeLayout){
                ((RelativeLayout)getParent()).addView(emptyView);
            }else if(getParent() instanceof FrameLayout){
                ((FrameLayout)getParent()).addView(emptyView);
            }else {
                View view = (View) getParent();
                if(view.getParent() instanceof RelativeLayout){
                    ((RelativeLayout) view.getParent()).addView(emptyView);
                }else if(view.getParent() instanceof FrameLayout){
                    ((FrameLayout) view.getParent()).addView(emptyView);
                }
            }

        }
        if (emptyView != null && getAdapter() != null && loadingView!=null) {
            final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
            if(loadingView.getVisibility()==VISIBLE){
                emptyView.setVisibility(emptyViewVisible ? GONE : VISIBLE);
                setVisibility(emptyViewVisible ? GONE : VISIBLE);
            }else{
                emptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
                setVisibility(emptyViewVisible ? GONE : VISIBLE);
            }
        }else if (emptyView != null && getAdapter() != null) {
            final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
            emptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }

    }


    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
        checkIfEmpty();
    }

    public void setObjects(List<E> objects) {
        this.objects = objects;
        if(getAdapter()!=null){
            getAdapter().notifyDataSetChanged();
        }
    }

    public void setEmptyLayoutListener(EmptyLayoutListener emptyLayoutListener) {
        this.emptyLayoutListener = emptyLayoutListener;
    }

    public void refresh(){
        if (emptyView != null) {
            emptyView.setVisibility(GONE);
        }
    }

    @Override
    public <T> void setOnRefreshListener(Observable<T> observable, Consumer<? super T> onAny) {
        if(getParent() instanceof AlfaSwipeRefreshLayout){
            ((AlfaSwipeRefreshLayout) getParent()).setOnRefreshListener(observable, onAny);
        }
    }

    @Override
    public <T> void setOnRefreshListener(Observable<T> observable, Consumer<? super T> onAny, Consumer<? super Throwable> onError) {
        if(getParent() instanceof AlfaSwipeRefreshLayout){
            ((AlfaSwipeRefreshLayout) getParent()).setOnRefreshListener(observable, onAny, onError);
        }
    }

    @Override
    public <T> void loadFirst(Observable<T> observable, Consumer<? super T> onAny) {
        if(getParent() instanceof AlfaSwipeRefreshLayout){
            ((AlfaSwipeRefreshLayout) getParent()).loadFirst(observable, onAny);
        }
    }

    @Override
    public <T> Disposable loadFirst(Observable<T> observable, Consumer<? super T> onAny, Consumer<? super Throwable> onError) {
        if(getParent() instanceof AlfaSwipeRefreshLayout){
            return ((AlfaSwipeRefreshLayout) getParent()).loadFirst(observable, onAny, onError);
        }
        return null;
    }

    @Override
    public <T> Disposable load(Observable<T> observable, Consumer<? super T> onAny, Consumer<? super Throwable> onError) {
        if(getParent() instanceof AlfaSwipeRefreshLayout){
            return ((AlfaSwipeRefreshLayout) getParent()).load(observable, onAny, onError);
        }
        return null;
    }

}



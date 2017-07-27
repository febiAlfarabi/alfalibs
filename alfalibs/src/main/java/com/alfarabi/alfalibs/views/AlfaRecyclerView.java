package com.alfarabi.alfalibs.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.alfarabi.alfalibs.R;
import com.alfarabi.alfalibs.adapters.recyclerview.viewholder.LoadingViewHolder;
import com.alfarabi.alfalibs.helper.FindFirstItemInLayoutManagerInterface;
import com.alfarabi.alfalibs.helper.FindLastItemInLayoutManagerInterface;
import com.alfarabi.alfalibs.helper.PaginationCompletionInterface;
import com.alfarabi.alfalibs.helper.PaginationHandler;
import com.alfarabi.alfalibs.helper.PaginationInterface;
import com.alfarabi.alfalibs.helper.PaginationScrollListener;
import com.alfarabi.alfalibs.views.interfaze.EmptyLayoutListener;
import com.alfarabi.alfalibs.views.interfaze.LoadingInterface;
import com.paginate.Paginate;
import com.paginate.recycler.LoadingListItemCreator;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import lombok.Getter;
import lombok.Setter;


public final class AlfaRecyclerView<E> extends android.support.v7.widget.RecyclerView implements LoadingInterface {

    @Getter List<E> objects ;
    private EmptyLayoutListener emptyLayoutListener ;

    private String TAG = AlfaRecyclerView.class.getName();
    private View emptyView ;
    private Context context ;
    private AttributeSet attrs ;
//    @Getter@Setter Paginate paginate ;
    @Getter@Setter PaginationHandler paginationHandler ;


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
        if (emptyView != null && getAdapter() != null) {
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

    /**
     *
     *
     *
     * @param paginationScrollListener
     * /************

    This is minimal usage of this component, if you want more usage please see "Home.class"

    1- you must call this method after set adapter and layoutManager into recyclerView
    2- make sure you call build() method on Builder object



    new PaginationHandler.Builder()
    .setRecyclerView(recyclerView)  // set recyclerView that you want to handle pagination
    .setOffsetCount(5)    // set count pre last to load more happened
    .setLoadMoreListener(new PaginationInterface() {      // handle loadMore,
     @Override
     public void onLoadMore(final PaginationCompletionInterface pageComplete) {
     //      @Caution => remember to call pageComplete after adding items into list, or get failed response.

     // send your request in any way you want from DB or network
     new Thread(new Runnable() {
     @Override
     public void run() {
     try {
     Thread.sleep(5000);
     } catch (InterruptedException e) {
     }


     Handler handler = new Handler(Looper.getMainLooper());
     handler.post(new Runnable() {
     @Override
     public void run() {

     for (int i = 0; i < 40; i++) {
     adapterList.add(new Data("test" + (40 * page + i)));
     }

     adapter.setItems(adapterList);
     pageComplete.handledDataComplete(++page >= 5);
     }
     });

     }
     }).start();
     }
     }).build();



     */

    public void withPagination(PaginationInterface paginationInterface, int offset, int columnCount){
        postDelayed(() -> {
            paginationHandler = new PaginationHandler.Builder().setRecyclerView(this).setOffsetCount(offset).setLoadMoreListener(paginationInterface)
            // default value is 1
            .setColumncount(columnCount)

            // this method will return lastVisibleItem method in your layoutManager.
            .setFindLastItemInLayoutManagerInterface(new FindLastItemInLayoutManagerInterface() {
                @Override
                public int findLastVisibleItemPosition() {
                    if(getLayoutManager() instanceof LinearLayoutManager){
                        return ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                    }
                    if(getLayoutManager() instanceof GridLayoutManager){
                        return ((GridLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                    }
                    return 0;
                }
            })
            // if your direction is LOAD_FROM_TOP you must set it instead of above method in custom layoutManager.
            .setFindFirstItemInLayoutManagerInterface(new FindFirstItemInLayoutManagerInterface() {
                @Override
                public int findFirstVisibleItemPosition() {
                    if(getLayoutManager() instanceof LinearLayoutManager){
                        return ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
                    }
                    if(getLayoutManager() instanceof GridLayoutManager){
                        return ((GridLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
                    }
                    return 0;
                }
            }).build();
        }, 3000);
    }

//    public void withPagination(PaginatonCallBack callBack){
//        postDelayed(() -> {
//            paginate = Paginate.with(this, new Paginate.Callbacks() {
//                @Override
//                public void onLoadMore() {
//                    try {
//                        if(paginate!=null){
//                            callBack.onLoadMore();
//                        }
//                    }catch (Exception e){
//
//                    }
//                }
//
//                @Override
//                public boolean isLoading() {
//                    return false;
//                }
//
//                @Override
//                public boolean hasLoadedAllItems() {
//                    if(paginate!=null) {
//                        paginate.setHasMoreDataToLoad(true);
//                    }
//                    return true;
//                }
//            }).setLoadingTriggerThreshold(2)
//                    .addLoadingListItem(true).setLoadingListItemCreator(new LoadingListItemCreator() {
//                        @Override
//                        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//                            View view = inflater.inflate(R.layout.loading_list_creator, parent, false);
//                            return new LoadingViewHolder(view);
//                        }
//
//                        @Override
//                        public void onBindViewHolder(ViewHolder holder, int position) {
//
//                        }
//                    }).build();
//        }, 3000);
//    }


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

    public interface PaginatonCallBack {
        void onLoadMore() throws Exception;

    }
}
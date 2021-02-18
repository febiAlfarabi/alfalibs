package com.alfarabi.alfalibs.helper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfarabi.alfalibs.adapters.recyclerview.viewholder.LoadingViewHolder;
import com.alfarabi.alfalibs.views.AlfaRecyclerView;
import com.paginate.recycler.LoadingListItemCreator;
import com.alfarabi.alfalibs.R;
import lombok.Getter;
import lombok.Setter;

public abstract class AlfaPagination implements PaginationInterface {

    private PaginationCompletionInterface completion ;
    @Getter @Setter View view ;

    private WrapperSpanSizeLookup wrapperSpanSizeLookup ;
    public WrapperAdapter wrapperAdapter ;
    @Getter@Setter AlfaRecyclerView alfaRecyclerView ;
    @Getter@Setter int trigger ;

    public AlfaPagination(int trigger){
        this.trigger = trigger ;
    }

    public AlfaPagination(AlfaRecyclerView recyclerView, int trigger){
        this.alfaRecyclerView = recyclerView ;
        this.trigger = trigger ;
    }

    public void stopLoading(){
        if(completion!=null){
            completion.handledDataComplete(false);
            wrapperAdapter.displayLoadingRow(false);
        }
    }

    @Override
    public void onLoadMore(PaginationCompletionInterface completion) {
        this.completion = completion ;
        onLoading(completion);

        AlfaRecyclerView.Adapter adapter = alfaRecyclerView.getAdapter();

        wrapperAdapter = new WrapperAdapter(adapter, new LoadingListItemCreator() {
            @Override
            public AlfaRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.loading_list_creator, parent, false);
                return new LoadingViewHolder(view);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

        });

        adapter.registerAdapterDataObserver(mDataObserver);
        alfaRecyclerView.setAdapter(wrapperAdapter);
        // For GridLayoutManager use separate/customisable span lookup for loading row
        if (alfaRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
            wrapperSpanSizeLookup = new WrapperSpanSizeLookup(((GridLayoutManager) alfaRecyclerView.getLayoutManager()).getSpanSizeLookup(), () -> this.trigger, wrapperAdapter);
            ((GridLayoutManager) alfaRecyclerView.getLayoutManager()).setSpanSizeLookup(wrapperSpanSizeLookup);
        }
    }
    public abstract void onLoading(PaginationCompletionInterface completion);

    private final RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            wrapperAdapter.notifyDataSetChanged();
            onAdapterDataChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            wrapperAdapter.notifyItemRangeInserted(positionStart, itemCount);
            onAdapterDataChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            wrapperAdapter.notifyItemRangeChanged(positionStart, itemCount);
            onAdapterDataChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            wrapperAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
            onAdapterDataChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            wrapperAdapter.notifyItemRangeRemoved(positionStart, itemCount);
            onAdapterDataChanged();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            wrapperAdapter.notifyItemMoved(fromPosition, toPosition);
            onAdapterDataChanged();
        }
    };
    public void onAdapterDataChanged() {
        wrapperAdapter.displayLoadingRow(true);
        checkEndOffset();
    }
    void checkEndOffset() {
        int visibleItemCount = alfaRecyclerView.getChildCount();
        int totalItemCount = alfaRecyclerView.getLayoutManager().getItemCount();

        int firstVisibleItemPosition;
        if (alfaRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            firstVisibleItemPosition = ((LinearLayoutManager) alfaRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        } else if (alfaRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            // https://code.google.com/p/android/issues/detail?id=181461
            if (alfaRecyclerView.getLayoutManager().getChildCount() > 0) {
                firstVisibleItemPosition = ((StaggeredGridLayoutManager) alfaRecyclerView.getLayoutManager()).findFirstVisibleItemPositions(null)[0];
            } else {
                firstVisibleItemPosition = 0;
            }
        } else {
            throw new IllegalStateException("LayoutManager needs to subclass LinearLayoutManager or StaggeredGridLayoutManager");
        }

        // Check if end of the list is reached (counting threshold) or if there is no items at all
//        if ((totalItemCount - visibleItemCount) <= (firstVisibleItemPosition + loadingTriggerThreshold) || totalItemCount == 0) {
        if ((totalItemCount - visibleItemCount) <= (firstVisibleItemPosition + this.trigger) || totalItemCount == 0) {
            // Call load more only if loading is not currently in progress and if there is more items to load
            if (!wrapperAdapter.displayLoadingRow) {
                wrapperAdapter.displayLoadingRow(true);
            }
        }
    }

}
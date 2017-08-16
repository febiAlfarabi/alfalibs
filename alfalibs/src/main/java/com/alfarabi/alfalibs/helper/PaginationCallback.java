package com.alfarabi.alfalibs.helper;

import com.alfarabi.alfalibs.tools.WLog;
import com.alfarabi.alfalibs.views.AlfaRecyclerView;
import com.paginate.Paginate;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Alfarabi on 8/7/17.
 */

public abstract class PaginationCallback implements Paginate.Callbacks {

    public static final String TAG = PaginationCallback.class.getName();

    @Getter@Setter boolean loading ;
    @Getter@Setter boolean loadedAllItems = false ;
    @Getter@Setter Paginate paginate ;
    @Getter@Setter AlfaRecyclerView alfaRecyclerView ;

    @Getter@Setter int page = 1;
    public int restartPage(){
        page = 1;
        return page ;
    }
    public int increasePage(){
        page++;
        return page ;
    }
    public int decreasePage(){
        page--;
        return page ;
    }
    public PaginationCallback(){
    }
//    @Override
//    public void onLoadMore() {}

    @Override
    public boolean isLoading() {
        return loading;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return loadedAllItems ;
    }

    public void paginationNext(){
        if(!isLoadedAllItems()){
            setLoading(true);
            setLoadedAllItems(false);
            paginate.setHasMoreDataToLoad(true);
            WLog.i(TAG, ":::: PAGINATION NEXT ");
        }
    }

    public void paginationStop(){
        alfaRecyclerView.postDelayed(() -> {
            decreasePage();
            setLoading(false);
            setLoadedAllItems(true);
            paginate.setHasMoreDataToLoad(false);
            WLog.i(TAG, ":::: PAGINATION STOP ");
        }, 2000);
    }

    public void paginationError(){
        decreasePage();
        setLoading(false);
        setLoadedAllItems(true);
        paginate.setHasMoreDataToLoad(false);
        WLog.i(TAG, ":::: PAGINATION ERROR");
        alfaRecyclerView.postDelayed(() -> {
            paginationReenable();
        }, 2000);
    }

    public void paginationReenable(){
        setLoading(false);
        setLoadedAllItems(false);
        WLog.i(TAG, ":::: PAGINATION REENABLE");
//        paginate.setHasMoreDataToLoad(true);
    }

    public void paginationReset(){
        setLoading(false);
        setLoadedAllItems(false);
        paginate.setHasMoreDataToLoad(true);
        WLog.i(TAG, ":::: PAGINATION RESET");
    }

//    public int fistPage(){
//        paginationReset();
//        return this.page ;
//    }

}

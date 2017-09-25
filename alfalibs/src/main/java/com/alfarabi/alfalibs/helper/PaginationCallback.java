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

    @Getter@Setter boolean loading = true ;
//    @Getter@Setter boolean loadedAllItems = false ;
    @Getter Paginate paginate ;
    @Getter@Setter AlfaRecyclerView alfaRecyclerView ;
    @Getter@Setter int page = 0;

    public void setPaginate(Paginate paginate) {
        this.paginate = paginate;
//        paginate.setHasMoreDataToLoad(loading);
        paginationNext();
    }

    public int zeroPage(){
        page = 0;
        return page ;
    }
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
        return !loading ;
    }

    public void paginationNext(){
        setLoading(true);
        paginate.setHasMoreDataToLoad(loading);
        onLoadMore();
        WLog.d(TAG, "--> PAGINATION NEXT ");
    }

    public void paginationStop(){
        decreasePage();
        setLoading(false);
//            setLoadedAllItems(true);
        paginate.setHasMoreDataToLoad(loading);
        WLog.d(TAG, "--> PAGINATION STOP ");
        alfaRecyclerView.postDelayed(() -> {
            paginationReenable();
        }, 2000);
    }

    public void paginationError(){
        decreasePage();
        setLoading(false);
//        setLoadedAllItems(true);
        paginate.setHasMoreDataToLoad(loading);
        WLog.d(TAG, "--> PAGINATION ERROR");
        alfaRecyclerView.postDelayed(() -> {
            paginationReenable();
        }, 2000);
    }

    public void paginationReenable(){
        setLoading(true);
//        setLoadedAllItems(false);
        paginate.setHasMoreDataToLoad(loading);
        WLog.d(TAG, "--> PAGINATION REENABLE");

    }

    public void paginationReset(){
        setLoading(true);
//        setLoadedAllItems(false);
        paginate.setHasMoreDataToLoad(loading);
        WLog.d(TAG, "--> PAGINATION RESET");
    }

//    public int fistPage(){
//        paginationReset();
//        return this.page ;
//    }

}

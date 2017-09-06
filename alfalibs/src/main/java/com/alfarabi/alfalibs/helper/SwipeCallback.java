package com.alfarabi.alfalibs.helper;

import com.alfarabi.alfalibs.tools.WLog;
import com.alfarabi.alfalibs.views.AlfaRecyclerView;
import com.paginate.Paginate;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Alfarabi on 8/7/17.
 */

public abstract class SwipeCallback{

    public static final String TAG = SwipeCallback.class.getName();

    public abstract void onSwipeFromTop();
    public abstract void onSwipeFromBottom();

}

package com.alfarabi.alfalibs.adapters.recyclerview.viewholder;

import android.support.v4.app.Fragment;
import android.view.ViewGroup;

import com.alfarabi.alfalibs.fragments.interfaze.RecyclerCallback;

/**
 * Created by Alfarabi on 6/22/17.
 */

public abstract class SimpleStickyBodyViewHolder<F extends Fragment & RecyclerCallback, O, FP> extends SimpleViewHolder<F, O, FP> {


    public SimpleStickyBodyViewHolder(F fragment, int resId, ViewGroup viewGroup) {
        super(fragment, resId, viewGroup);
        // test Lagi
    }
}

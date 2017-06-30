package com.alfarabi.alfalibs.adapters.recyclerview.viewholder;

import android.support.v4.app.Fragment;
import android.view.ViewGroup;

import com.alfarabi.alfalibs.fragments.interfaze.SimpleFragmentCallback;

/**
 * Created by Alfarabi on 6/22/17.
 */

public abstract class SimpleStickyBodyViewHolder<F extends Fragment & SimpleFragmentCallback, O> extends SimpleViewHolder<F, O> {


    public SimpleStickyBodyViewHolder(F fragment, int resId, ViewGroup viewGroup) {
        super(fragment, resId, viewGroup);
        // test Lagi
    }
}

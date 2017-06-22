package com.alfarabi.alfalibs.adapters.recyclerview.viewholder;

import android.support.v4.app.Fragment;
import android.view.ViewGroup;

import com.alfarabi.alfalibs.fragments.interfaze.SimpleFragmentCallback;

/**
 * Created by Alfarabi on 6/22/17.
 */

public abstract class SimpleStickyHeaderViewHolder<F extends Fragment & SimpleFragmentCallback>  extends SimpleViewHolder<F> {


    public SimpleStickyHeaderViewHolder(F fragment, int resId, ViewGroup viewGroup) {
        super(fragment, resId, viewGroup);
    }
}

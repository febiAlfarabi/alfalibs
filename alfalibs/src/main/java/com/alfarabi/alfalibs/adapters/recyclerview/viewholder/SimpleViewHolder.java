package com.alfarabi.alfalibs.adapters.recyclerview.viewholder;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfarabi.alfalibs.adapters.interfaze.SimpleHolderCallback;
import com.alfarabi.alfalibs.fragments.interfaze.SimpleFragmentCallback;

import butterknife.ButterKnife;

/**
 * Created by Alfarabi on 6/15/17.
 */

public abstract class SimpleViewHolder<F extends Fragment & SimpleFragmentCallback> extends RecyclerView.ViewHolder implements SimpleHolderCallback {


    protected Fragment fragment ;


    public SimpleViewHolder(F fragment, int resId, ViewGroup viewGroup){
        super(LayoutInflater.from(fragment.getActivity()).inflate(resId, viewGroup, false));
        this.fragment = fragment ;
        ButterKnife.bind(this, itemView);
    }

    private SimpleViewHolder(View itemView) {
        super(itemView);
    }
}

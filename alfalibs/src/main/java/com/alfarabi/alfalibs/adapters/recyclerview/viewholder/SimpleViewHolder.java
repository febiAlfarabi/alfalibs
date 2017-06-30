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

public abstract class SimpleViewHolder<F extends Fragment & SimpleFragmentCallback, O> extends RecyclerView.ViewHolder implements SimpleHolderCallback<O> {


    protected Fragment fragment ;


    public SimpleViewHolder(F fragment, int id, ViewGroup viewGroup){
        super(LayoutInflater.from(fragment.getActivity()).inflate(id, viewGroup, false));
        this.fragment = fragment ;
        ButterKnife.bind(this, itemView);
    }


    private SimpleViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void showData(O object) {

    }

}

package com.alfarabi.alfalibs.adapters.recyclerview.viewholder;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfarabi.alfalibs.adapters.interfaze.SimpleHolderCallback;
import com.alfarabi.alfalibs.fragments.SimpleBaseFragment;
import com.alfarabi.alfalibs.fragments.interfaze.SimpleFragmentCallback;

import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Alfarabi on 6/15/17.
 */

public abstract class SimpleViewHolder<F extends Fragment & SimpleFragmentCallback, O, FP> extends RecyclerView.ViewHolder implements SimpleHolderCallback<O, FP> {


    @Getter@Setter Fragment fragment ;
    @Getter@Setter O object ;


    public SimpleViewHolder(F fragment, int id, ViewGroup viewGroup){
        super(LayoutInflater.from(fragment.getActivity()).inflate(id, viewGroup, false));
        this.fragment = fragment ;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void showData(O object) {
        this.object = object ;
    }

    public String getString(int id){
        return getFragment().getString(id);
    }
    public int getInteger(int id){
        return getFragment().getActivity().getResources().getInteger(id);
    }

    public <T extends SimpleBaseFragment> T getFragment(Class<T> tClass){
        return (T) getFragment();
    }


}

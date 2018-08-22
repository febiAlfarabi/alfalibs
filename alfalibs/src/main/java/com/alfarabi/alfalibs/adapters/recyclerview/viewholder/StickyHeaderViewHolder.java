package com.alfarabi.alfalibs.adapters.recyclerview.viewholder;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfarabi.alfalibs.adapters.interfaze.SimpleHolderCallback;
import com.alfarabi.alfalibs.adapters.interfaze.StickyHolderInterface;
import com.alfarabi.alfalibs.fragments.SimpleBaseFragment;
import com.alfarabi.alfalibs.fragments.interfaze.RecyclerCallback;
import com.onecode.stickyheadergrid.viewholder.BaseViewHolder;

import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Alfarabi on 6/15/17.
 */

public abstract class StickyHeaderViewHolder<F extends Fragment & RecyclerCallback, O extends StickyHolderInterface, FP> extends BaseViewHolder implements SimpleHolderCallback<O, FP> {

    @Getter@Setter F fragment ;
    @Getter@Setter O object ;


    public StickyHeaderViewHolder(F fragment, View view){
        super(view);
        this.fragment = fragment ;
        ButterKnife.bind(this, view);
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

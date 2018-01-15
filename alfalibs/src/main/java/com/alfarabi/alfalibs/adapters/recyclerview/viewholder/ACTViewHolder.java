package com.alfarabi.alfalibs.adapters.recyclerview.viewholder;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alfarabi.alfalibs.activity.SimpleBaseActivity;
import com.alfarabi.alfalibs.adapters.interfaze.SimpleHolderCallback;
import com.alfarabi.alfalibs.fragments.SimpleBaseFragment;
import com.alfarabi.alfalibs.fragments.interfaze.RecyclerCallback;

import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Alfarabi on 6/15/17.
 */

public abstract class ACTViewHolder<ACT extends SimpleBaseActivity & RecyclerCallback, O, FP> extends RecyclerView.ViewHolder implements SimpleHolderCallback<O, FP> {


    @Getter@Setter ACT act ;
    @Getter@Setter O object ;


    public ACTViewHolder(ACT act, int id, ViewGroup viewGroup){
        super(LayoutInflater.from(act).inflate(id, viewGroup, false));
        this.act = act ;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void showData(O object) {
        this.object = object ;
    }

    public String getString(int id){
        return act.getString(id);
    }
    public int getInteger(int id){
        return act.getResources().getInteger(id);
    }

    public <ACT extends SimpleBaseActivity> ACT activity(Class<ACT> tClass){
        return (ACT) act;
    }


}

package com.alfarabi.alfalibs.adapters.recyclerview;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.alfarabi.alfalibs.adapters.recyclerview.viewholder.SimpleViewHolder;
import com.alfarabi.alfalibs.tools.UISimulation;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import lombok.Getter;


/**
 * Created by Alfarabi on 6/19/17.
 */

public class HorizontalRecyclerAdapter<F extends Fragment, VH extends SimpleViewHolder, OBJ extends Object> extends RecyclerView.Adapter<VH> {

    private F fragment;
    @Getter RecyclerView recyclerView ;
    private Class<VH> vhClass ;
    private List<OBJ> objects ;

    public HorizontalRecyclerAdapter(F fragment, Class<VH> vhClass, List<OBJ> objects) {
        this.fragment = fragment;
        this.vhClass = vhClass;
        this.objects = objects;
    }

    public HorizontalRecyclerAdapter initRecyclerView(RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(fragment.getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(this);
        notifyDataSetChanged();
        return this ;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            return vhClass.getConstructor(fragment.getClass(), ViewGroup.class).newInstance(fragment, parent);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null ;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return UISimulation.size(objects);
    }
}

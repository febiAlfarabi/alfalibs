package com.alfarabi.alfalibs.adapters.recyclerview;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alfarabi.alfalibs.adapters.recyclerview.viewholder.SimpleViewHolder;
import com.alfarabi.alfalibs.fragments.interfaze.SimpleFragmentCallback;
import com.alfarabi.alfalibs.tools.Demo;
import com.alfarabi.alfalibs.tools.UISimulation;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Alfarabi on 6/15/17.
 */

public class SimpleRecyclerAdapter<OBJ extends Object, F extends Fragment & SimpleFragmentCallback, VH extends SimpleViewHolder> extends RecyclerView.Adapter<VH>{

    public Class<VH> vhClass ;
    public F fragment ;
    public List<OBJ> objects ;

    public SimpleRecyclerAdapter(F fragment, Class<VH> vhClass, List<OBJ> objects) {
        this.vhClass = vhClass;
        this.fragment = fragment;
        this.objects = objects;
    }

    public SimpleRecyclerAdapter initRecyclerView(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager){
        recyclerView.setLayoutManager(layoutManager);
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

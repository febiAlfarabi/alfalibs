package com.alfarabi.alfalibs.adapters.recyclerview;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.alfarabi.alfalibs.adapters.recyclerview.viewholder.SimpleViewHolder;
import com.alfarabi.alfalibs.fragments.interfaze.RecyclerCallback;
import com.alfarabi.alfalibs.tools.UISimulation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Alfarabi on 6/15/17.
 */

public class BasicFragRecyclerAdapter<OBJ extends Object, F extends Fragment & RecyclerCallback, VH extends SimpleViewHolder> extends RecyclerView.Adapter<VH>{

    @Getter@Setter Class<VH> vhClass ;
    @Getter@Setter F fragment ;
    @Getter List<OBJ> objects ;
    @Getter@Setter List<OBJ> copiedObjects = new ArrayList<>();
    @Getter@Setter HashMap<OBJ, VH> viewHolders = new HashMap<>();



    public BasicFragRecyclerAdapter(F fragment, Class<VH> vhClass, List<OBJ> objects) {
        this.vhClass = vhClass;
        this.fragment = fragment;
        this.objects = objects;
        copiedObjects.clear();
        copiedObjects.addAll(objects);
    }

    public BasicFragRecyclerAdapter initRecyclerView(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager){
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
        if(objects!=null && objects.size()>0){
            holder.showData(objects.get(position));
            viewHolders.put(objects.get(position), holder);
        }
    }

    public void setObjects(List<OBJ> objects) {
        this.objects = objects;
        this.copiedObjects.clear();
        this.copiedObjects.addAll(this.objects);
        notifyDataSetChanged();
    }

    public void appendObjects(List<OBJ> objects) {
        if(this.objects==null){
            this.objects = new ArrayList<OBJ>();
        }
        this.objects.addAll(objects);
        this.copiedObjects.clear();
        this.copiedObjects.addAll(this.objects);
        notifyItemRangeChanged(0, this.objects.size());
    }

    @Override
    public int getItemCount() {
        return UISimulation.size(objects);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

}

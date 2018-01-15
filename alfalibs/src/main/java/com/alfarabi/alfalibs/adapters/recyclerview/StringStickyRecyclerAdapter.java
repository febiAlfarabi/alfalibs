package com.alfarabi.alfalibs.adapters.recyclerview;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.alfarabi.alfalibs.adapters.recyclerview.viewholder.SimpleStickyBodyViewHolder;
import com.alfarabi.alfalibs.adapters.recyclerview.viewholder.SimpleStickyHeaderViewHolder;
import com.alfarabi.alfalibs.fragments.interfaze.RecyclerCallback;
import com.alfarabi.alfalibs.tools.UISimulation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Alfarabi on 6/22/17.
 */

public class StringStickyRecyclerAdapter<F extends Fragment & RecyclerCallback, OBJ extends String
        , HVH extends SimpleStickyHeaderViewHolder, BVH extends SimpleStickyBodyViewHolder> extends RecyclerView.Adapter<BVH> implements StickyHeaderAdapter<HVH> {

    @Getter@Setter Class<HVH> headerVh ;
    @Getter@Setter Class<BVH> bodyVh ;
    @Getter@Setter F fragment ;
    @Getter List<OBJ> objects ;
    @Getter@Setter List<OBJ> copiedObjects = new ArrayList<>();
    @Getter@Setter HashMap<OBJ, HVH> headerViewHolders = new HashMap<>();
    @Getter@Setter HashMap<OBJ, BVH> bodyViewHolders = new HashMap<>();

    public StringStickyRecyclerAdapter(F fragment, Class<HVH> headerVh, Class<BVH> bodyVh, List<OBJ> objects) {
        this.bodyVh = bodyVh;
        this.headerVh = headerVh;
        this.fragment = fragment;
        this.objects = objects;
        copiedObjects.clear();
        copiedObjects.addAll(objects);

    }

    @Override
    public long getHeaderId(int position) {
        return position;
    }

    @Override
    public HVH onCreateHeaderViewHolder(ViewGroup parent) {
        try {
            return headerVh.getConstructor(fragment.getClass(), ViewGroup.class).newInstance(fragment, parent);
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


    public StringStickyRecyclerAdapter initRecyclerView(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager){
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(this);
        notifyDataSetChanged();
        return this ;
    }


    @Override
    public void onBindHeaderViewHolder(HVH holder, int position) {
        if(objects!=null && objects.size()>0){
            holder.showData(objects.get(position));
            headerViewHolders.put(objects.get(position), holder);
        }
    }

    @Override
    public BVH onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            return bodyVh.getConstructor(fragment.getClass(), ViewGroup.class).newInstance(fragment, parent);
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
    public void onBindViewHolder(BVH holder, int position) {
        if(objects!=null && objects.size()>0){
            holder.showData(objects.get(position));
            bodyViewHolders.put(objects.get(position), holder);
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
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return UISimulation.size(objects);
    }
}

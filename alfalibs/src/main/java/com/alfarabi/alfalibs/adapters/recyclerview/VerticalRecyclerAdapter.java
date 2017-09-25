package com.alfarabi.alfalibs.adapters.recyclerview;

import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.alfarabi.alfalibs.adapters.recyclerview.viewholder.SimpleViewHolder;
import com.alfarabi.alfalibs.fragments.interfaze.SimpleFragmentCallback;
import com.alfarabi.alfalibs.helper.model.ObjectAdapterInterface;
import com.alfarabi.alfalibs.tools.Demo;
import com.alfarabi.alfalibs.tools.UISimulation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


/**
 * Created by Alfarabi on 6/19/17.
 */

public class VerticalRecyclerAdapter<F extends Fragment, VH extends SimpleViewHolder, OBJ extends Object & ObjectAdapterInterface> extends RecyclerView.Adapter<VH> {

    @Getter@Setter F fragment;
    @Getter@Setter RecyclerView recyclerView ;
    @Getter@Setter Class<VH> vhClass ;
    @Getter List<OBJ> objects ;
    @Getter@Setter List<OBJ> copiedObjects = new ArrayList<>();
    @Getter@Setter HashMap<Integer, VH> viewHolders = new HashMap<>();

    public VerticalRecyclerAdapter(F fragment, Class<VH> vhClass, List<OBJ> objects) {
        this.fragment = fragment;
        this.vhClass = vhClass;
        this.objects = objects;
        copiedObjects.clear();
        copiedObjects.addAll(objects);
    }

    public VerticalRecyclerAdapter initRecyclerView(RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(fragment.getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(this);
        notifyDataSetChanged();
        return this ;
    }

    public VerticalRecyclerAdapter initRecyclerView(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager){
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(this);
        notifyDataSetChanged();
        return this ;
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
            viewHolders.put(position, holder);
        }
    }

    @Override
    public int getItemCount() {
        return UISimulation.size(objects);
    }

    public void filter(String text){
        if(objects==null || copiedObjects==null){
            return;
        }
        objects.clear();
        if(text.isEmpty()){
            objects.addAll(copiedObjects);
        } else{
            text = text.toLowerCase();
            int cursor = 0;
            for(OBJ item: copiedObjects){
                if(!item.isSearchable()){
                    return;
                }
                try {
                    Class fieldClass = item.getClass();
                    Field field = fieldClass.getDeclaredField(item.canSearchByField());
                    field.setAccessible(true);
                    String value = (String) field.get(item);
                    if(value.toLowerCase().contains(text)){
                        objects.add(item);
                    }
                    if(viewHolders.get(cursor)!=null){
                        viewHolders.get(cursor).find(text);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                cursor++;
            }
        }
        notifyDataSetChanged();
    }
}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alfarabi.alfalibs.adapters.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.ViewGroup;

import com.alfarabi.alfalibs.activity.SimpleBaseActivity;
import com.alfarabi.alfalibs.adapters.recyclerview.viewholder.ACTViewHolder;
import com.alfarabi.alfalibs.helper.model.ObjectAdapterInterface;
import com.alfarabi.alfalibs.tools.UISimulation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ActRecyclerAdapter<OBJ extends Object & ObjectAdapterInterface, ACT extends SimpleBaseActivity, VH extends ACTViewHolder> extends Adapter<VH> {
    @Getter @Setter Class<VH> vhClass;
    @Getter @Setter ACT activity ;
    @Getter List<OBJ> objects;
    @Getter @Setter List<OBJ> copiedObjects = new ArrayList();
    @Getter @Setter HashMap<OBJ, VH> viewHolders = new HashMap();

    public ActRecyclerAdapter(ACT activity, Class<VH> vhClass, List<OBJ> objects) {
        this.vhClass = vhClass;
        this.activity = activity;
        this.objects = objects;
        this.copiedObjects.clear();
        this.copiedObjects.addAll(objects);
    }

    public ActRecyclerAdapter initRecyclerView(RecyclerView recyclerView, LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(this);
        this.notifyDataSetChanged();
        return this;
    }

    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            return (VH) this.vhClass.getConstructor(new Class[]{this.activity.getClass(), ViewGroup.class}).newInstance(new Object[]{this.activity, parent});
        } catch (InstantiationException var4) {
            var4.printStackTrace();
        } catch (IllegalAccessException var5) {
            var5.printStackTrace();
        } catch (InvocationTargetException var6) {
            var6.printStackTrace();
        } catch (NoSuchMethodException var7) {
            var7.printStackTrace();
        }

        return null;
    }

    public void onBindViewHolder(VH holder, int position) {
        if(this.objects != null && this.objects.size() > 0) {
            holder.showData(this.objects.get(position));
            this.viewHolders.put(this.objects.get(position), holder);
        }

    }

    public void setObjects(List<OBJ> objects) {
        this.objects = objects;
        this.copiedObjects.clear();
        this.copiedObjects.addAll(this.objects);
        this.notifyDataSetChanged();
    }

    public void appendObjects(List<OBJ> objects) {
        if(this.objects == null) {
            this.objects = new ArrayList();
        }

        this.objects.addAll(objects);
        this.copiedObjects.clear();
        this.copiedObjects.addAll(this.objects);
        this.notifyItemRangeChanged(0, this.objects.size());
    }

    public int getItemCount() {
        return UISimulation.size(this.objects);
    }

    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void filter(String text) {
        if(this.objects != null && this.copiedObjects != null) {
            this.objects.clear();
            if(text.isEmpty()) {
                this.objects.addAll(this.copiedObjects);
            } else {
                text = text.toLowerCase();
                int cursor = 0;

                for(Iterator var3 = this.copiedObjects.iterator(); var3.hasNext(); ++cursor) {
                    OBJ item = (OBJ) var3.next();
                    if(!((ObjectAdapterInterface)item).isSearchable()) {
                        return;
                    }

                    try {
                        Class fieldClass = item.getClass();
                        Field field = fieldClass.getDeclaredField(((ObjectAdapterInterface)item).canSearchByField());
                        field.setAccessible(true);
                        String value = (String)field.get(item);
                        if(value.toLowerCase().contains(text)) {
                            this.objects.add(item);
                        }

                        if(this.viewHolders.get(item) != null) {
                            ((ACTViewHolder)this.viewHolders.get(item)).find(text);
                        }
                    } catch (Exception var8) {
                        var8.printStackTrace();
                    }
                }
            }

            this.notifyDataSetChanged();
        }
    }

}

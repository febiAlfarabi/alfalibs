package com.alfarabi.alfalibs.adapters.recyclerview;

import android.support.v4.app.Fragment;
import android.view.View;
import com.alfarabi.alfalibs.adapters.interfaze.StickyHolderInterface;
import com.alfarabi.alfalibs.adapters.recyclerview.viewholder.StickyBodyViewHolder;
import com.alfarabi.alfalibs.adapters.recyclerview.viewholder.StickyHeaderViewHolder;
import com.alfarabi.alfalibs.fragments.interfaze.RecyclerCallback;
import com.alfarabi.alfalibs.helper.model.ObjectAdapterInterface;
import com.onecode.stickyheadergrid.adapter.StickyGridAdapter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by alfarabi on 1/15/18.
 */

public abstract class StickyRecyclerGridAdapter<OBJ extends Object & ObjectAdapterInterface & StickyHolderInterface, F extends Fragment & RecyclerCallback,
        HVH extends StickyHeaderViewHolder, BVH extends StickyBodyViewHolder>
        extends StickyGridAdapter<OBJ, BVH,HVH> {


    @Getter@Setter F fragment ;
    @Getter @Setter Class<HVH> headerVhClass ;
    @Getter@Setter Class<BVH> bodyVhClass ;
    @Getter List<OBJ> objects ;

    public StickyRecyclerGridAdapter(F fragment, Class<HVH> headerVhClass, Class<BVH> bodyVhClass, List<OBJ> objects){
        super(fragment.getActivity());
        this.fragment = fragment ;
        this.headerVhClass = headerVhClass ;
        this.bodyVhClass = bodyVhClass ;
        this.objects = objects ;
        this.objects = objects ;
    }


    public void setObjects(List<OBJ> objects) {
        if(this.objects!=null){
            if(this.objects.size()>0){
                for (int i = 0; i < this.objects.size(); i++) {
                    removeObject(i);
                }
            }
            this.objects.clear();
            this.objects = objects;
            appendBottomAll(objects);
        }
    }

    public void appendObject(OBJ object, boolean bottom) {
        if(this.objects!=null){
            if(bottom){
                this.objects.add(object);
                appendBottom(object);
            }else{
               this.objects.add(0, object);
               appendTop(object);
            }
        }else{
            this.objects = new ArrayList<>();
            appendObject(object, bottom);
        }
    }


    public void removeObject(int position){
        if(this.objects!=null && objects.size()>position){
            remove(position);
        }
    }

    @Override
    protected HVH headerViewHolder(View view) {
        try {
            return headerVhClass.getConstructor(fragment.getClass(), View.class).newInstance(fragment, view);
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
    protected void populateHeader(OBJ obj, HVH bvh) {
        bvh.showData(obj);
    }


    @Override
    protected BVH viewHolder(View view) {
        try {
            return bodyVhClass.getConstructor(fragment.getClass(), View.class).newInstance(fragment, view);
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
    protected void populate(OBJ obj, BVH hvh) {
        hvh.showData(obj);
    }

}

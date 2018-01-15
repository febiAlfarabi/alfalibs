package com.alfarabi.alfalibs.adapters.recyclerview;

import android.app.Fragment;
import android.view.ViewGroup;

import com.alfarabi.alfalibs.adapters.recyclerview.viewholder.StickyBodyViewHolder;
import com.alfarabi.alfalibs.adapters.recyclerview.viewholder.StickyHeaderViewHolder;
import com.alfarabi.alfalibs.fragments.interfaze.RecyclerCallback;
import com.alfarabi.alfalibs.helper.model.ObjectAdapterInterface;
import com.codewaves.stickyheadergrid.StickyHeaderGridAdapter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by alfarabi on 1/15/18.
 */

public class StickyGridAdapter<OBJ extends Object & ObjectAdapterInterface, F extends Fragment & RecyclerCallback,
        HVH extends StickyHeaderViewHolder, BVH extends StickyBodyViewHolder> extends StickyHeaderGridAdapter {


    @Getter@Setter F fragment ;
    @Getter @Setter Class<HVH> headerVhClass ;
    @Getter@Setter Class<BVH> bodyVhClass ;
    @Getter List<OBJ> objects ;


    public StickyGridAdapter(F fragment, Class<HVH> headerVhClass, Class<BVH> bodyVhClass, List<OBJ> objects){
        this.fragment = fragment ;
        this.headerVhClass = headerVhClass ;
        this.bodyVhClass = bodyVhClass ;
        this.objects = objects ;
        this.objects = objects ;
    }

    @Override
    public StickyHeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        try {
            return headerVhClass.getConstructor(fragment.getClass(), ViewGroup.class).newInstance(fragment, parent);
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
    public StickyBodyViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        try {
            return bodyVhClass.getConstructor(fragment.getClass(), ViewGroup.class).newInstance(fragment, parent);
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
    public void onBindHeaderViewHolder(HeaderViewHolder viewHolder, int section) {
        if(objects!=null && objects.size()>0) {
            if (viewHolder instanceof StickyHeaderViewHolder) {
                ((StickyHeaderViewHolder) viewHolder).showData(objects.get(section));
            }
        }
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder viewHolder, int section, int offset) {
        if(objects!=null && objects.size()>0) {
            if (viewHolder instanceof StickyBodyViewHolder) {
                ((StickyBodyViewHolder) viewHolder).showData(objects.get(section));
            }
        }
    }

    @Override
    public int getSectionCount() {
        return objects!=null?objects.size():0;
    }
}

package com.alfarabi.alfalibs.adapters.recyclerview;

import android.support.v4.app.Fragment;
import android.view.ViewGroup;

import com.alfarabi.alfalibs.adapters.recyclerview.viewholder.SimpleStickyBodyViewHolder;
import com.alfarabi.alfalibs.adapters.recyclerview.viewholder.SimpleStickyHeaderViewHolder;
import com.alfarabi.alfalibs.adapters.recyclerview.viewholder.SimpleViewHolder;
import com.alfarabi.alfalibs.fragments.interfaze.SimpleFragmentCallback;
import com.alfarabi.alfalibs.helper.model.StickyHeaderInterface;
import com.alfarabi.alfalibs.tools.UISimulation;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;

/**
 * Created by Alfarabi on 6/22/17.
 */

public class SimpleStickyRecyclerAdapter<F extends Fragment & SimpleFragmentCallback, OBJ extends Object & StickyHeaderInterface
        , HVH extends SimpleStickyHeaderViewHolder, BVH extends SimpleStickyBodyViewHolder> extends SimpleRecyclerAdapter<OBJ, F, BVH> implements StickyHeaderAdapter<HVH> {

    private Class<HVH> headerVh ;

    public SimpleStickyRecyclerAdapter(F fragment, Class<HVH> headerVh, Class<BVH> bodyVh, List<OBJ> objects) {
        super(fragment, bodyVh, objects);
        this.headerVh = headerVh;
    }

    @Override
    public long getHeaderId(int position) {
        return objects.get(position).getHeaderId();
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



    @Override
    public void onBindHeaderViewHolder(HVH holder, int position) {
        holder.showData(objects);
    }

    @Override
    public int getItemCount() {
        return UISimulation.size(objects);
    }
}

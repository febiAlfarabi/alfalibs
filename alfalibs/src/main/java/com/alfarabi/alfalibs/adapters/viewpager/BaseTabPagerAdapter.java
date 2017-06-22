package com.alfarabi.alfalibs.adapters.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.alfarabi.alfalibs.fragments.SimpleBaseFragment;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Alfarabi on 6/20/17.
 */

public abstract class BaseTabPagerAdapter<CF extends SimpleBaseFragment> extends FragmentStatePagerAdapter{

    public abstract String[] titles();
    public abstract Class<CF>[] fragmentClasses();

    public BaseTabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CF getItem(int position) {
        CF cf = null;
        try {
            cf = fragmentClasses()[position].getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return cf;
    }

    @Override
    public int getCount() {
        return titles().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles()[position];
    }
}

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
    public abstract CF[] fragmentClasses();

    public BaseTabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CF getItem(int position) {
        CF cf = fragmentClasses()[position];
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

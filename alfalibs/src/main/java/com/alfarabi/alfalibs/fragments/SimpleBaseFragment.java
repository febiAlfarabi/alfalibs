package com.alfarabi.alfalibs.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfarabi.alfalibs.fragments.interfaze.SimpleFragmentInitiator;
import com.alfarabi.alfalibs.tools.WLog;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Alfarabi on 6/15/17.
 */

public abstract class SimpleBaseFragment extends Fragment implements SimpleFragmentInitiator{
    public static String TAG = SimpleBaseFragment.class.getName();

    public static int VIEW_DELAY = 1000;

    @Getter@Setter View container ;

    private boolean viewObserved ;

    public void onceViewObserved(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TAG = getTAG();
        WLog.d(TAG,"onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getTAG();
        WLog.d(TAG,"onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WLog.d(TAG,"onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WLog.d(TAG,"onActivityCreated()");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        WLog.d(TAG,"onViewCreated()");
        super.onViewCreated(view, savedInstanceState);
        if (view != null) {
            this.container = view;
            if(viewObserved){
                return;
            }
            this.container.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
                if(viewObserved){
                    return;
                }
                viewObserved = !viewObserved ;
                this.container.postDelayed(() -> {
                    if (getActivity() != null && isVisible()) {
                        onceViewObserved();
                    }
                }, VIEW_DELAY);
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        WLog.d(TAG,"onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        WLog.d(TAG,"onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        WLog.d(TAG,"onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        WLog.d(TAG,"onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        WLog.d(TAG,"onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WLog.d(TAG,"onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        WLog.d(TAG,"onDetach()");
    }

    @Override
    public boolean onBackPressed() {
        WLog.d(TAG,"onBackPressed()");
        return true;
    }
//
//    @Override
//    public String getTAG() {
//        return TAG = getClass().getName();
//    }
}

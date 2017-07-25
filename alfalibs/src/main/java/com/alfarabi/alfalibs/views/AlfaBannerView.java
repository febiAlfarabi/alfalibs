package com.alfarabi.alfalibs.views;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.alfarabi.alfalibs.tools.WLog;
import com.yarolegovich.discretescrollview.DiscreteScrollView;

/**
 * Created by Alfarabi on 7/24/17.
 */

public class AlfaBannerView extends DiscreteScrollView {

    private boolean forward = true ;
    private boolean pause = false ;

    public AlfaBannerView(Context context) {
        super(context);
    }

    public AlfaBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void startAutoScroll(int timeMilis){
        postDelayed(() -> {
            if(!pause){
                if(forward){
                    if(getCurrentItem()<getAdapter().getItemCount()-1){
                        smoothScrollToPosition(getCurrentItem()+1);
                        forward = true ;
                        startAutoScroll(timeMilis);
                    }else{
                        smoothScrollToPosition(getCurrentItem()-1);
                        forward = false ;
                        startAutoScroll(timeMilis);
                    }
                }else{
                    if(getCurrentItem()>0){
                        smoothScrollToPosition(getCurrentItem()-1);
                        forward = false ;
                        startAutoScroll(timeMilis);
                    }else{
                        forward = true ;
                        startAutoScroll(timeMilis);
                    }
                }
            }
            setOnTouchListener((v, event) -> {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    handler.postDelayed(mLongPressed, 1000);
                    pause = true;
                }
                if((event.getAction() == MotionEvent.ACTION_MOVE)||(event.getAction() == MotionEvent.ACTION_UP)) {
                    handler.removeCallbacks(mLongPressed);
                    pause = true;
                }
                return false ;
            });
        }, timeMilis);
    }

    final Handler handler = new Handler();
    Runnable mLongPressed = () -> {
        WLog.i("", "Long press!");
    };
}

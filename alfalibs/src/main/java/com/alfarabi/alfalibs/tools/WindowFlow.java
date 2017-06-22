package com.alfarabi.alfalibs.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;


import org.fingerlinks.mobile.android.navigator.Navigator;
import org.fingerlinks.mobile.android.navigator.builder.Builders;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Alfarabi on 6/15/17.
 */

public class WindowFlow {

    public static final String TAG = WindowFlow.class.getName();

    public static Fragment goFirst(FragmentActivity activity, String tag, int id){
        activity.getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
        if(currentIs(activity,tag)){
            return currentFragment(activity);
        }
        Log.i(TAG, "GO TO = "+tag);
        Builders.Any.G G = Navigator.with(activity).build();
        Builders.Any.F F = G.goTo(tag,id).tag(tag);
        F.addToBackStack();
        F.replace().commit();
        return activity.getSupportFragmentManager().findFragmentByTag(actualFragmentTag(activity));
    }

    public static Fragment goTo(FragmentActivity activity, String tag, int id, boolean backStack){
        activity.getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
        if(currentIs(activity,tag)){
            return currentFragment(activity);
        }
        Log.i(TAG, "GO TO = "+tag);
        Builders.Any.G G = Navigator.with(activity).build();
        Builders.Any.F F = G.goTo(tag,id).tag(tag);
        if(backStack){
            F.addToBackStack();
        }
        F.replace().commit();
        return activity.getSupportFragmentManager().findFragmentByTag(tag);
    }

    public static Fragment goTo(FragmentActivity activity, Fragment fragment, String tag, int id, boolean backStack){
        activity.getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
        if(currentIs(activity,tag)){
            return currentFragment(activity);
        }
        Log.i(TAG, "GO TO = "+tag);
        Builders.Any.G G = Navigator.with(activity).build();
        Builders.Any.F F = G.goTo(fragment,id).tag(tag);
        if(backStack){
            F.addToBackStack();
        }
        F.replace().commit();
        return activity.getSupportFragmentManager().findFragmentByTag(tag);
    }


    public static Fragment goTo(FragmentActivity activity, String tag, Bundle bundle, int id, boolean backStack){
        activity.getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
        if(currentIs(activity, tag)){
            return currentFragment(activity);
        }
        Log.i(TAG, "GO TO = "+tag);
        Builders.Any.G G = Navigator.with(activity).build();
        Builders.Any.F F = G.goTo(tag, bundle, id).tag(tag);
        if(backStack){
            F.addToBackStack();
        }
        F.replace().commit();
        return activity.getSupportFragmentManager().findFragmentByTag(tag);
    }

    public static Fragment backstack(FragmentActivity activity){
        activity.getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
        boolean canGoBack = Navigator.with(activity).utils().canGoBack(activity.getSupportFragmentManager());
        if(canGoBack){
            Navigator.with(activity).utils().goToPreviousBackStack();
        }
        return activity.getSupportFragmentManager().findFragmentByTag(actualFragmentTag(activity));
    }


    public static Fragment backstack(FragmentActivity activity, String tag, int id){
        activity.getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
        boolean canGoBack = Navigator.with(activity).utils().canGoBackToSpecificPoint(tag, id, activity.getSupportFragmentManager());
        if(canGoBack){
            Navigator.with(activity).utils().goBackToSpecificPoint(tag);
        }
        return activity.getSupportFragmentManager().findFragmentByTag(actualFragmentTag(activity));
    }

    public static boolean canGoBack(FragmentActivity activity){
        boolean canGoBack = Navigator.with(activity).utils().canGoBack(activity.getSupportFragmentManager());
        return canGoBack;
    }

    public static Fragment currentFragment(FragmentActivity activity){
        if(actualFragmentTag(activity)==null){
            return null;
        }
        return activity.getSupportFragmentManager().findFragmentByTag(actualFragmentTag(activity));
    }

    public static boolean currentIs(FragmentActivity a, Class c){
        Log.i(TAG, "TAG OF CURRENT FRAGMENT IS = "+actualFragmentTag(a));
        try {
            if(c.getName().equalsIgnoreCase(actualFragmentTag(a))){
                return true ;
            }
        }catch (Exception e){

        }
        return false;
    }

    public static boolean currentIs(FragmentActivity a, String name){
        Log.i(TAG, "TAG OF CURRENT FRAGMENT IS = "+actualFragmentTag(a));
        try {
            if(name.equalsIgnoreCase(actualFragmentTag(a))){
                return true ;
            }
        }catch (Exception e){

        }
        return false;
    }

    public static <T extends Fragment> T currentFragment(FragmentActivity activity, Class<T> cazz){
        return (T) activity.getSupportFragmentManager().findFragmentByTag(actualFragmentTag(activity));
    }

    public static <T extends Fragment> T getFragment(FragmentActivity activity, String tag){
        return (T) activity.getSupportFragmentManager().findFragmentByTag(tag);
    }

    public static String actualFragmentTag(FragmentActivity activity){
        String tag = Navigator.with(activity).utils().getActualTag();
        return tag ;
    }

    public static void restart(@NonNull Context context, @NonNull Class<? extends Activity> nextActivityClass) {
        restart(context, new Intent(context, nextActivityClass));
    }

    public static void restart(@NonNull Context context, @NonNull Intent nextActivity) {
        nextActivity.addFlags(FLAG_ACTIVITY_NEW_TASK); // In case we are called with non-Activity context.
        context.startActivity(nextActivity);
        if (context instanceof Activity)
            ((Activity) context).finish();
        Runtime.getRuntime().exit(0); // Kill kill kill!
    }
    public static Intent startActivity(Context from, Class to){
        Intent intent = new Intent(from, to);
        from.startActivity(intent);
        ((Activity)from).finish();
        return intent;
    }

    public static Intent startActivity(Context from, Class to, boolean finish){
        Intent intent = new Intent(from, to);
        from.startActivity(intent);
        if(finish)
            ((Activity)from).finish();
        return intent;
    }

    public static Intent startActivityForResult(Context from, Class to, int requestCode){
        Intent intent = new Intent(from, to);
        ((Activity)from).startActivityForResult(intent, requestCode);
        ((Activity)from).finish();
        return intent;
    }


}

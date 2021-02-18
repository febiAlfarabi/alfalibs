package com.alfarabi.alfalibs.fragments.api;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alfarabi.alfalibs.R;
import com.alfarabi.alfalibs.fragments.notices.WithAlertFragment;
import com.alfarabi.alfalibs.http.Kitchen;
import com.alfarabi.alfalibs.tools.AppProgress;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import okhttp3.Response;
import rx.functions.Action2;
import rx.functions.Action3;

/**
 * Created by alfarabi on 3/19/18.
 */

public abstract class APIBaseFragment extends WithAlertFragment {

    public static final int WITH_ERROR_DEF_DIALOG = 1;
    public static final int WITH_ERROR_REPEAT_DIALOG = 2;
    public static final int WITH_ERROR_DEF_TOAST = 3;
    public static final int WITH_ERROR_REPEAT_TOAST = 4;
    public static final int WITH_AUTO_REPEAT_ON_ERROR = 5;

    interface Test{
        Observable<String> test();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public <T> void grabServerData(Observable<T> apiClassObservable, Consumer<? super T> resultConsumer, Consumer<Throwable> exceptionConsumer, boolean... dismissPrevious){
        Kitchen.cook(apiClassObservable,resultConsumer, exceptionConsumer, dismissPrevious!=null&&dismissPrevious.length>0?dismissPrevious[0]:false);
    }

    public <T> void grabServerData(Observable<T> apiClassObservable, Action2<? super T, Response> resultConsumer, Action3<Boolean, Throwable, Response> exceptionConsumer, boolean... dismissPrevious){
        Kitchen.cook(apiClassObservable, resultConsumer, exceptionConsumer,dismissPrevious!=null&&dismissPrevious.length>0?dismissPrevious[0]:false);
    }


    public <T> void grabServerData(Observable<T> apiClassObservable, Action2<? super T, Response> resultConsumer, Action3<Boolean, Throwable, Response> exceptionConsumer, int loadingTextResource,  boolean... dismissPrevious){
        if(loadingTextResource!=0) {
            AppProgress.showBasic(getActivity(), loadingTextResource, true);
            Kitchen.cook(apiClassObservable, (t, response) -> {
                AppProgress.dismissBasic();
                resultConsumer.call(t, response);
            }, (aBoolean, throwable, response) -> {
                AppProgress.dismissBasic();
                throwable.printStackTrace();
                exceptionConsumer.call(aBoolean, throwable, response);
            }, dismissPrevious!=null&&dismissPrevious.length>0?dismissPrevious[0]:false);
        }else{
            Kitchen.cook(apiClassObservable, resultConsumer, exceptionConsumer,dismissPrevious!=null&&dismissPrevious.length>0?dismissPrevious[0]:false);
        }
    }


    public <T> void grabServerData(Observable<T> apiClassObservable, Consumer<? super T> resultConsumer, Consumer<Throwable> exceptionConsumer, int loadingTextResource,  boolean... dismissPrevious){
        if(loadingTextResource!=0) {
            AppProgress.showBasic(getActivity(), loadingTextResource, true);
            Kitchen.cook(apiClassObservable, t -> {
                AppProgress.dismissBasic();
                resultConsumer.accept(t);
            }, throwable -> {
                AppProgress.dismissBasic();
                throwable.printStackTrace();
                exceptionConsumer.accept(throwable);
            }, dismissPrevious!=null&&dismissPrevious.length>0?dismissPrevious[0]:false);
        }else{
            Kitchen.cook(apiClassObservable, resultConsumer, exceptionConsumer, dismissPrevious!=null&&dismissPrevious.length>0?dismissPrevious[0]:false);
        }
    }


    public <T> void grabServerData(Observable<T> apiClassObservable, Consumer<? super T> resultConsumer, Consumer<Throwable> exceptionConsumer, int loadingTextResource,  boolean dismissPrevious){
        if(loadingTextResource!=0) {
            AppProgress.showBasic(getActivity(), loadingTextResource, true);
            Kitchen.cook(apiClassObservable, t -> {
                AppProgress.dismissBasic();
                resultConsumer.accept(t);
            }, throwable -> {
                AppProgress.dismissBasic();
                throwable.printStackTrace();
                exceptionConsumer.accept(throwable);
            }, dismissPrevious);
        }else{
            Kitchen.cook(apiClassObservable, resultConsumer, exceptionConsumer, dismissPrevious);
        }
    }

    public <T> void grabServerData(Observable<T> apiClassObservable, Consumer<? super T> resultConsumer, Consumer<Throwable> exceptionConsumer
            , int loadingTextResource, boolean dismissPrevious, int ADDITIONAL_DEFAULT){
        if(loadingTextResource!=0) {
            AppProgress.showBasic(getActivity(), loadingTextResource, true);
            Kitchen.cook(apiClassObservable, t -> {
                AppProgress.dismissBasic();
                resultConsumer.accept(t);
            }, throwable -> {
                AppProgress.dismissBasic();
                throwable.printStackTrace();
                exceptionConsumer.accept(throwable);
                switch (ADDITIONAL_DEFAULT){
                    case WITH_ERROR_DEF_DIALOG :
                        showDialog(throwable, (dialog, which) -> {});
                        break;
                    case WITH_ERROR_REPEAT_DIALOG :
                        showDialog(getString(R.string.error), getString(R.string.api_repeat_confirm), new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                grabServerData(apiClassObservable, resultConsumer,  exceptionConsumer, loadingTextResource, dismissPrevious, ADDITIONAL_DEFAULT);
                            }
                        }, new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            }
                        });
                        break;
                    case WITH_ERROR_DEF_TOAST :
                        showDialog(throwable, (dialog, which) -> {});
                        break;
                    case WITH_ERROR_REPEAT_TOAST :
                        showDialog(getString(R.string.api_repeat_confirm), (dialog, which) -> {
                            grabServerData(apiClassObservable, resultConsumer,  exceptionConsumer, loadingTextResource, dismissPrevious, ADDITIONAL_DEFAULT);
                        });
                        break;
                    case WITH_AUTO_REPEAT_ON_ERROR :
                        grabServerData(apiClassObservable, resultConsumer,  exceptionConsumer, loadingTextResource, dismissPrevious, ADDITIONAL_DEFAULT);
                        break;
                    default:
                        return;
                }
            }, dismissPrevious);
        }else{
            Kitchen.cook(apiClassObservable, t -> {
                resultConsumer.accept(t);
            }, throwable -> {
                throwable.printStackTrace();
                exceptionConsumer.accept(throwable);
                switch (ADDITIONAL_DEFAULT){
                    case WITH_ERROR_DEF_DIALOG :
                        showDialog(throwable, (dialog, which) -> {});
                        break;
                    case WITH_ERROR_REPEAT_DIALOG :
                        showDialog(getString(R.string.error), getString(R.string.api_repeat_confirm), new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                grabServerData(apiClassObservable, resultConsumer,  exceptionConsumer, loadingTextResource, dismissPrevious, ADDITIONAL_DEFAULT);
                            }
                        }, new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            }
                        });
                        break;
                    case WITH_ERROR_DEF_TOAST :
                        showDialog(throwable, (dialog, which) -> {});
                        break;
                    case WITH_ERROR_REPEAT_TOAST :
                        showDialog(getString(R.string.api_repeat_confirm), (dialog, which) -> {
                            grabServerData(apiClassObservable, resultConsumer,  exceptionConsumer, loadingTextResource, dismissPrevious, ADDITIONAL_DEFAULT);
                        });
                        break;
                    case WITH_AUTO_REPEAT_ON_ERROR :
                        grabServerData(apiClassObservable, resultConsumer,  exceptionConsumer, loadingTextResource, dismissPrevious, ADDITIONAL_DEFAULT);
                        break;
                    default:
                        return;
                }
            }, dismissPrevious);
        }
    }

    public <T> void grabServerData(Observable<T> apiClassObservable, Consumer<? super T> resultConsumer, Consumer<Throwable> exceptionConsumer
            , boolean dismissPrevious, int ADDITIONAL_DEFAULT){
        Kitchen.cook(apiClassObservable, t -> {
            resultConsumer.accept(t);
        }, throwable -> {
            throwable.printStackTrace();
            exceptionConsumer.accept(throwable);
            switch (ADDITIONAL_DEFAULT){
                case WITH_ERROR_DEF_DIALOG :
                    showDialog(throwable, (dialog, which) -> {});
                    break;
                case WITH_ERROR_REPEAT_DIALOG :
                    showDialog(getString(R.string.error), getString(R.string.api_repeat_confirm), new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            grabServerData(apiClassObservable, resultConsumer,  exceptionConsumer, dismissPrevious, ADDITIONAL_DEFAULT);
                        }
                    }, new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        }
                    });
                    break;
                case WITH_ERROR_DEF_TOAST :
                    showDialog(throwable, (dialog, which) -> {});
                    break;
                case WITH_ERROR_REPEAT_TOAST :
                    showDialog(getString(R.string.api_repeat_confirm), (dialog, which) -> {
                        grabServerData(apiClassObservable, resultConsumer,  exceptionConsumer, dismissPrevious, ADDITIONAL_DEFAULT);
                    });
                    break;
                case WITH_AUTO_REPEAT_ON_ERROR :
                    grabServerData(apiClassObservable, resultConsumer,  exceptionConsumer, dismissPrevious, ADDITIONAL_DEFAULT);
                    break;
                default:
                    return;
            }
        }, dismissPrevious);
    }

}

package com.alfarabi.alfalibsapps;

import android.app.Activity;
import android.os.Bundle;

import com.alfarabi.alfalibs.helper.AlfaPagination;
import com.alfarabi.alfalibs.http.HttpInstance;
import com.alfarabi.alfalibs.http.mock.ExampleService;
import com.alfarabi.alfalibs.http.mock.MockService;
import com.alfarabi.alfalibs.model.UserModel;
import com.alfarabi.alfalibs.tools.WLog;
import com.alfarabi.alfalibs.views.AlfaRecyclerView;
import com.alfarabi.alfalibs.views.AlfaSwipeRefreshLayout;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {
    public static final String TAG = MainActivity.class.getName();

//    @BindView(R.id.alfarefreshlayout) AlfaSwipeRefreshLayout alfarefreshlayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        HttpInstance.call(HttpInstance.mock(this, MockService.class).getUsers(1), userModels -> {
//            WLog.i(TAG, new Gson().toJson(userModels));
//        }, Throwable::printStackTrace);


        HttpInstance.withProgress(this).observe(HttpInstance.create(MockService.class).getUser(1), userModel -> {

        }, throwable -> {
            throwable.printStackTrace();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

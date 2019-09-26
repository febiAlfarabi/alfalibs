package com.alfarabi.alfalibsapps;

import android.os.Bundle;
import com.alfarabi.alfalibs.activity.APIBaseActivity;
import butterknife.ButterKnife;

public class MainActivity extends APIBaseActivity {
    public static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public int contentXmlLayout() {
        return R.layout.activity_main;
    }
}

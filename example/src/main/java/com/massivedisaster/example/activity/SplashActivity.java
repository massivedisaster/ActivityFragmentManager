package com.massivedisaster.example.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.massivedisaster.activitymanager.ActivityFragmentManager;
import com.massivedisaster.example.activitymanager.R;
import com.massivedisaster.example.fragment.FragmentOpenAdd;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityFragmentManager.open(this, PrimaryThemeActivity.class, FragmentOpenAdd.class);
        finish();
    }
}

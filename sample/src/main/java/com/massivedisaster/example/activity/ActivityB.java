package com.massivedisaster.example.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.massivedisaster.example.activitymanager.R;

public class ActivityB extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
    }
}

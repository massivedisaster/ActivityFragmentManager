package com.massivedisaster.example.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.massivedisaster.activitymanager.ActivityFragmentManager;
import com.massivedisaster.example.activitymanager.R;
import com.massivedisaster.example.feature.sharedelements.FragmentSharedElement;

public class ActivityA extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        findViewById(R.id.imgSharedElement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityA.this,ActivityB.class);

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        ActivityA.this,
                        v,
                        ViewCompat.getTransitionName(v));

                ActivityA.this.startActivity(i, options.toBundle());

                ActivityFragmentManager.open(ActivityA.this, ActivityPrimaryTheme.class, FragmentSharedElement.class)
                        .addSharedElement(v, ViewCompat.getTransitionName(v))
                        .commit();
            }
        });
    }
}

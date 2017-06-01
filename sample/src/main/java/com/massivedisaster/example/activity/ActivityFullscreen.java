package com.massivedisaster.example.activity;

import android.support.v4.app.Fragment;

import com.massivedisaster.activitymanager.AbstractFragmentActivity;
import com.massivedisaster.example.activitymanager.R;
import com.massivedisaster.example.fragment.FragmentSplash;

public class ActivityFullscreen extends AbstractFragmentActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fullscreen;
    }

    @Override
    protected int getContainerViewId() {
        return R.id.frmContainer;
    }

    @Override
    protected Class<? extends Fragment> getDefaultFragment() {
        return FragmentSplash.class;
    }
}

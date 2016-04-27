package com.massivedisaster.example.activity;

import com.massivedisaster.activitymanager.AbstractThemeActivity;
import com.massivedisaster.example.activitymanager.R;

/**
 * Activity Manager
 * Created by jms on 27/04/16.
 */
public class SecondaryThemeActivity extends AbstractThemeActivity {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_secondary;
    }

    @Override
    protected int getContainerViewId() {
        return R.id.frmContainer;
    }
}

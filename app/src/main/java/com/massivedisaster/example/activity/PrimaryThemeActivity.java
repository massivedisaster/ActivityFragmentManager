package com.massivedisaster.example.activity;

import com.massivedisaster.activitymanager.AbstractThemeActivity;
import com.massivedisaster.example.activitymanager.R;

/**
 * Activity Manager
 * Created by jms on 27/04/16.
 */
public class PrimaryThemeActivity extends AbstractThemeActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_primary;
    }

    @Override
    protected int getContainerViewId() {
        return R.id.frmContainer;
    }
}

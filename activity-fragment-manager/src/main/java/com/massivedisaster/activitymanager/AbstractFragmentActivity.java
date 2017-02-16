package com.massivedisaster.activitymanager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Activity Manager
 * Created by jms on 21/04/16.
 */
public abstract class AbstractFragmentActivity extends AppCompatActivity {

    protected abstract int getLayoutResId();

    protected abstract int getContainerViewId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResId());
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (getSupportFragmentManager().getBackStackEntryCount() == 0 && getIntent().hasExtra(ActivityFragmentManager.ACTIVITY_MANAGER_FRAGMENT)) {
            performTransaction(getFragment(), getFragmentTag());
        }
    }

    protected void performTransaction(Fragment fragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(getContainerViewId(), fragment, tag);
        ft.addToBackStack(tag);

        ft.commitAllowingStateLoss();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Force to call onActivityResult on nested fragments
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != null)
                fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private Fragment getFragment() {
        try {
            Fragment f = ((Fragment) Class.forName(getIntent().getStringExtra(ActivityFragmentManager.ACTIVITY_MANAGER_FRAGMENT)).newInstance());

            if (getIntent().getExtras() != null)
                f.setArguments(getIntent().getExtras());

            return f;
        } catch (ClassNotFoundException e) {
            Log.e(ActivityFragmentManager.class.getCanonicalName(), e.toString());
        } catch (IllegalAccessException e) {
            Log.e(ActivityFragmentManager.class.getCanonicalName(), e.toString());
        } catch (InstantiationException e) {
            Log.e(ActivityFragmentManager.class.getCanonicalName(), e.toString());
        }

        return null;
    }

    private String getFragmentTag() {
        return getIntent().getStringExtra(ActivityFragmentManager.ACTIVITY_MANAGER_FRAGMENT_TAG);
    }

    protected int getAnimFragmentEnter() {
        return android.R.anim.fade_in;
    }

    protected int getAnimFragmentExit() {
        return android.R.anim.fade_out;
    }

    protected int getAnimFragmentPopEnter() {
        return android.R.anim.fade_in;
    }

    protected int getAnimFragmentPopExit() {
        return android.R.anim.fade_out;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}

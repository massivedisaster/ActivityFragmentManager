package com.massivedisaster.activitymanager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Abstract Fragment Activity.
 */
public abstract class AbstractFragmentActivity extends AppCompatActivity {

    /**
     * @return the layout resource id.
     */
    protected abstract int getLayoutResId();

    /**
     * @return the container view id to inject the fragments.
     */
    protected abstract int getContainerViewId();

    /**
     * Override this method if you want to set a default fragment.
     * Example: If you want to use this activity for a splash screen you need to override this method.
     *
     * @return The fragment class to inject in this activity.
     */
    protected Class<? extends Fragment> getDefaultFragment() {
        Log.w(AbstractFragmentActivity.class.getCanonicalName(), "No default fragment implemented!");
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResId());
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (getSupportFragmentManager().getBackStackEntryCount() == 0 && getIntent().hasExtra(ActivityFragmentManager.ACTIVITY_MANAGER_FRAGMENT)) {
            performInitialTransaction(getFragment(getIntent().getStringExtra(ActivityFragmentManager.ACTIVITY_MANAGER_FRAGMENT)), getFragmentTag());
        } else if (getDefaultFragment() != null) {
            performInitialTransaction(getFragment(getDefaultFragment().getCanonicalName()), null);
        }
    }

    /**
     * Perform a transaction of a fragment.
     *
     * @param fragment the fragment to be applied.
     * @param tag      the tag to be applied.
     */
    protected void performInitialTransaction(Fragment fragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(getContainerViewId(), fragment, tag);
        ft.addToBackStack(tag);

        ft.commitAllowingStateLoss();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * Get a new instance of the Fragment.
     *
     * @param clazz the canonical Fragment name.
     * @return the instance of the Fragment.
     */
    private Fragment getFragment(String clazz) {
        try {
            Fragment f = ((Fragment) Class.forName(clazz).newInstance());

            if (getIntent().getExtras() != null) {
                f.setArguments(getIntent().getExtras());
            }

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

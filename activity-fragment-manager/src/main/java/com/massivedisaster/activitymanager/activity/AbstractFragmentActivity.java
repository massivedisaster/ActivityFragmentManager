/*
 * ActivityFragmentManager - A library to help android developer working easily with activities and fragments.
 *
 * Copyright (c) 2017 ActivityFragmentManager
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.massivedisaster.activitymanager.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.massivedisaster.activitymanager.ActivityFragmentManager;
import com.massivedisaster.activitymanager.animation.TransactionAnimation;

import static com.massivedisaster.activitymanager.ActivityTransaction.ACTIVITY_MANAGER_FRAGMENT;
import static com.massivedisaster.activitymanager.ActivityTransaction.ACTIVITY_MANAGER_FRAGMENT_TAG;

/**
 * Abstract Fragment Activity.
 */
public abstract class AbstractFragmentActivity extends AppCompatActivity implements TransactionAnimation {

    /**
     * @return the layout resource id.
     */
    @LayoutRes
    public abstract int getLayoutResId();

    /**
     * @return the container view id to inject the fragments.
     */
    @IdRes
    public abstract int getContainerViewId();

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            requestWindowFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
            requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }

        super.onCreate(savedInstanceState);

        View rootView = LayoutInflater.from(this).inflate(getLayoutResId(), null);
        setContentView(rootView);
        initializeDataBinding(rootView);

        if (getSupportFragmentManager().getBackStackEntryCount() == 0 && getIntent().hasExtra(ACTIVITY_MANAGER_FRAGMENT)) {
            performInitialTransaction(getFragment(getIntent().getStringExtra(ACTIVITY_MANAGER_FRAGMENT)), getFragmentTag());
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

        ft.commitNow();
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

    /**
     * Override if you want to initialize DataBinding
     *
     * @param view the view to bind
     */
    @SuppressWarnings("PMD.UnnecessaryReturn")
    protected void initializeDataBinding(View view) {
        return;
    }

    private String getFragmentTag() {
        return getIntent().getStringExtra(ACTIVITY_MANAGER_FRAGMENT_TAG);
    }

    @Override
    public int getAnimationEnter() {
        return android.R.anim.fade_in;
    }

    @Override
    public int getAnimationExit() {
        return android.R.anim.fade_out;
    }

    @Override
    public int getAnimationPopEnter() {
        return android.R.anim.fade_in;
    }

    @Override
    public int getAnimationPopExit() {
        return android.R.anim.fade_out;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

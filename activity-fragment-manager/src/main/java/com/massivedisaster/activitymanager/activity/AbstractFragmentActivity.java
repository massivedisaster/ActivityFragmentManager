/*
 * Activity Fragment Manager - A library to help android developer working easily with activities and fragments
 * Copyright (C) 2017 ActivityFragmentManager.
 *
 * ActivityFragmentManager is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or any later version.
 *
 * ActivityFragmentManager is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License along
 * with ActivityFragmentManager. If not, see <http://www.gnu.org/licenses/>.
 */

package com.massivedisaster.activitymanager.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
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

        setContentView(getLayoutResId());
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (getSupportFragmentManager().getBackStackEntryCount() == 0 && getIntent().hasExtra(ACTIVITY_MANAGER_FRAGMENT)) {
            performInitialTransaction(getFragment(getIntent().getStringExtra(ACTIVITY_MANAGER_FRAGMENT)), getFragmentTag());
        } else if (getDefaultFragment() != null) {
            performInitialTransaction(getFragment(getDefaultFragment().getCanonicalName()), null);
        }

        // Add element shared startPostponedEnterTransition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            supportPostponeEnterTransition();

            getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentViewCreated(FragmentManager fm, Fragment f, final View view, Bundle savedInstanceState) {
                    if (view != null) {
                        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public boolean onPreDraw() {
                                view.getViewTreeObserver().removeOnPreDrawListener(this);

                                supportStartPostponedEnterTransition();

                                return true;
                            }
                        });
                    }
                }

                @Override
                public void onFragmentStarted(FragmentManager fm, Fragment f) {
                    final View view = f.getView();
                    if (view != null) {
                        f.getView().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public boolean onPreDraw() {
                                view.getViewTreeObserver().removeOnPreDrawListener(this);

                                startPostponedEnterTransition();

                                return true;
                            }
                        });
                    }
                }
            }, true);
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
        return getIntent().getStringExtra(ACTIVITY_MANAGER_FRAGMENT_TAG);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    finish();
                }
            });
            super.onBackPressed();

        } else {
            super.onBackPressed();
        }
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

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

package com.massivedisaster.activitymanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

/**
 * Activity Fragment Manager
 */
public final class ActivityFragmentManager {

    public static final String ACTIVITY_MANAGER_FRAGMENT = "activity_manager_fragment";
    public static final String ACTIVITY_MANAGER_FRAGMENT_TAG = "activity_manager_fragment_tag";

    /**
     * Private constructor to prevent instantiation.
     */
    private ActivityFragmentManager() {
    }

    /**
     * Open a new activity with a specific fragment.
     *
     * @param activity      The activity to be used to start the new activity.
     * @param activityClazz The AbstractFragmentActivity class.
     * @param fragmentClazz The Fragment to be injected in the activityClass.
     * @param tag           The tag to be used in the fragment transaction.
     * @param bundle        The Bundle with the parameters to be passed to the new Fragment.
     * @param requestCode   The code to be used in the startActivityFromResult.
     */
    public static void open(Activity activity, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz,
                            String tag, Bundle bundle, Integer requestCode) {

        Intent intent = new Intent(activity, activityClazz);

        intent.putExtra(ActivityFragmentManager.ACTIVITY_MANAGER_FRAGMENT, fragmentClazz.getCanonicalName());
        intent.putExtra(ActivityFragmentManager.ACTIVITY_MANAGER_FRAGMENT_TAG, tag);

        if (bundle != null) {
            intent.putExtras(bundle);
        }

        if (requestCode == null) {
            activity.startActivity(intent);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * Calls {@link #open(Activity, Class, Class, String, Bundle, Integer)} with a null tag.
     */
    @LinkedMethod
    public static void open(Activity activity, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz,
                            Bundle bundle, Integer requestCode) {
        open(activity, activityClazz, fragmentClazz, null, bundle, requestCode);
    }

    /**
     * Calls {@link #open(Activity, Class, Class, String, Bundle, Integer)} with a null request code.
     */
    @LinkedMethod
    public static void open(Activity activity, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz,
                            String tag, Bundle bundle) {
        open(activity, activityClazz, fragmentClazz, tag, bundle, null);
    }

    /**
     * Calls {@link #open(Activity, Class, Class, String, Bundle, Integer)} with a null bundle and null request code.
     */
    @LinkedMethod
    public static void open(Activity activity, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz,
                            String tag) {
        open(activity, activityClazz, fragmentClazz, tag, null, null);
    }

    /**
     * Calls {@link #open(Activity, Class, Class, String, Bundle, Integer)} with a null bundle.
     */
    @LinkedMethod
    public static void open(Activity activity, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz,
                            String tag, Integer requestCode) {
        open(activity, activityClazz, fragmentClazz, tag, null, requestCode);
    }

    /**
     * Calls {@link #open(Activity, Class, Class, String, Bundle, Integer)} with a null tag and a null request code.
     */
    @LinkedMethod
    public static void open(Activity activity, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz,
                            Bundle bundle) {
        open(activity, activityClazz, fragmentClazz, null, bundle, null);
    }

    /**
     * Calls {@link #open(Activity, Class, Class, String, Bundle, Integer)} with a null tag, bundle and request code.
     */
    @LinkedMethod
    public static void open(Activity activity, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz) {
        open(activity, activityClazz, fragmentClazz, null, null, null);
    }

    /**
     * Calls {@link #open(Activity, Class, Class, String, Bundle, Integer)} with a null tag.
     */
    @LinkedMethod
    public static void open(Activity activity, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz,
                            Integer requestCode) {
        open(activity, activityClazz, fragmentClazz, null, null, requestCode);
    }

    /**
     * Open a new activity with a specific fragment.
     * Use this method if you want to catch the result in the original fragment.
     *
     * @param fragment      The fragment to be used to start the new activity.
     * @param activityClazz The AbstractFragmentActivity class.
     * @param fragmentClazz The Fragment to be injected in the activityClass.
     * @param tag           The tag to be used in the fragment transaction.
     * @param bundle        The Bundle with the parameters to be passed to the new Fragment.
     * @param requestCode   The code to be used in the startActivityFromResult.
     */
    @LinkedMethod
    public static void open(Fragment fragment, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz,
                            String tag, Bundle bundle, Integer requestCode) {

        Intent intent = new Intent(fragment.getContext(), activityClazz);

        intent.putExtra(ActivityFragmentManager.ACTIVITY_MANAGER_FRAGMENT, fragmentClazz.getCanonicalName());
        intent.putExtra(ActivityFragmentManager.ACTIVITY_MANAGER_FRAGMENT_TAG, tag);

        if (bundle != null) {
            intent.putExtras(bundle);
        }

        if (requestCode == null) {
            fragment.startActivity(intent);
        } else {
            fragment.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * Calls {@link #open(Fragment, Class, Class, String, Bundle, Integer)} with a null tag.
     */
    @LinkedMethod
    public static void open(Fragment fragment, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz,
                            Bundle bundle, Integer requestCode) {
        open(fragment, activityClazz, fragmentClazz, null, bundle, requestCode);
    }

    /**
     * Calls {@link #open(Fragment, Class, Class, String, Bundle, Integer)} with a null bundle.
     */
    @LinkedMethod
    public static void open(Fragment fragment, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz,
                            String tag, Integer requestCode) {
        open(fragment, activityClazz, fragmentClazz, tag, null, requestCode);
    }

    /**
     * Calls {@link #open(Fragment, Class, Class, String, Bundle, Integer)} with a null tag and null bundle.
     */
    @LinkedMethod
    public static void open(Fragment fragment, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz,
                            Integer requestCode) {
        open(fragment, activityClazz, fragmentClazz, null, null, requestCode);
    }

    /**
     * Add a new fragment in a specific AbstractFragmentActivity activity.
     *
     * @param activity      The activity to be used to add the new fragment.
     * @param fragmentClazz The Fragment to be injected in the activityClass.
     * @param tag           The tag to be used in the fragment transaction.
     * @param bundle        The Bundle with the parameters to be passed to the new Fragment.
     * @param animation     The animation to used in fragment transaction.
     */
    public static void add(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClazz, String tag, Bundle bundle,
                           TransactionAnimation animation) {

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

        try {

            Fragment f = fragmentClazz.newInstance();

            if (bundle != null) {
                f.setArguments(bundle);
            }

            transaction.addToBackStack(null);
            if (animation != null) {
                transaction.setCustomAnimations(animation.getEnter(), animation.getExit(), animation.getPopEnter(), animation.getPopExit());
            } else {
                transaction.setCustomAnimations(activity.getAnimFragmentEnter(), activity.getAnimFragmentExit(), activity.getAnimFragmentPopEnter(),
                        activity.getAnimFragmentPopExit());
            }

            if (activity.getSupportFragmentManager().findFragmentById(activity.getContainerViewId()) != null) {
                transaction.hide(activity.getSupportFragmentManager().findFragmentById(activity.getContainerViewId()));
            }

            transaction.add(activity.getContainerViewId(), f, tag);
            transaction.commit();
        } catch (InstantiationException e) {
            Log.e(ActivityFragmentManager.class.getCanonicalName(), e.toString());
        } catch (IllegalAccessException e) {
            Log.e(ActivityFragmentManager.class.getCanonicalName(), e.toString());
        }
    }

    /**
     * Calls {@link #add(AbstractFragmentActivity, Class, String, Bundle, TransactionAnimation)} with a null tag.
     */
    @LinkedMethod
    public static void add(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClazz, Bundle bundle,
                           TransactionAnimation animation) {
        add(activity, fragmentClazz, null, bundle, animation);
    }

    /**
     * Calls {@link #add(AbstractFragmentActivity, Class, String, Bundle, TransactionAnimation)} with a null bundle.
     */
    @LinkedMethod
    public static void add(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClazz, String tag, TransactionAnimation animation) {
        add(activity, fragmentClazz, tag, null, animation);
    }

    /**
     * Calls {@link #add(AbstractFragmentActivity, Class, String, Bundle, TransactionAnimation)} with a null transactionAnimation.
     */
    @LinkedMethod
    public static void add(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClazz, String tag, Bundle bundle) {
        add(activity, fragmentClazz, tag, bundle, null);
    }

    /**
     * Calls {@link #add(AbstractFragmentActivity, Class, String, Bundle, TransactionAnimation)} with a null tag and a null bundle.
     */
    @LinkedMethod
    public static void add(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClazz, TransactionAnimation animation) {
        add(activity, fragmentClazz, null, null, animation);
    }

    /**
     * Calls {@link #add(AbstractFragmentActivity, Class, String, Bundle, TransactionAnimation)} with a null tag and a null transactionAnimation.
     */
    @LinkedMethod
    public static void add(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClazz, Bundle bundle) {
        add(activity, fragmentClazz, null, bundle, null);
    }

    /**
     * Calls {@link #add(AbstractFragmentActivity, Class, String, Bundle, TransactionAnimation)} with a null bundle and a null transactionAnimation.
     */
    @LinkedMethod
    public static void add(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClazz, String tag) {
        add(activity, fragmentClazz, tag, null, null);
    }

    /**
     * Calls {@link #add(AbstractFragmentActivity, Class, String, Bundle, TransactionAnimation)} with a null tag, bundle and transactionAnimation.
     */
    @LinkedMethod
    public static void add(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClazz) {
        add(activity, fragmentClazz, null, null, null);
    }

    /**
     * Replace a new fragment in a specific AbstractFragmentActivity activity.
     *
     * @param activity      The activity to be used to add the new fragment.
     * @param fragmentClazz The Fragment to be replace in the activityClass.
     * @param bundle        The Bundle with the parameters to be passed to the new Fragment.
     * @param animation     The animation to used in fragment transaction.
     */
    public static void replace(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClazz, Bundle bundle,
                               TransactionAnimation animation) {

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

        try {

            Fragment f = fragmentClazz.newInstance();

            if (bundle != null) {
                f.setArguments(bundle);
            }

            if (animation != null) {
                transaction.setCustomAnimations(animation.getEnter(), animation.getExit(), animation.getPopEnter(), animation.getPopExit());
            } else {
                transaction.setCustomAnimations(activity.getAnimFragmentEnter(), activity.getAnimFragmentExit(), activity.getAnimFragmentPopEnter(),
                        activity.getAnimFragmentPopExit());
            }

            transaction.replace(activity.getContainerViewId(), f);
            transaction.addToBackStack(null);
            transaction.commit();
        } catch (InstantiationException e) {
            Log.e(ActivityFragmentManager.class.getCanonicalName(), e.toString());
        } catch (IllegalAccessException e) {
            Log.e(ActivityFragmentManager.class.getCanonicalName(), e.toString());
        }
    }

    /**
     * Calls {@link #replace(AbstractFragmentActivity, Class, Bundle, TransactionAnimation)} with a null transactionAnimation.
     */
    @LinkedMethod
    public static void replace(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClazz, Bundle bundle) {
        replace(activity, fragmentClazz, bundle, null);
    }

    /**
     * Calls {@link #replace(AbstractFragmentActivity, Class, Bundle, TransactionAnimation)} with a null bundle and transactionAnimation.
     */
    @LinkedMethod
    public static void replace(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClazz) {
        replace(activity, fragmentClazz, null, null);
    }

    /**
     * Calls {@link #replace(AbstractFragmentActivity, Class, Bundle, TransactionAnimation)} with a null bundle.
     */
    @LinkedMethod
    public static void replace(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClazz, TransactionAnimation animation) {
        replace(activity, fragmentClazz, null, animation);
    }

    /**
     * Make the same of {@link #open(Activity, Class, Class, String, Bundle, Integer)},
     * but in the end returns the Intent and don't start the activity.
     * Example, if you want to use Intent Flags you can add.
     *
     * @param context       The context to be used to create the intent.
     * @param activityClazz The AbstractFragmentActivity class.
     * @param fragmentClazz The Fragment to be added in the activityClass.
     * @param tag           The tag to be used in the fragment transaction.
     * @param bundle        The Bundle with the parameters to be passed to the new Fragment.
     * @return The new Intent
     */
    public static Intent getIntent(Context context, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz,
                                   String tag, Bundle bundle) {

        Intent intent = new Intent(context, activityClazz);

        intent.putExtra(ActivityFragmentManager.ACTIVITY_MANAGER_FRAGMENT, fragmentClazz.getCanonicalName());
        intent.putExtra(ActivityFragmentManager.ACTIVITY_MANAGER_FRAGMENT_TAG, tag);

        if (bundle != null) {
            intent.putExtras(bundle);
        }

        return intent;
    }

    /**
     * Calls {@docRoot} {@link #getIntent(Context, Class, Class, String, Bundle)} with a null tag and null bundle.
     */
    @LinkedMethod
    public static Intent getIntent(Context context, Class<? extends AbstractFragmentActivity> activityClazz,
                                   Class<? extends Fragment> fragmentClazz) {
        return getIntent(context, activityClazz, fragmentClazz, null, null);
    }

    /**
     * Calls {@link #getIntent(Context, Class, Class, String, Bundle)} with a null bundle.
     */
    @LinkedMethod
    public static Intent getIntent(Context context, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz,
                                   String tag) {
        return getIntent(context, activityClazz, fragmentClazz, tag, null);
    }

    /**
     * Calls {@link #getIntent(Context, Class, Class, String, Bundle)} with a null tag.
     */
    @LinkedMethod
    public static Intent getIntent(Context context, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz,
                                   Bundle bundle) {
        return getIntent(context, activityClazz, fragmentClazz, null, bundle);
    }
}

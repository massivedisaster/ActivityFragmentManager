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

package com.massivedisaster.activitymanager;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.massivedisaster.activitymanager.activity.AbstractFragmentActivity;

/**
 * Activity Fragment Manager
 */
public final class ActivityFragmentManager {

    /**
     * Private constructor to prevent instantiation.
     */
    private ActivityFragmentManager() {
    }

    /**
     * Start a new {@link AddFragmentTransaction}, when you {@link ActivityTransaction#commit} will
     * added a new fragment to a specific activity.
     *
     * @param activity      The activity to be used to add the new fragment.
     * @param fragmentClass The Fragment to be injected in the activity.
     * @return The {@link AddFragmentTransaction}.
     */
    public static FragmentTransaction add(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClass) {
        return new AddFragmentTransaction(activity, fragmentClass);
    }

    /**
     * Start a new {@link ReplaceFragmentTransaction}, when you {@link ReplaceFragmentTransaction#commit} will
     * replace a fragment in the container with a new fragment.
     *
     * @param activity      The activity to be used to replace the new fragment.
     * @param fragmentClass The Fragment to be replaced in the activity.
     * @return The {@link ReplaceFragmentTransaction}.
     */
    public static FragmentTransaction replace(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClass) {
        return new ReplaceFragmentTransaction(activity, fragmentClass);
    }

    /**
     * Starts a new {@link ActivityTransaction},
     * when you {@link ActivityTransaction#commit} w'll open a new activity with the specific fragment.
     *
     * @param activity      The activity to be used to start the new activity.
     * @param activityClass The AbstractFragmentActivity class.
     * @param fragmentClass The Fragment to be injected in the activityClass.
     * @return The {@link ActivityTransaction}.
     */
    public static ActivityTransaction open(Activity activity, Class<? extends AbstractFragmentActivity> activityClass,
                                           Class<? extends Fragment> fragmentClass) {
        return new ActivityTransaction(activity, activityClass, fragmentClass);
    }

    /**
     * Starts a new {@link ActivityTransaction},
     * when you {@link ActivityTransaction#commit} w'll open a new activity with the specific fragment.
     * Use this method if you want to catch the result in the original fragment.
     *
     * @param fragment      The fragment to be used to start the new activity.
     * @param activityClass The AbstractFragmentActivity class.
     * @param fragmentClass The Fragment to be injected in the activityClass.
     * @return The {@link ActivityTransaction}.
     */
    public static ActivityTransaction open(Fragment fragment, Class<? extends AbstractFragmentActivity> activityClass,
                                           Class<? extends Fragment> fragmentClass) {
        return new ActivityTransaction(fragment, activityClass, fragmentClass);
    }
}

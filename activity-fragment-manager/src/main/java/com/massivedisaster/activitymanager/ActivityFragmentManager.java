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

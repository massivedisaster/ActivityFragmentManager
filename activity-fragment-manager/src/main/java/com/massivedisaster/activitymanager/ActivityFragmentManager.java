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
import com.massivedisaster.activitymanager.transaction.TransactionAddFragment;
import com.massivedisaster.activitymanager.transaction.TransactionOpenFragment;
import com.massivedisaster.activitymanager.transaction.TransactionReplaceFragment;

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


    public static TransactionAddFragment add(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClazz){
        return new TransactionAddFragment(activity, fragmentClazz);
    }

    public static TransactionReplaceFragment replace(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClazz){
        return new TransactionReplaceFragment(activity, fragmentClazz);
    }

    /**
     * Starts a new {@link TransactionOpenFragment}, when you commit w'll open a new activity with the specific fragment.
     *
     * @param activity      The activity to be used to start the new activity.
     * @param activityClazz The AbstractFragmentActivity class.
     * @param fragmentClazz The Fragment to be injected in the activityClass.
     * @return the Fragment Transaction.
     */
    public static TransactionOpenFragment open(Activity activity, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz){
        return new TransactionOpenFragment(activity, activityClazz, fragmentClazz);
    }

    /**
     * Starts a new {@link TransactionOpenFragment}, when you commit w'll open a new activity with the specific fragment.
     * Use this method if you want to catch the result in the original fragment.
     *
     * @param fragment      The fragment to be used to start the new activity.
     * @param activityClazz The AbstractFragmentActivity class.
     * @param fragmentClazz The Fragment to be injected in the activityClass.
     * @return the Fragment Transaction.
     */
    public static TransactionOpenFragment open(Fragment fragment, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz){
        return new TransactionOpenFragment(fragment,activityClazz, fragmentClazz);
    }
}

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

package com.massivedisaster.example.feature.splash;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.massivedisaster.activitymanager.ActivityFragmentManager;
import com.massivedisaster.adal.fragment.AbstractSplashFragment;
import com.massivedisaster.example.activity.ActivityPrimaryTheme;
import com.massivedisaster.example.activitymanager.R;
import com.massivedisaster.example.feature.home.FragmentHome;

/**
 * Fragment Splash Screen
 */
public class FragmentSplash extends AbstractSplashFragment {

    @Override
    protected void getFromBundle(Bundle bundle) {

    }

    @Override
    protected int layoutToInflate() {
        return R.layout.fragment_splash;
    }

    @Override
    protected void restoreInstanceState(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void doOnCreated() {

    }


    @Override
    protected void onSplashStarted() {
        onSplashFinish(new OnFinishSplashScreen() {
            @Override
            public void onFinish() {
                openHomeScreen(getActivity());
            }
        });
    }

    /**
     * Open the home screen
     *
     * @param activity The actual activity.
     */
    protected void openHomeScreen(Activity activity) {
        ActivityFragmentManager.open(activity, ActivityPrimaryTheme.class, FragmentHome.class).commit();
        activity.finish();
    }
}

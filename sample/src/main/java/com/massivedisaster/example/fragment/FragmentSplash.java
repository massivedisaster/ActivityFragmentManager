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

package com.massivedisaster.example.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.massivedisaster.activitymanager.ActivityFragmentManager;
import com.massivedisaster.example.activity.ActivityPrimaryTheme;
import com.massivedisaster.example.activitymanager.R;

/**
 * Fragment Splash Screen
 */
public class FragmentSplash extends Fragment {

    private static final long SPLASH_TIMEOUT = 2000;
    private Handler mHandler;
    private long mStartTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        mHandler = new Handler();
    }

    @Override
    public void onResume() {
        super.onResume();

        mStartTime = System.currentTimeMillis();

        startSplashScreen();
    }

    @Override
    public void onPause() {
        super.onPause();

        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * Start handler to open home screen
     */
    protected void startSplashScreen() {
        if (mHandler == null) {
            return;
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                openHomeScreen(getActivity());
            }
        }, getTimeout());
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

    /**
     * Gets the timeout for the splash finishes.
     *
     * @return the timeout.
     */
    private long getTimeout() {
        long diff = System.currentTimeMillis() - mStartTime;

        if (diff > SPLASH_TIMEOUT) {
            return 0;
        }

        return SPLASH_TIMEOUT - diff;
    }
}

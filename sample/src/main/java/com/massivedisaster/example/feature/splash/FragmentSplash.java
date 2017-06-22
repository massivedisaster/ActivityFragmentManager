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

package com.massivedisaster.example.feature.splash;

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
import com.massivedisaster.example.feature.home.FragmentHome;

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

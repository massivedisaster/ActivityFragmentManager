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
import android.support.annotation.Nullable;

import com.massivedisaster.activitymanager.ActivityFragmentManager;
import com.massivedisaster.adal.fragment.AbstractSplashFragment;
import com.massivedisaster.example.activity.ActivityHome;
import com.massivedisaster.example.activitymanager.R;
import com.massivedisaster.example.feature.home.FragmentHome;

/**
 * Fragment Splash Screen
 */
public class FragmentSplash extends AbstractSplashFragment {

    @Override
    protected void getFromBundle(Bundle bundle) {
        //TODO
    }

    @Override
    protected int layoutToInflate() {
        return R.layout.fragment_splash;
    }

    @Override
    protected void restoreInstanceState(@Nullable Bundle savedInstanceState) {
        //TODO
    }

    @Override
    protected void doOnCreated() {
        //TODO
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
        ActivityFragmentManager.open(activity, ActivityHome.class, FragmentHome.class).commit();
        activity.finish();
    }
}

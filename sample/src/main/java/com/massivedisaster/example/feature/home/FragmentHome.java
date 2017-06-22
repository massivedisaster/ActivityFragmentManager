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

package com.massivedisaster.example.feature.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.massivedisaster.activitymanager.ActivityFragmentManager;
import com.massivedisaster.example.activity.ActivityFullscreen;
import com.massivedisaster.example.activity.ActivityPrimaryTheme;
import com.massivedisaster.example.activitymanager.R;
import com.massivedisaster.example.feature.navigation.FragmentAddReplace;
import com.massivedisaster.example.feature.sharedelements.FragmentSharedElementsOptions;

/**
 * Fragment Home
 */
public class FragmentHome extends Fragment implements View.OnClickListener {

    private Button mBtnOpenFragmentOtherActivity,
            mBtnOpenFragmentOtherActivityFullscreen,
            mBtnOpenFragmentOtherActivityWithCustomAnimation,
            mBtnSharedElements;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        mBtnOpenFragmentOtherActivity = (Button) v.findViewById(R.id.btnOpenFragmentOtherActivity);
        mBtnOpenFragmentOtherActivityFullscreen = (Button) v.findViewById(R.id.btnOpenFragmentOtherActivityFullscreen);
        mBtnOpenFragmentOtherActivityWithCustomAnimation = (Button) v.findViewById(R.id.btnOpenFragmentOtherActivityWithCustomAnimation);
        mBtnSharedElements = (Button) v.findViewById(R.id.btnSharedElements);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtnOpenFragmentOtherActivity.setOnClickListener(this);
        mBtnOpenFragmentOtherActivityFullscreen.setOnClickListener(this);
        mBtnOpenFragmentOtherActivityWithCustomAnimation.setOnClickListener(this);
        mBtnSharedElements.setOnClickListener(this);

        getActivity().setTitle(getString(R.string.home));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOpenFragmentOtherActivity:
                ActivityFragmentManager.open(getActivity(), ActivityPrimaryTheme.class, FragmentAddReplace.class)
                        .commit();
                break;
            case R.id.btnOpenFragmentOtherActivityFullscreen:
                ActivityFragmentManager.open(getActivity(), ActivityFullscreen.class, FragmentAddReplace.class)
                        .commit();
                break;
            case R.id.btnOpenFragmentOtherActivityWithCustomAnimation:
                ActivityFragmentManager.open(getActivity(), ActivityPrimaryTheme.class, FragmentAddReplace.class)
                        .commit();
                break;
            case R.id.btnSharedElements:
                openSharedElements();
                break;
            default:
                break;
        }
    }

    /**
     * Open Shared Elements Fragment
     */
    private void openSharedElements() {
        ActivityFragmentManager.open(getActivity(), ActivityPrimaryTheme.class, FragmentSharedElementsOptions.class)
                .commit();
    }
}

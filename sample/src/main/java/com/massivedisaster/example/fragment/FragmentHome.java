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

/**
 * Fragment Home
 */
public class FragmentHome extends Fragment implements View.OnClickListener {

    private Button mBtnOpenFragmentOtherActivity,
            mBtnOpenFragmentOtherActivityFullscreen,
            mBtnOpenFragmentOtherActivityWithCustomAnimation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        mBtnOpenFragmentOtherActivity = (Button) v.findViewById(R.id.btnOpenFragmentOtherActivity);
        mBtnOpenFragmentOtherActivityFullscreen = (Button) v.findViewById(R.id.btnOpenFragmentOtherActivityFullscreen);
        mBtnOpenFragmentOtherActivityWithCustomAnimation = (Button) v.findViewById(R.id.btnOpenFragmentOtherActivityWithCustomAnimation);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtnOpenFragmentOtherActivity.setOnClickListener(this);
        mBtnOpenFragmentOtherActivityFullscreen.setOnClickListener(this);
        mBtnOpenFragmentOtherActivityWithCustomAnimation.setOnClickListener(this);

        getActivity().setTitle(getString(R.string.home));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOpenFragmentOtherActivity:
                ActivityFragmentManager.open(getActivity(), ActivityPrimaryTheme.class, FragmentAddReplace.class);
                break;
            case R.id.btnOpenFragmentOtherActivityFullscreen:
                ActivityFragmentManager.open(getActivity(), ActivityFullscreen.class, FragmentAddReplace.class);
                break;
            case R.id.btnOpenFragmentOtherActivityWithCustomAnimation:
                ActivityFragmentManager.open(getActivity(), ActivityPrimaryTheme.class, FragmentAddReplace.class);
                break;
            default:
                break;
        }
    }
}

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

package com.massivedisaster.example.feature.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.massivedisaster.activitymanager.ActivityFragmentManager;
import com.massivedisaster.adal.fragment.AbstractBaseFragment;
import com.massivedisaster.example.activity.ActivityFullscreen;
import com.massivedisaster.example.activity.ActivityToolbar;
import com.massivedisaster.example.activitymanager.R;
import com.massivedisaster.example.feature.navigation.FragmentAddReplace;
import com.massivedisaster.example.feature.sharedelements.FragmentSharedElementsOptions;

import static com.massivedisaster.activitymanager.ActivityFragmentManager.open;

/**
 * Fragment Home
 */
public class FragmentHome extends AbstractBaseFragment implements View.OnClickListener {

    @Override
    protected int layoutToInflate() {
        return R.layout.fragment_home;
    }

    @Override
    protected void getFromBundle(Bundle bundle) {

    }

    @Override
    protected void restoreInstanceState(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void doOnCreated() {
        getActivity().setTitle(getString(R.string.home));

        Button btnSharedElements = findViewById(R.id.btnSharedElements);
        Button btnOpenFragmentOtherActivity = findViewById(R.id.btnOpenFragmentOtherActivity);
        Button btnOpenFragmentOtherActivityFullscreen = findViewById(R.id.btnOpenFragmentOtherActivityFullscreen);

        btnOpenFragmentOtherActivity.setOnClickListener(this);
        btnOpenFragmentOtherActivityFullscreen.setOnClickListener(this);
        btnSharedElements.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOpenFragmentOtherActivity:
                open(getActivity(), ActivityToolbar.class, FragmentAddReplace.class)
                        .commit();
                break;
            case R.id.btnOpenFragmentOtherActivityFullscreen:
                open(getActivity(), ActivityFullscreen.class, FragmentAddReplace.class)
                        .commit();
                break;
            case R.id.btnSharedElements:
                ActivityFragmentManager.open(getActivity(), ActivityToolbar.class, FragmentSharedElementsOptions.class)
                        .commit();
                break;
            default:
                break;
        }
    }
}

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

package com.massivedisaster.example.feature.sharedelements;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.massivedisaster.activitymanager.ActivityFragmentManager;
import com.massivedisaster.activitymanager.activity.AbstractFragmentActivity;
import com.massivedisaster.example.activity.ActivityPrimaryTheme;
import com.massivedisaster.example.activitymanager.R;

/**
 * Fragment Shared Elements Options
 */
public class FragmentSharedElementsOptions extends Fragment implements View.OnClickListener {

    private Button mBtnAddFragment, mBtnOpenFragment, mBtnReplaceFragment;
    private ImageView mImgSharedElement;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shared_elements_options, container, false);

        mBtnAddFragment = (Button) v.findViewById(R.id.btnAddFragment);
        mBtnOpenFragment = (Button) v.findViewById(R.id.btnOpenFragment);
        mBtnReplaceFragment = (Button) v.findViewById(R.id.btnReplaceFragment);

        mImgSharedElement = (ImageView) v.findViewById(R.id.imgSharedElement);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtnAddFragment.setOnClickListener(this);
        mBtnReplaceFragment.setOnClickListener(this);
        mBtnOpenFragment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddFragment:
                add();
                break;
            case R.id.btnReplaceFragment:
                replace();
                break;
            case R.id.btnOpenFragment:
                open();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Add a new fragment, the transaction dont work here.
     */
    private void add() {
        ActivityFragmentManager.add((AbstractFragmentActivity) getActivity(), FragmentSharedElement.class)
                .addSharedElement(mImgSharedElement, ViewCompat.getTransitionName(mImgSharedElement))
                .commit();
    }

    /**
     * Replace the fragment
     */
    private void replace() {
        ActivityFragmentManager.replace((AbstractFragmentActivity) getActivity(), FragmentSharedElement.class)
                .addSharedElement(mImgSharedElement, ViewCompat.getTransitionName(mImgSharedElement))
                .commit();
    }

    /**
     * Open a new fragment on a new activity
     */
    private void open() {
        ActivityFragmentManager.open(getActivity(), ActivityPrimaryTheme.class, FragmentSharedElement.class)
                .addSharedElement(getActivity().findViewById(R.id.imgSharedElement),
                        ViewCompat.getTransitionName(getActivity().findViewById(R.id.imgSharedElement)))
                .commit();
    }
}

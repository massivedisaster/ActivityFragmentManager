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

package com.massivedisaster.example.feature.navigation;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.massivedisaster.activitymanager.ActivityFragmentManager;
import com.massivedisaster.activitymanager.activity.AbstractFragmentActivity;
import com.massivedisaster.activitymanager.animation.TransactionAnimation;
import com.massivedisaster.example.activitymanager.R;

/**
 * Fragment Add Replace
 * Fragment with some features to add and remove more fragments.
 */
public class FragmentAddReplace extends LifecycleFragment implements View.OnClickListener {

    private static final String VALUE = "value";

    private Button mBtnAddFragment, mBtnAddFragmentWithAnimation, mBtnReplaceFragment;
    private TextView mTxtNumberOfFragments;
    private EditText mEdtValue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add, container, false);

        mBtnAddFragment = (Button) v.findViewById(R.id.btnAddFragment);
        mBtnReplaceFragment = (Button) v.findViewById(R.id.btnReplaceFragment);
        mBtnAddFragmentWithAnimation = (Button) v.findViewById(R.id.btnAddFragmentWithAnimation);
        mTxtNumberOfFragments = (TextView) v.findViewById(R.id.txtNumberOfFragments);
        mEdtValue = (EditText) v.findViewById(R.id.edtValue);

        if (savedInstanceState != null && savedInstanceState.containsKey(VALUE)) {
            mEdtValue.setText(savedInstanceState.getString(VALUE));
        }

        Log.d("AFM", "OnCreated Called");

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtnAddFragment.setOnClickListener(this);
        mBtnReplaceFragment.setOnClickListener(this);
        mBtnAddFragmentWithAnimation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddFragment:
                ActivityFragmentManager.add((AbstractFragmentActivity) getActivity(), FragmentAddReplace.class)
                        .commit();
                break;
            case R.id.btnAddFragmentWithAnimation:
                ActivityFragmentManager.add((AbstractFragmentActivity) getActivity(), FragmentAddReplace.class)
                        .setTransactionAnimation(new TransactionAnimation() {
                            @Override
                            public int getAnimationEnter() {
                                return R.anim.enter_from_right;
                            }

                            @Override
                            public int getAnimationExit() {
                                return R.anim.exit_from_left;
                            }

                            @Override
                            public int getAnimationPopEnter() {
                                return R.anim.pop_enter;
                            }

                            @Override
                            public int getAnimationPopExit() {
                                return R.anim.pop_exit;
                            }
                        }).commit();
                break;
            case R.id.btnReplaceFragment:
                ActivityFragmentManager.replace((AbstractFragmentActivity) getActivity(), FragmentAddReplace.class)
                        .commit();
                break;
            default:
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        // Refresh value when fragment changes to visible
        if (!hidden) {
            mTxtNumberOfFragments.setText(getString(R.string.number_fragment_in_this_activity,
                    getActivity().getSupportFragmentManager().getBackStackEntryCount()));
            Log.d("AFM", "Number:" + getActivity().getSupportFragmentManager().getBackStackEntryCount());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mTxtNumberOfFragments.setText(getString(R.string.number_fragment_in_this_activity,
                getActivity().getSupportFragmentManager().getBackStackEntryCount()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(VALUE, mEdtValue.getText().toString());
    }
}

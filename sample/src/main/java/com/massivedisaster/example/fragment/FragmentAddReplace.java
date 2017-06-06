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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.massivedisaster.activitymanager.activity.AbstractFragmentActivity;
import com.massivedisaster.activitymanager.ActivityFragmentManager;
import com.massivedisaster.activitymanager.animation.TransactionAnimation;
import com.massivedisaster.example.activitymanager.R;

/**
 * Fragment Add Replace
 * Fragment with some features to add and remove more fragments.
 */
public class FragmentAddReplace extends Fragment implements View.OnClickListener {

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
                        .addTransactionAnimation(new TransactionAnimation() {
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

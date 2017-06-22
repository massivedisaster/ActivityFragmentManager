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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.massivedisaster.activitymanager.ActivityFragmentManager;
import com.massivedisaster.activitymanager.activity.AbstractFragmentActivity;
import com.massivedisaster.activitymanager.animation.TransactionAnimation;
import com.massivedisaster.adal.fragment.AbstractBaseFragment;
import com.massivedisaster.example.activitymanager.R;

/**
 * Fragment Add Replace
 * Fragment with some features to add and remove more fragments.
 */
public class FragmentAddReplace extends AbstractBaseFragment implements View.OnClickListener {

    private static final String VALUE = "value";
    private String mValue;

    private TextView mTxtNumberOfFragments;
    private EditText mEdtValue;

    @Override
    protected int layoutToInflate() {
        return R.layout.fragment_add;
    }

    @Override
    protected void getFromBundle(Bundle bundle) {

    }

    @Override
    protected void restoreInstanceState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(VALUE)) {
            mValue = savedInstanceState.getString(VALUE);
        }
    }

    @Override
    protected void doOnCreated() {
        Button btnAddFragment = findViewById(R.id.btnAddFragment);
        Button btnReplaceFragment = findViewById(R.id.btnReplaceFragment);
        Button btnAddFragmentWithAnimation = findViewById(R.id.btnAddFragmentWithAnimation);
        mTxtNumberOfFragments = findViewById(R.id.txtNumberOfFragments);
        mEdtValue = findViewById(R.id.edtValue);

        btnAddFragment.setOnClickListener(this);
        btnReplaceFragment.setOnClickListener(this);
        btnAddFragmentWithAnimation.setOnClickListener(this);

        mEdtValue.setText(mValue);

        mTxtNumberOfFragments.setText(getString(R.string.number_fragment_in_this_activity,
                getActivity().getSupportFragmentManager().getBackStackEntryCount()));
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(VALUE, mEdtValue.getText().toString());
    }
}

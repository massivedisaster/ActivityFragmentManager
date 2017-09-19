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

package com.massivedisaster.activitymanager;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;

import com.massivedisaster.activitymanager.activity.AbstractFragmentActivity;
import com.massivedisaster.activitymanager.animation.TransactionAnimation;

/**
 * Abstract transaction
 */
public abstract class FragmentTransaction implements ITransaction<FragmentTransaction> {

    protected AbstractFragmentActivity mActivity;
    protected Fragment mFragment;
    protected android.support.v4.app.FragmentTransaction mFrgTransaction;
    protected String mTag;
    protected TransactionAnimation mTransactionAnimation;

    /**
     * FragmentTransaction constructor, created to be used by an activity.
     *
     * @param activity      The activity to be used to add the new fragment.
     * @param fragmentClass The Fragment to be injected in the activityClass.
     */
    @SuppressLint("CommitTransaction")
    FragmentTransaction(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClass) {
        mActivity = activity;

        mFrgTransaction = mActivity.getSupportFragmentManager().beginTransaction();

        try {
            mFragment = fragmentClass.newInstance();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mFragment.setSharedElementEnterTransition(TransitionInflater.from(mActivity).inflateTransition(android.R.transition.move));
                mFragment.setSharedElementReturnTransition(TransitionInflater.from(mActivity).inflateTransition(android.R.transition.move));
            }

        } catch (InstantiationException e) {
            Log.e(ActivityFragmentManager.class.getCanonicalName(), e.toString());
        } catch (IllegalAccessException e) {
            Log.e(ActivityFragmentManager.class.getCanonicalName(), e.toString());
        }
    }

    @Override
    public FragmentTransaction setTag(String tag) {
        mTag = tag;
        return this;
    }

    @Override
    public FragmentTransaction setBundle(Bundle bundle) {
        if (bundle != null) {
            mFragment.setArguments(bundle);
        }
        return this;
    }

    @Override
    public FragmentTransaction addSharedElement(View view, String transactionName) {
        mFrgTransaction.addSharedElement(view, transactionName);
        return this;
    }

    /**
     * Add the new view to do an animated in the transaction.
     *
     * @param name An optional name for this back stack state, or null.
     * @return Return the Transaction instance.
     */
    public FragmentTransaction addToBackStack(String name) {
        mFrgTransaction.addToBackStack(name);
        return this;
    }

    /**
     * Retrieve the fragment transaction.
     *
     * @return The fragment transaction.
     */
    public android.support.v4.app.FragmentTransaction getFragmentTransaction() {
        return mFrgTransaction;
    }

    /**
     * Retrieve the fragment created.
     *
     * @return The fragment instance.
     */
    public Fragment getFragment() {
        return mFragment;
    }

    /**
     * Set the transaction animation to be passed to the new Fragment.
     *
     * @param transactionAnimation The animation to used in fragment transaction.
     * @return Return the Transaction instance.
     */
    public FragmentTransaction setTransactionAnimation(TransactionAnimation transactionAnimation) {
        mTransactionAnimation = transactionAnimation;

        return this;
    }

}

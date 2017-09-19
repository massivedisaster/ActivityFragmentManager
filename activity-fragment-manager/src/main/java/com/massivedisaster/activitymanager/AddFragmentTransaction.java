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

import android.support.v4.app.Fragment;

import com.massivedisaster.activitymanager.activity.AbstractFragmentActivity;

/**
 * AddFragmentTransaction
 * This class implements the {@link ITransaction#commit()} with the add fragment logic.
 */
class AddFragmentTransaction extends FragmentTransaction {

    /**
     * ActivityTransaction constructor, created to be used by an activity.
     *
     * @param activity      The activity to be used to add the new fragment.
     * @param fragmentClass The Fragment to be injected in the activityClass.
     */
    AddFragmentTransaction(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClass) {
        super(activity, fragmentClass);
    }

    @Override
    public void commit() {
        // Apply the default activity animation if the FragmentTransaction is null.
        if (mTransactionAnimation == null) {
            mTransactionAnimation = mActivity;
        }

        // Set the custom transaction animation.
        mFrgTransaction.setCustomAnimations(mTransactionAnimation.getAnimationEnter(), mTransactionAnimation.getAnimationExit(),
                mTransactionAnimation.getAnimationPopEnter(),
                mTransactionAnimation.getAnimationPopExit());

        // Add the new fragment to the stack.
        mFrgTransaction.add(mActivity.getContainerViewId(), mFragment, mTag);
        // Hide the first fragment in the container.
        if (mActivity.getSupportFragmentManager().findFragmentById(mActivity.getContainerViewId()) != null) {
            mFrgTransaction.hide(mActivity.getSupportFragmentManager().findFragmentById(mActivity.getContainerViewId()));
        }

        mFrgTransaction.commit();
    }
}

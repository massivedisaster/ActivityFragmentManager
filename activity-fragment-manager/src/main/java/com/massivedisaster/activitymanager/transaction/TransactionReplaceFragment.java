package com.massivedisaster.activitymanager.transaction;

import android.support.v4.app.Fragment;

import com.massivedisaster.activitymanager.activity.AbstractFragmentActivity;

/**
 *
 */
public class TransactionReplaceFragment extends AbstractFragmentTransaction {

    public TransactionReplaceFragment(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClass) {
        super(activity, fragmentClass);
    }

    @Override
    public void commit() {
        // Hide the first fragment in the container.
        if (mActivity.getSupportFragmentManager().findFragmentById(mActivity.getContainerViewId()) != null) {
            mFrgTransaction.hide(mActivity.getSupportFragmentManager().findFragmentById(mActivity.getContainerViewId()));
        }

        // Add the new fragment to the stack.
        mFrgTransaction.replace(mActivity.getContainerViewId(), mFragment, mTag);
        mFrgTransaction.commit();
    }
}

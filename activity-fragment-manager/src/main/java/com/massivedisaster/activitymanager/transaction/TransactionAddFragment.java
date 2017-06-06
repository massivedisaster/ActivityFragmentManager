package com.massivedisaster.activitymanager.transaction;

import android.support.v4.app.Fragment;

import com.massivedisaster.activitymanager.activity.AbstractFragmentActivity;

public class TransactionAddFragment extends AbstractFragmentTransaction {

    public TransactionAddFragment(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClass) {
        super(activity, fragmentClass);
    }

    @Override
    public void commit() {
        // Hide the first fragment in the container.
        if (mActivity.getSupportFragmentManager().findFragmentById(mActivity.getContainerViewId()) != null) {
            mFrgTransaction.hide(mActivity.getSupportFragmentManager().findFragmentById(mActivity.getContainerViewId()));
        }

        // Add the new fragment to the stack.
        mFrgTransaction.add(mActivity.getContainerViewId(), mFragment, mTag);
        mFrgTransaction.commit();
    }
}

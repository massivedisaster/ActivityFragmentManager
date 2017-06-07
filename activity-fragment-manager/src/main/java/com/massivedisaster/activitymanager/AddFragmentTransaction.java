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
        // Hide the first fragment in the container.
        if (mActivity.getSupportFragmentManager().findFragmentById(mActivity.getContainerViewId()) != null) {
            mFrgTransaction.hide(mActivity.getSupportFragmentManager().findFragmentById(mActivity.getContainerViewId()));
        }

        // Add the new fragment to the stack.
        mFrgTransaction.add(mActivity.getContainerViewId(), mFragment, mTag);
        mFrgTransaction.commit();
    }
}

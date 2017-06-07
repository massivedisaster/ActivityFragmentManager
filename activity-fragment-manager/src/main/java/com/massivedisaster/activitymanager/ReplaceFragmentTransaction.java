package com.massivedisaster.activitymanager;

import android.support.v4.app.Fragment;

import com.massivedisaster.activitymanager.activity.AbstractFragmentActivity;

/**
 * ReplaceFragmentTransaction
 * This class implements the {@link ITransaction#commit()} with the replace fragment logic.
 */
class ReplaceFragmentTransaction extends FragmentTransaction {

    /**
     * ActivityTransaction constructor, created to be used by an activity.
     *
     * @param activity      The activity to be used to add the new fragment.
     * @param fragmentClass The Fragment to be injected in the activityClass.
     */
    ReplaceFragmentTransaction(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClass) {
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

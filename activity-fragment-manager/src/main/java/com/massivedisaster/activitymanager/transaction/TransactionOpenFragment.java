package com.massivedisaster.activitymanager.transaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.massivedisaster.activitymanager.activity.AbstractFragmentActivity;
import com.massivedisaster.activitymanager.ActivityFragmentManager;

public class TransactionOpenFragment implements ICommitTransaction {

    private Activity mActivityBase;
    private Fragment mFragment;
    private Class<? extends AbstractFragmentActivity> mAbstractBaseActivity;
    private Integer mRequestCode;
    private Intent mIntent;

    public TransactionOpenFragment(Activity activity, Class<? extends AbstractFragmentActivity> abstractBaseActivity, Class<? extends Fragment> fragmentClass) {
        mActivityBase = activity;
        mAbstractBaseActivity = abstractBaseActivity;

        mIntent = new Intent(mActivityBase, mAbstractBaseActivity);
        mIntent.putExtra(ActivityFragmentManager.ACTIVITY_MANAGER_FRAGMENT, fragmentClass.getCanonicalName());
    }

    public TransactionOpenFragment(Fragment fragment, Class<? extends AbstractFragmentActivity> abstractBaseActivity, Class<? extends Fragment> fragmentClass) {
        mFragment = fragment;
        mAbstractBaseActivity = abstractBaseActivity;

        mIntent = new Intent(mFragment.getContext(), mAbstractBaseActivity);
        mIntent.putExtra(ActivityFragmentManager.ACTIVITY_MANAGER_FRAGMENT, fragmentClass.getCanonicalName());
    }

    /**
     * @param tag
     * @return The TransactionOpenFragment;
     */
    public TransactionOpenFragment addTag(String tag) {
        mIntent.putExtra(ActivityFragmentManager.ACTIVITY_MANAGER_FRAGMENT_TAG, tag);
        return this;
    }

    public TransactionOpenFragment addBundle(Bundle bundle) {
        mIntent.putExtras(bundle);
        return this;
    }

    public TransactionOpenFragment addRequestCode(int requestCode) {
        mRequestCode = requestCode;
        return this;
    }

    public Intent getIntent() {
        return mIntent;
    }

    @Override
    public void commit() {
        Intent intent = getIntent();

        if (mRequestCode == null) {
            if (mActivityBase != null) {
                mActivityBase.startActivity(intent);
            } else {
                mFragment.startActivity(intent);
            }
        } else {
            if (mActivityBase != null) {
                mActivityBase.startActivityForResult(intent, mRequestCode);
            } else {
                mFragment.startActivityForResult(intent, mRequestCode);
            }
        }
    }
}

package com.massivedisaster.activitymanager.transaction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.massivedisaster.activitymanager.activity.AbstractFragmentActivity;
import com.massivedisaster.activitymanager.ActivityFragmentManager;
import com.massivedisaster.activitymanager.animation.TransactionAnimation;

/**
 * Abstract transaction
 */
public abstract class AbstractFragmentTransaction implements ICommitTransaction {

    AbstractFragmentActivity mActivity;
    Fragment mFragment;
    FragmentTransaction mFrgTransaction;
    String mTag;

    public AbstractFragmentTransaction(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClass){
        mActivity = activity;

        mFrgTransaction = mActivity.getSupportFragmentManager().beginTransaction();

        try {
            mFragment = fragmentClass.newInstance();
            mFrgTransaction.addToBackStack(null);
        } catch (InstantiationException e) {
            Log.e(ActivityFragmentManager.class.getCanonicalName(), e.toString());
        } catch (IllegalAccessException e) {
            Log.e(ActivityFragmentManager.class.getCanonicalName(), e.toString());
        }
    }

    public AbstractFragmentTransaction addTag(String tag){
        mTag = tag;
        return this;
    }

    public AbstractFragmentTransaction addBundle(Bundle bundle){
        if (bundle != null) {
            mFragment.setArguments(bundle);
        }
        return this;
    }

    public AbstractFragmentTransaction addTransactionAnimation(TransactionAnimation transactionAnimation){
        // Apply the default activity animation if the AbstractFragmentTransaction is null.
        if (transactionAnimation == null) {
            transactionAnimation = mActivity;
        }

        // Set the custom transaction animation.
        mFrgTransaction.setCustomAnimations(transactionAnimation.getAnimationEnter(), transactionAnimation.getAnimationExit(), transactionAnimation.getAnimationPopEnter(),
                transactionAnimation.getAnimationPopExit());

        return this;
    }

}

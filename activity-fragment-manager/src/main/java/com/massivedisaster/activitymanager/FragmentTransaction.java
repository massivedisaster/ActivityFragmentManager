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
            mFrgTransaction.addToBackStack(null);


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
     * Set the transaction animation to be passed to the new Fragment.
     *
     * @param transactionAnimation The animation to used in fragment transaction.
     * @return Return the Transaction instance.
     */
    public FragmentTransaction setTransactionAnimation(TransactionAnimation transactionAnimation) {
        // Apply the default activity animation if the FragmentTransaction is null.
        if (transactionAnimation == null) {
            transactionAnimation = mActivity;
        }

        // Set the custom transaction animation.
        mFrgTransaction.setCustomAnimations(transactionAnimation.getAnimationEnter(), transactionAnimation.getAnimationExit(),
                transactionAnimation.getAnimationPopEnter(),
                transactionAnimation.getAnimationPopExit());

        return this;
    }

}

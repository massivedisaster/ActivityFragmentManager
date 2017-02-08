package com.massivedisaster.activitymanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 * Activity Manager
 * Created by jms on 21/04/16.
 */
public class ActivityFragmentManager {

    public static final String ACTIVITY_MANAGER_FRAGMENT = "activity_manager_fragment";

    /**
     * Open a new activity with a specific fragment
     */
    public static void open(Activity activity, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz, Bundle bundle, Integer requestCode) {

        Intent intent = new Intent(activity, activityClazz);

        intent.putExtra(ActivityFragmentManager.ACTIVITY_MANAGER_FRAGMENT, fragmentClazz.getCanonicalName());

        if (bundle != null) intent.putExtras(bundle);

        if (requestCode == null) {
            activity.startActivity(intent);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }

    public static void open(Activity activity, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz, Bundle bundle) {
        open(activity, activityClazz, fragmentClazz, bundle, null);
    }

    public static void open(Activity activity, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz) {
        open(activity, activityClazz, fragmentClazz, null, null);
    }

    public static void open(Activity activity, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz, Integer requestCode) {
        open(activity, activityClazz, fragmentClazz, null, requestCode);
    }

    public static void add(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClazz, Bundle b) {

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

        try {

            Fragment f = fragmentClazz.newInstance();

            if (b != null) {
                f.setArguments(b);
            }

            transaction.addToBackStack(null);
            transaction.setCustomAnimations(activity.getAnimFragmentEnter(), activity.getAnimFragmentExit(), activity.getAnimFragmentPopEnter(), activity.getAnimFragmentPopExit());

            if (activity.getSupportFragmentManager().findFragmentById(activity.getContainerViewId()) != null)
                transaction.hide(activity.getSupportFragmentManager().findFragmentById(activity.getContainerViewId()));

            transaction.add(activity.getContainerViewId(), f);
            transaction.commit();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void add(AbstractFragmentActivity activity, Class<? extends Fragment> fragmentClazz) {
        add(activity, fragmentClazz, null);
    }

    public static Intent getIntent(Context context, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz) {
        return getIntent(context, activityClazz, fragmentClazz, null);
    }

    public static Intent getIntent(Context context, Class<? extends AbstractFragmentActivity> activityClazz, Class<? extends Fragment> fragmentClazz, Bundle bundle) {

        Intent intent = new Intent(context, activityClazz);

        intent.putExtra(ActivityFragmentManager.ACTIVITY_MANAGER_FRAGMENT, fragmentClazz.getCanonicalName());

        if (bundle != null) intent.putExtras(bundle);

        return intent;
    }
}

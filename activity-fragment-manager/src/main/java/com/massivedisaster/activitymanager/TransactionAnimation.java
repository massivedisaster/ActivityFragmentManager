package com.massivedisaster.activitymanager;

import android.support.annotation.AnimRes;

public interface TransactionAnimation {

    @AnimRes
    int getEnter();

    @AnimRes
    int getExit();

    @AnimRes
    int getPopEnter();

    @AnimRes
    int getPopExit();
}

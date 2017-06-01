package com.massivedisaster.activitymanager;

import android.support.annotation.AnimRes;

/**
 * Transaction animation.
 * <p>
 * You can implement this interface and return the specific animations
 * to be applied when the fragment enters or exits the container.
 */
public interface TransactionAnimation {

    /**
     * Gets the entering animation.
     *
     * @return the animation.
     */
    @AnimRes
    int getEnter();

    /**
     * Gets the exiting animation.
     *
     * @return the animation.
     */
    @AnimRes
    int getExit();

    /**
     * Gets the pop entering animation.
     *
     * @return the animation.
     */
    @AnimRes
    int getPopEnter();

    /**
     * Gets the pop exiting animation.
     *
     * @return the animation.
     */
    @AnimRes
    int getPopExit();
}

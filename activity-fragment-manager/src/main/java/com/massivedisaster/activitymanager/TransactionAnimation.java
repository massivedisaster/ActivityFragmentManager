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
    int getAnimationEnter();

    /**
     * Gets the exiting animation.
     *
     * @return the animation.
     */
    @AnimRes
    int getAnimationExit();

    /**
     * Gets the pop entering animation.
     *
     * @return the animation.
     */
    @AnimRes
    int getAnimationPopEnter();

    /**
     * Gets the pop exiting animation.
     *
     * @return the animation.
     */
    @AnimRes
    int getAnimationPopExit();
}

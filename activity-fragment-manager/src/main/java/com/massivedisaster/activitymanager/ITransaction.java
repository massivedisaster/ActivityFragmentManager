package com.massivedisaster.activitymanager;


import android.os.Bundle;
import android.view.View;

/**
 * /**
 * Transaction Interface.
 * <p>
 * You can implement this interface and return the specific animations
 * to be applied when the fragment enters or exits the container.
 *
 * @param <T> The Transaction type to be returned.
 */
interface ITransaction<T> {

    /**
     * Set the Bundle to be passed to the new Fragment.
     *
     * @param bundle The Bundle with the parameters to be passed to the new Fragment.
     * @return Return the Transaction instance.
     */
    T setBundle(Bundle bundle);

    /**
     * Set the tag to be applied to the new Fragment.
     *
     * @param tag The tag to be used in the fragment transaction.
     * @return Return the Transaction instance.
     */
    T setTag(String tag);

    /**
     * Add the new view to do an animated in the transaction.
     *
     * @param sharedElement   A View in a disappearing Fragment to match with a View in an
     *                        appearing Fragment.
     * @param transactionName The transitionName for a View in an appearing Fragment to match to the shared
     *                        element.
     * @return Return the Transaction instance.
     */
    T addSharedElement(View sharedElement, String transactionName);


    /**
     * Commit the transaction with all the configurations.
     */
    void commit();
}

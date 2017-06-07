package com.massivedisaster.activitymanager;


import android.os.Bundle;

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
    T addBundle(Bundle bundle);

    /**
     * Set the tag to be applied to the new Fragment.
     *
     * @param tag The tag to be used in the fragment transaction.
     * @return Return the Transaction instance.
     */
    T addTag(String tag);


    /**
     * Commit the transaction with all the configurations.
     */
    void commit();
}

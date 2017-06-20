/*
 * ActivityFragmentManager - A library to help android developer working easily with activities and fragments.
 *
 * Copyright (c) 2017 ActivityFragmentManager
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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

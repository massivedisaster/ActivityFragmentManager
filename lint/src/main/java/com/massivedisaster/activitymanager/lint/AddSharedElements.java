package com.massivedisaster.activitymanager.lint;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

public class AddSharedElements extends Detector implements Detector.JavaScanner {

    private static final Implementation IMPLEMENTATION = new Implementation(
            CommitTransaction.class,
            Scope.JAVA_FILE_SCOPE);

    /**
     * Problems with shared elements in add transaction.
     */
    static final Issue ADD_SHARED_ELEMENT = Issue.create(
            "AddSharedElement", //$NON-NLS-1$
            "Shared elements don't work in add a transaction",

            "Android don't support shared elements in add transactions. If you want to use shared elements between fragments use {@code replace.}",

            Category.CORRECTNESS,
            7,
            Severity.WARNING,
            IMPLEMENTATION);
}

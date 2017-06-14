package com.massivedisaster.activitymanager.lint;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;

import java.util.Arrays;
import java.util.List;

/**
 * ActivityFragmentManager issue registry for lint warning.
 */
@SuppressWarnings("unused")
public class ActivityFragmentManagerIssueRegistry extends IssueRegistry {

    @Override
    public List<Issue> getIssues() {
        return Arrays.asList(CommitTransaction.COMMIT_FRAGMENT);
    }
}

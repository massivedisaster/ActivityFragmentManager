package com.example;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;

import java.util.Arrays;
import java.util.List;

public class ActivityFragmentManager extends IssueRegistry {

    @Override
    public List<Issue> getIssues() {
        return Arrays.asList(CallNeedsPermissionDetector.ISSUE,
                NoCorrespondingNeedsPermissionDetector.ISSUE,
                CallOnRequestPermissionsResultDetector.ISSUE);
    }
}

# ActivityFragmentManager
> A library to help android developer working easly with activities and fragments 

# Usage
First you need create an activity and extends AbstractFragmentActivity.

Open a new activity with a specific fragment inside
```java
ActivityFragmentManager.open(mActivity, PrimaryThemeActivity.class, FragmentLogin.class, bundle, requestCode);
```

Add a new fragment to an activity
```java
ActivityFragmentManager.add(mActivity, FragmentLogin.class);
```
### Useful info
- If you want to change the fragment enter/exit animations, override the methods **getAnimFragmentEnter()** and **getAnimFragmentExit()** on your activity.
- Don't forget to check the [Sample project]( https://github.com/***REMOVED***/ActivityFragmentManager/blob/master/sample/src/main/java/).

## Install

Add the dependency in the form:
```groovy
dependencies {
  compile 'com.massivedisaster:activity-fragment-manager:0.1.2'
}
```

# ActivityFragmentManager
> A library to help android developer working easly with activities and fragment 

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
## Install

Add the dependency in the form:
```groovy
dependencies {
  compile 'com.massivedisaster:activity-fragment-manager:0.1.2'
}
```

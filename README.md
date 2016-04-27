# ActivityFragmentManager
[![License](http://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://choosealicense.com/licenses/apache-2.0/)
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
- Don't forget to check the [Sample project]( https://github.com/jmspt/ActivityFragmentManager/blob/master/example/src/main/java/com/massivedisaster/example/fragment/FragmentOpenAdd.java).

## Install

Add the dependency in the form:
```groovy
dependencies {
  compile 'com.massivedisaster:activity-fragment-manager:0.1.2'
}
```

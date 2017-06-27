# Activity Fragment Manager 
[![Download](https://api.bintray.com/packages/jmspt/maven/activity-fragment-manager/images/download.svg)](https://bintray.com/jmspt/maven/activity-fragment-manager/)
[![Build Status](https://travis-ci.org/massivedisaster/ActivityFragmentManager.svg?branch=master)](https://travis-ci.org/massivedisaster/ActivityFragmentManager)
[![API](https://img.shields.io/badge/API-10%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=10)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Activity%20Fragment%20Manager-red.svg?style=flat)](https://android-arsenal.com/details/1/5916)  
A library to help android developer working easly with activities and fragments 

## Motivation

* Accelerate the process and abstract the logic of opening, adding and replacing fragments in an activity;
* Reduce the number of activities declared in the project;
* Get access to ```Activity.onBackPressed()``` inside of the fragments.
* Add animated transitions between fragments in an easy way;
* Easy way to work with shared elements;

<div align="center">
  <img src="extra/navigation.gif" width="280" alt="An animated GIF showing navigation flow" />
  <img src="extra/shared_elements.gif" width="280" alt="An animated GIF showing shared elements working" />
  <img src="extra/onbackpressed.gif" width="280" alt="An animated GIF showing onbackpressed working" />
  <br />
</div>

## Download

To use the Activity Fragment Manager, add the compile dependency with the latest version.

### Gradle

Add the Activity Fragment Manager to your `build.gradle`:
```gradle
dependencies {
    compile 'com.massivedisaster:activity-fragment-manager:0.4.1'
}
```

### Maven

In the `pom.xml` file:
```xml
<dependency>
    <groupId>com.massivedisaster</groupId>
    <artifactId>activity-fragment-manager</artifactId>
    <version>0.4.1</version>
</dependency>
```

## Usage

### 1. Create your Activity

Create a new activity and extends the ```AbstractFragmentActivity```.

```java
public class ActivityPrimaryTheme extends AbstractFragmentActivity {

    // The layout resource you want to find the FrameLayout.
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_primary;
    }

    // The FrameLayout id you want to inject the fragments.
    @Override
    protected int getContainerViewId() {
        return R.id.frmContainer;
    }

}

```

Create the layout to be used by your ```AbstractFragmentActivity```.
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/frmContainer"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```

### 2. Opening, adding or replacing fragments in your AbstractFragmentActivity.

#### Open a new AbstractFragmentActivity with a fragment.
```java
ActivityFragmentManager.open(getActivity(), ActivityPrimaryTheme.class, FragmentExample.class)
                .commit();
```

#### Add a new Fragment in the actual AbstractFragmentActivity.
```java
ActivityFragmentManager.add(getActivity(), FragmentExample.class)
                .commit();
```

#### Replace a new Fragment in the actual AbstractFragmentActivity.
```java
ActivityFragmentManager.replace((AbstractFragmentActivity) getActivity(), FragmentExample.class)
                .commit();
```

### 3. Default Fragment

You can set a default fragment in you ```AbstractFragmentActivity```.
An example, if your ```AbstractFragmentActivity``` is started by an external intent you need to define a default fragment.

```java
public class ActivityPrimaryTheme extends AbstractFragmentActivity {

    ...
    
    @Override
    protected Class<? extends Fragment> getDefaultFragment() {
        return FragmentSplash.class;
    }

}
```

### 4. Fragment Transaction Animations.
When you add or replace fragments in the old way you can set a custom animations for the transactions. So, you can set custom animation in easly way using this library.

#### Single Transaction Animation

If you want to add a single animation only for one transaction you can do this:
```java
ActivityFragmentManager.add(getActivity(), FragmentExample.class)
        .setTransactionAnimation(new TransactionAnimation() {
            @Override
            public int getAnimationEnter() {
                return R.anim.enter_from_right;
            }

            @Override
            public int getAnimationExit() {
                return R.anim.exit_from_left;
            }

            @Override
            public int getAnimationPopEnter() {
                return R.anim.pop_enter;
            }

            @Override
            public int getAnimationPopExit() {
                return R.anim.pop_exit;
            }
        })
        .commit();
```
**Attention:** This only works in transactions between fragments, i.e. ```add()``` and ```replace()```

#### Custom animation for all transactions.

If you want to add a custom animation for all transactions inside of a ```AbstractFragmentActivity``` you can override the follow methods:
```java
public class ActivityPrimaryTheme extends AbstractFragmentActivity {

    ...
    
    @Override
    public int getAnimationEnter() {
        return R.anim.enter_from_right;
    }

    @Override
    public int getAnimationExit() {
        return R.anim.exit_from_left;
    }

    @Override
    public int getAnimationPopEnter() {
        return R.anim.pop_enter;
    }

    @Override
    public int getAnimationPopExit() {
        return R.anim.pop_exit;
    }

}
```

### 5. Shared Elements
If you want to make your app beautiful you need to put some cool animation on it!  
Shared elements are introduce in API 21 and makes the transactions so great and sweet.  
So, now it's very easy to share elements between fragments or activities.
Let's take a look:

**Activity A**
```java
...
.addSharedElement(view, "sharedElement")
...
.commit();
```

**Activity B**
```java
ViewCompat.setTransitionName(view, "sharedElement");
```
or

```xml
<View
  ...
  android:transitionName="sharedElement" />
```

**Attention:** Shared elements doesn't work when you use ```add()```!  
Well if you remove the first fragment it's possible, i.e. a replace :)

### 6. Custom Intents
Sometimes you want to add more information to the ```Intent``` or set some flags. You can use the follow method to open a new ```AbstractActivityFragment```:

```java
Intent intent = ActivityFragmentManager.open(getContext(), ActivityPrimaryTheme.class, FragmentExample.class).getIntent();
intent.setFlag(Intent.FLAG_ACTIVITY_NEW_TASK
                | intent.FLAG_ACTIVITY_CLEAR_TASK);
getActivity().startActivity(intent);
``` 

### 7. Fragment#OnBackPressed
Allows to have back pressed events in `Fragments`.

```java
public class FragmentA extends Fragment implements OnBackPressedListener {

    ...

    @Override
    public boolean onBackPressed() {
      // Do what you want here! If you return true the activity will not process the OnBackPressed
    }

}
```

## Goodies

* You can pass a tag to be applied in the ```Fragment```.
* You can pass ```REQUEST_CODE``` to the ```startActivityForResult```.
* You can pass data between fragments using a ```Bundle```.
* You can get acess to the original ```FragmentTransaction```.
* You can use ```DataBinding``` in your ```AbstractFragmentActivity```, all you need is override ```initializeDataBinding()``` and bind the view!

## Sample

Sample app can be found in the [sample module](sample). 
Alternatively, you can use [dryrun](https://github.com/cesarferreira/dryrun) to run the sample.

The Sample app don't require any configuration to interact.

## Contributing
[CONTRIBUTING](CONTRIBUTING.md)

## License
[MIT LICENSE](LICENSE.md)

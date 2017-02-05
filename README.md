# react-native-sync-background

[Intelligent Job-Scheduling](https://developer.android.com/topic/performance/scheduling.html) port to React Native: Scheduling data background synchronizations that run in your JavaScript. 

Read more in the following post: *React Native using the Android SyncAdapter to sync data in the background* **(Coming soon...)**.

## Requirements
* React Native 0.36+

## Pros
Under the hood, this library uses a [SyncAdapter](https://developer.android.com/reference/android/content/AbstractThreadedSyncAdapter.html):

* Android will trigger a sync using our `syncFlexTime` to decide when is the best moment to do so (battery efficiency)
* No need to worry about internet connection
* No need to worry about the user restarting the device
* Compatible with all Android versions supported by RN (4.1+)

## Caveats
No iOS support:

* To run tasks on the background using JavaScript, this library uses [Headles JS](https://facebook.github.io/react-native/docs/headless-js-android.html) which is only currently supported on Android. You can vote for the related issue [here](https://productpains.com/post/react-native/headless-js-for-ios).

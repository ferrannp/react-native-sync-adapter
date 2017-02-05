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

App in the foreground:
* Syncs will only trigger if the app is **not** in the foreground. Check out the blog post mentioned above to read more about this and how to mitigate it.

## Getting started

```
yarn add react-native-sync-background
```

### Installation
```
react-native link react-native-sync-background
```
#### Manual Android required step
Open up the `string.xml` file of your Android project. You need to add the following (just change the content):
```xml
<string name="app_name">YourAppName</string>
<string name="rnsb_sync_account_type" translatable="false">your.android.package.name</string>
<string name="rnsb_content_authority" translatable="false">your.android.package.name.provider</string>
```

This will override the default values from the library and make them unique for your app.

### Usage
You need to register a task with a specific name and only with this specific name: `TASK_SYNC_BACKGROUND`. You can do in the same place where you register your app:

```js
AppRegistry.registerComponent('MyApp', () => MyApp);
AppRegistry.registerHeadlessTask('TASK_SYNC_BACKGROUND', () => TestTask);
```

Then, on your top most component:
```js
import SyncBackground from 'react-native-sync-background';

...

componentDidMount() {
  SyncBackground.init({
    syncInterval,
    syncFlexTime,
  });
}

...
```

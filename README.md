# react-native-sync-adapter

[![Circle CI](https://circleci.com/gh/ferrannp/react-native-sync-adapter.svg?style=shield)](https://circleci.com/gh/ferrannp/react-native-sync-adapter) [![npm version](https://badge.fury.io/js/react-native-sync-adapter.svg)](https://badge.fury.io/js/react-native-sync-adapter)

[Intelligent Job-Scheduling](https://developer.android.com/topic/performance/scheduling.html) port to React Native: Scheduling data background synchronizations that run in your JavaScript.

Read a broader introduction in the following post: [React Native and Native Modules: The Android SyncAdapter](https://blog.callstack.io/react-native-and-native-modules-the-android-syncadapter-517ddf851bf4#.qb5ed9din)

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

* To run tasks on the background using JavaScript, this library uses [Headles JS](https://facebook.github.io/react-native/docs/headless-js-android.html) which is currently supported only on Android. You can vote for the related issue [here](https://productpains.com/post/react-native/headless-js-for-ios)

## Getting started

```
yarn add react-native-sync-adapter
```

### Installation
```
react-native link react-native-sync-adapter
```
#### Manual Android required step
Open up the `string.xml` file of your Android project. You need to add the following (just change the content):
```xml
<string name="app_name">YourAppName</string>
<string name="rnsb_sync_account_type" translatable="false">your.android.package.name</string>
<string name="rnsb_content_authority" translatable="false">your.android.package.name.provider</string>
```

This will override the default values from the library and make them unique for your app.

## Usage
You need to [register a task](https://facebook.github.io/react-native/docs/headless-js-android.html#the-js-api) with a specific name and only with this specific name: `TASK_SYNC_ADAPTER`. You should do it in the same place where you register your app:

```js
AppRegistry.registerComponent('MyApp', () => MyApp);
AppRegistry.registerHeadlessTask('TASK_SYNC_ADAPTER', () => TestTask);
```

Then, on your top most component:
```js
import SyncAdapter from 'react-native-sync-adapter';

...

componentDidMount() {
  SyncAdapter.init({
    syncInterval,
    syncFlexTime,
  });
}

...
```

That is all! Some extras:

### Timeout

The default timeout for your Headless JS task is 5 minutes (300000ms). If you want to override this value, you will also need to override `strings.xml` again:

```xml
<!-- Overrides default timeout to 10 minutes -->
<string name="rnsb_default_timeout" translatable="false">600000</string>
```

### Running the task while the app is in the foreground

By default, the sync task will only run if the app is **not** in the foreground. This is one of the default [caveats](https://facebook.github.io/react-native/docs/headless-js-android.html#caveats) from HeadlessJS.
If you want to override this behavior, you can, one more time overriding `strings.xml`:

```xml
<string name="rnsb_allow_foreground">true</string>
```

### Broadcast Receiver

If you want to trigger a sync natively (e.g. responding to a broadcast receiver), you can call:

```java
SyncAdapter.syncImmediately(Context context, int syncInterval, int syncFlexTime);
```

## API

### init

Schedules background syncs within your app.

```js
Object: {
  syncInterval: number;
  syncFlexTime: number;
}
```

* `syncInterval`: The amount of time in seconds that you wish to elapse between periodic syncs
* `syncFlexTime`: The amount of flex time in seconds before `syncInterval` that you permit for the sync to take place. Must be less than `syncInterval`

A good example could be `syncInterval: 12 * 60 * 60` (12 hours) and `syncFlexTime: 0.5 * 60 * 60` (30 minutes).

Notice that `syncFlexTime` only works for Android 4.4+, for older versions, that value will be ignored and syncs will be always exact.

### syncImmediately

Invoke the sync task. Use the same values as in the [init](#init) call.

```js
Object: {
  syncInterval: number;
  syncFlexTime: number;
}
```

Be aware that for this method to work (if you call it from inside your app) you need to allow the task to [work on the foreground](https://github.com/ferrannp/react-native-sync-adapter#running-the-task-while-the-app-is-in-the-foreground).

## Running example

You can try this library running the `example` app:

```
cd example && yarn && npm start
```

Then just run:

```
react-native run-android
```

**Be careful**: The installed app will trigger a sync around every minute (so it is easy to see that is working). If you debug the app, you should be able to see the HeadlessJS ouputing: `Headless JS task was fired!` (remember not to have the app on the foreground: Unless you override this behavior). After you try it, I recommend to uninstall the app so you don't harm your device battery life.

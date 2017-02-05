# react-native-sync-background

[![Circle CI](https://circleci.com/gh/ferrannp/react-native-sync-background.svg?style=shield)](https://circleci.com/gh/ferrannp/react-native-sync-background)

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

* To run tasks on the background using JavaScript, this library uses [Headles JS](https://facebook.github.io/react-native/docs/headless-js-android.html) which is currently supported only on Android. You can vote for the related issue [here](https://productpains.com/post/react-native/headless-js-for-ios)

App in the foreground:
* Syncs will only trigger if the app is **not** in the foreground. Check out the blog post mentioned above to read more about this and how to mitigate it

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
You need to [register a task](https://facebook.github.io/react-native/docs/headless-js-android.html#the-js-api) with a specific name and only with this specific name: `TASK_SYNC_BACKGROUND`. You should do it in the same place where you register your app:

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

That is all!

### API

#### init

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

## Running example

You can try this library running the `example` app:

```
cd example && npm install
```

Then just run:

```
react-native run-android
```

**Be careful**: The installed app will trigger a sync around every minute (so it is easy to see that is working). If you debug the app, you should be able to see the HeadlessJS ouputing: `Headless JS task was fired!` (remember not to have the app on the foreground). After you try it, I recommend to uninstall the app so you don't harm your device battery life.

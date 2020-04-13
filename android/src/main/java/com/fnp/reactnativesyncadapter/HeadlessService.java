package com.fnp.reactnativesyncadapter;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;

import java.util.List;

public class HeadlessService extends HeadlessJsTaskService {

    private static final String TASK_ID = "TASK_SYNC_ADAPTER";

    @Override
    protected HeadlessJsTaskConfig getTaskConfig(Intent intent) {
        boolean allowForeground = Boolean.parseBoolean(getString(R.string.rnsb_allow_foreground));

        if(allowForeground || !isAppOnForeground(this)) {
            Bundle extras = intent.getExtras();
            WritableMap data = extras != null ? Arguments.fromBundle(extras) : Arguments.createMap();
            return new HeadlessJsTaskConfig(
                    TASK_ID,
                    data,
                    Long.valueOf(getString(R.string.rnsb_default_timeout)),
                    allowForeground);
        }

        stopSelf();
        return null;
    }

    // From https://facebook.github.io/react-native/docs/headless-js-android.html
    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        assert activityManager != null;
        List<ActivityManager.RunningAppProcessInfo> appProcesses =
                activityManager.getRunningAppProcesses();

        if (appProcesses == null) {
            return false;
        }

        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance ==
                    ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&
                    appProcess.processName.equals(packageName)) {
                return true;
            }
        }

        return false;
    }
}
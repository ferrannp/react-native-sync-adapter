package com.fnp.reactnativesyncadapter;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;

import java.util.ArrayList;
import java.util.List;

public class HeadlessService extends HeadlessJsTaskService {

    private static final String TASK_ID = "TASK_SYNC_ADAPTER";

    private final IBinder mBinder = new LocalBinder();
    private List<Callback> mCallbacks = new ArrayList<>();

    public class LocalBinder extends Binder {
        HeadlessService getService() {
            return HeadlessService.this;
        }
    }

    public interface Callback {
        void onTaskCompletion();
    }

    @Override
    public @Nullable
    IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void notifyOnTaskCompletion(Callback cb) {
        mCallbacks.add(cb);
    }

    @Override
    public void onHeadlessJsTaskFinish(int taskId) {
        super.onHeadlessJsTaskFinish(taskId);
        for (Callback cb : mCallbacks) {
            cb.onTaskCompletion();
        }
        mCallbacks.clear();
    }

    public void startHeadlessTask(Intent intent) {
        HeadlessJsTaskConfig taskConfig = getTaskConfig(intent);
        if (taskConfig != null) {
            startTask(taskConfig);
        }
    }
    @Override
    protected HeadlessJsTaskConfig getTaskConfig(Intent intent) {
        boolean allowForeground = Boolean.valueOf(getString(R.string.rnsb_allow_foreground));

        if(allowForeground || !isAppOnForeground(this)) {
            return new HeadlessJsTaskConfig(
                    TASK_ID,
                    null,
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
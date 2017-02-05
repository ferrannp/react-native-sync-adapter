package com.fnp.reactnativesyncbackground;

import android.content.Intent;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;

public class HeadlessService extends HeadlessJsTaskService {

    private static final String TASK_ID = "TASK_SYNC_BACKGROUND";

    @Override
    protected HeadlessJsTaskConfig getTaskConfig(Intent intent) {
        if(!isAppInForeground()) {
            return new HeadlessJsTaskConfig(
                    TASK_ID,
                    null,
                    300000);
        }

        stopSelf();
        return null;
    }

    /**
     * Checks if the app is currently running in the foreground
     */
    private boolean isAppInForeground() {
        final ReactInstanceManager reactInstanceManager =
                ((ReactApplication) getApplication())
                        .getReactNativeHost()
                        .getReactInstanceManager();
        ReactContext reactContext = reactInstanceManager.getCurrentReactContext();

        return reactContext != null && reactContext.getLifecycleState() == LifecycleState.RESUMED;
    }
}
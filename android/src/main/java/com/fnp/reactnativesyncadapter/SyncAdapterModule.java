package com.fnp.reactnativesyncadapter;

import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

@SuppressWarnings("unused") class SyncAdapterModule extends ReactContextBaseJavaModule {

    SyncAdapterModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @ReactMethod
    public void init(int syncInterval, int syncFlexTime) {
        SyncAdapter.getSyncAccount(getReactApplicationContext(), syncInterval, syncFlexTime);
    }

    @ReactMethod
    public void syncImmediately(int syncInterval, int syncFlexTime) {
        boolean allowForeground = Boolean.valueOf(getReactApplicationContext().getString(R.string.rnsb_allow_foreground));

        if (!allowForeground && HeadlessService.isAppOnForeground(getReactApplicationContext())) {
            if (getCurrentActivity() != null) {
                Toast.makeText(
                        getCurrentActivity(),
                        "This sync task has not been configured to run on the foreground!",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            return;
        }

        SyncAdapter.syncImmediately(getReactApplicationContext(), syncInterval, syncFlexTime);
    }

    @Override
    public String getName() {
        return "SyncAdapter";
    }
}

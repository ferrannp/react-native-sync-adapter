package com.fnp.reactnativesyncadapter;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

@SuppressWarnings("unused")
class SyncAdapterModule extends ReactContextBaseJavaModule {

    SyncAdapterModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @ReactMethod
    public void init(int syncInterval, int syncFlexTime) {
        SyncAdapter.getSyncAccount(getReactApplicationContext(), syncInterval, syncFlexTime);
    }

    @ReactMethod
    public void syncImmediately() {
        // TODO implement this
        // SyncAdapter.syncImmediately(getReactApplicationContext(), syncInterval, syncFlexTime);
    }

    @Override
    public String getName() {
        return "SyncAdapter";
    }
}

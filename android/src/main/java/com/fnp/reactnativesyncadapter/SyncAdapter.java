package com.fnp.reactnativesyncadapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;

class SyncAdapter extends AbstractThreadedSyncAdapter {

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Intent service = new Intent(getContext(), HeadlessService.class);
        getContext().startService(service);
    }

    /**
     * Helper method to have the sync adapter sync immediately
     */
    public static void syncImmediately(Context context, int syncInterval, int syncFlexTime) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context, syncInterval, syncFlexTime),
                context.getString(R.string.rnsb_content_authority), bundle);
    }

    static Account getSyncAccount(Context context, int syncInterval, int syncFlexTime) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(context.getString(R.string.app_name),
                context.getString(R.string.rnsb_sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if (null == accountManager.getPassword(newAccount)) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
            onAccountCreated(newAccount, context, syncInterval, syncFlexTime);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context, int syncInterval, int syncFlexTime) {
        // Since we've created an account
        SyncAdapter.configurePeriodicSync(context, syncInterval, syncFlexTime);

        // Without calling setSyncAutomatically, our periodic sync will not be enabled
        ContentResolver.setSyncAutomatically(newAccount,
                context.getString(R.string.rnsb_content_authority), true);
    }

    /**
     * Based on https://gist.github.com/udacityandroid/7230489fb8cb3f46afee
     */
    private static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context, syncInterval, flexTime);
        String authority = context.getString(R.string.rnsb_content_authority);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // We can enable inexact timers in our periodic sync (better for batter life)
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

}

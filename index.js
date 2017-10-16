/* @flow */

import { NativeModules } from 'react-native';

const SyncAdapter = NativeModules.SyncAdapter;

type Init = {
  syncInterval: number,
  syncFlexTime: number,
};

export default {
  init: ({ syncInterval, syncFlexTime }: Init) => {
    if (syncFlexTime > syncInterval) {
      throw new Error(
        'Specified syncInterval must be greater than the specified syncFlexTime.'
      );
    }
    SyncAdapter.init(syncInterval, syncFlexTime);
  },

  syncImmediately: () => {
    SyncAdapter.syncImmediately();
  },
};

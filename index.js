/* @flow */

import { NativeModules } from 'react-native';

const SyncAdapter = NativeModules.SyncAdapter;

type Init = {
  syncInterval: number,
  syncFlexTime: number,
};

const _checkIntervals = (syncInterval: number, syncFlexTime: number) => {
  if (syncFlexTime > syncInterval) {
    throw new Error(
      'Specified syncInterval must be greater than the specified syncFlexTime.'
    );
  }
};

export default {
  init: ({ syncInterval, syncFlexTime }: Init) => {
    _checkIntervals(syncInterval, syncFlexTime);
    SyncAdapter.init(syncInterval, syncFlexTime);
  },

  syncImmediately: ({ syncInterval, syncFlexTime }: Init) => {
    _checkIntervals(syncInterval, syncFlexTime);
    SyncAdapter.syncImmediately(syncInterval, syncFlexTime);
  },
};

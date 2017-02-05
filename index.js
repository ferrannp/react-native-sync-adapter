/* @flow */

import { NativeModules } from 'react-native';

const SyncBackground = NativeModules.SyncBackground;

type Init = {
  syncInterval: number;
  syncFlexTime: number;
}

export default {
  init: ({ syncInterval, syncFlexTime }: Init) => {
    if (syncFlexTime > syncInterval) {
      throw new Error('Specified syncInterval must be greater than the specified syncFlexTime.');
    }
    SyncBackground.init(syncInterval, syncFlexTime);
  },

  syncImmediately: () => {
    SyncBackground.syncImmediately();
  },
};

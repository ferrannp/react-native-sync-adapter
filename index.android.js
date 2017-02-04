/* @flow */

import React, { Component } from 'react';
import {
  AppRegistry,
  Button,
  StyleSheet,
  Text,
  View
} from 'react-native';

import SyncBackground from './index.js';
import TestTask from './TestTask';

const syncInterval = 2 * 60 * 60; // 2h
const syncFlexTime = 0.25 * 60 * 60; // 15m

export default class ReactNativeSyncData extends Component {

  componentDidMount() {
    SyncBackground.init(syncInterval, syncFlexTime);
  }

  _onSyncPress = () => {
    SyncBackground.syncImmediately();
  };

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          SyncAdapter example
        </Text>
        <Button
          onPress={this._onSyncPress}
          title="Sync now"
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  welcome: {
    marginBottom: 24,
  },
});

AppRegistry.registerComponent('ReactNativeSyncData', () => ReactNativeSyncData);
AppRegistry.registerHeadlessTask('TASK_SYNC_BACKGROUND', () => TestTask);

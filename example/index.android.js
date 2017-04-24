/* @flow */

import React, { Component } from 'react';
import {
  AppRegistry,
  // Button,
  StyleSheet,
  Text,
  View,
} from 'react-native';
import SyncAdapter from 'react-native-sync-adapter';

import TestTask from './TestTask';

const syncInterval = 60; // 1 minute
const syncFlexTime = 15; // 15 seconds

export default class SyncAdapterExample extends Component {

  componentDidMount() {
    SyncAdapter.init({
      syncInterval,
      syncFlexTime,
    });
  }

  // _onSyncPress = () => {
  //   SyncAdapter.syncImmediately();
  // };

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.title}>
          React Native Sync Adapter
        </Text>
        <Text style={styles.subTitle}>
          Example is running!
        </Text>
        {/* <Button*/}
        {/* onPress={this._onSyncPress}*/}
        {/* title="Sync now"*/}
        {/* />*/}
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
  title: {
    fontSize: 20,
    color: 'rgba(0, 0, 0, 0.87)',
    textAlign: 'center',
    marginBottom: 12,
  },
  subTitle: {
    color: 'rgba(0, 0, 0, 0.54)',
    textAlign: 'center',
    marginBottom: 16,
  },
});

AppRegistry.registerComponent('SyncAdapterExample', () => SyncAdapterExample);
AppRegistry.registerHeadlessTask('TASK_SYNC_ADAPTER', () => TestTask);

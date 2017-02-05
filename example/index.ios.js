/* @flow */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
} from 'react-native';

export default class SyncBackgroundExample extends Component {

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.title}>
          React Native Sync Background
        </Text>
        <Text style={styles.subTitle}>
          This library is not available for iOS (neither HeadlessJS). PRs are welcome!
        </Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    marginHorizontal: 16,
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

AppRegistry.registerComponent('SyncBackgroundExample', () => SyncBackgroundExample);

/* @flow */

module.exports = (_, defaults) => ({
  entry: './index.android.js',
  output: {
    ...defaults.output,
    filename: 'index.android.bundle',
  },
});

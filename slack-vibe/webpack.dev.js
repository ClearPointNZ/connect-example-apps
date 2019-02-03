/**
 * Created by laurence-ho on 21/09/17.
 */
const merge = require('webpack-merge');
const common = require('./webpack.common.js');
const HotModuleReplacementPlugin = require('webpack/lib/HotModuleReplacementPlugin');

module.exports = merge(common, {
  devtool: 'source-map',
  devServer: {
    contentBase: './dist',
    historyApiFallback: true,
    hot: true,
    inline: true
  },
  plugins: [
    new HotModuleReplacementPlugin()
  ]
});

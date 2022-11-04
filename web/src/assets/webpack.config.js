
const path = require('path');
const MiniCssExtractPlugin = require("mini-css-extract-plugin");

// CSS
module.exports = {
  //エントリポイント
  entry: './src/index.js',
  //出力先
  output: {
    filename: 'app.js',
    path: path.resolve(__dirname, '../static/dist/css'),
  },
  module: {
    rules: [
      // sass, scss, css
      {
        //拡張子 .scss、.sass、css を対象
        test: /\.(scss|sass|css)$/i,
        // 使用するローダーの指定（後ろから順番に適用される）
        use: [ MiniCssExtractPlugin.loader, 'css-loader', 'sass-loader' ],
      },
    ],
  },
  //プラグインの設定
  plugins: [
    new MiniCssExtractPlugin({
      // 抽出する CSS のファイル名
      filename: 'style.css',
    }),
  ],
  //source-map タイプのソースマップを出力
  devtool: 'source-map',
  // node_modules を監視（watch）対象から除外
  watchOptions: {
    ignored: /node_modules/  //正規表現で指定
  },
};

// JavaScript
module.exports = {
  entry: {
    top: './src/js/top.js',
  },
  output: {
    filename: '[name].bundle.js',
    path: path.resolve(__dirname, '../static/dist/js'),
  },
  resolve: {
    alias: {
      vue$: 'vue/dist/vue.esm-bundler.js'
    }
  }
}
package dataseeder.loader;

import common.AppConfig;

/**
 * データベーステーブルへのデータセットアップ抽象クラス
 * @author Yasumasa
 */
abstract class AbstractBuilder {
  /**
   * ファイルなどからデータを読み込みImportBeansデータを作成する
  */
  abstract void prepareImportBeansFromFile();
  
  /**
   * データセットアップ関数
   */
  abstract void buildUpData();
  
}

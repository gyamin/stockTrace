package dataseeder;

import common.AppLogging;
import dataseeder.loader.IssuesBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * データ作成処理実行クラス
 * @author Yasumasa
 */
public class ExecuteDataSeed {
  /**
   * データベースへのデータ登録処理実行クラス
   * @param args コマンドパラメータ
   */
  public static void main(String[] args) {
    // ロギング共通設定
    AppLogging.setLoggingConfig();

    // 処理開始
    Logger.getLogger(AppLogging.LOG_NAME).log(Level.INFO, "START: データベースデータ登録処理");
    // issuesテーブルにデータを登録
    IssuesBuilder issues = new IssuesBuilder();
    issues.buildUpData();
    Logger.getLogger(AppLogging.LOG_NAME).log(Level.INFO, "FINISH: データベースデータ登録処理");
  }
}

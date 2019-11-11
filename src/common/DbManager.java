package common;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * データベース接続管理クラス
 * @author nogami
 */
public class DbManager {
  /**
   * PostgreSQLサーバへのコネクションを作成し、コネクションをリターンする
   * @return conn データベースコネクション
   */
    public static Connection getConnection() {
      try {
        Class.forName("org.postgresql.Driver");
      } catch (ClassNotFoundException ex) {
        Logger.getLogger(AppLogging.LOG_NAME).log(Level.SEVERE, null, ex);
      }

      // jdbc urlを取得
      String url = getUrl();
      // ユーザ、パスワードのプロパティ設定
      Properties appProperties = AppConfig.getApplicationProperties();

      Properties props = new Properties();
      props.setProperty("user",appProperties.getProperty("DB_USER"));
      props.setProperty("password",appProperties.getProperty("DB_PASSWD"));

      Connection conn = null;

      try {
          conn = DriverManager.getConnection(url, props);
      } catch (SQLException ex) {
          Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
      }
        return conn;
    }

  /**
   * アプリケーション設定からデータベース接続文字列を作成し、リターンする
   * @return url
   */
    private static String getUrl() {
      Properties appProperties = AppConfig.getApplicationProperties();
      StringBuilder url = new StringBuilder();
      url.append("jdbc:postgresql://");
      url.append(appProperties.getProperty("DB_HOST"));
      url.append("/");
      url.append(appProperties.getProperty("DB_NAME"));
      return url.toString();
    }
}

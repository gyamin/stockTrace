package dao;

import java.sql.Connection;
import common.DbManager;

/**
 * PostgreSQL DAOクラス親オブジェクト
 * @author Yasumasa
 */
public class PostgresBase {
  protected Connection conn = null;
  
  PostgresBase(Connection conn) {
    if (conn == null) {
      // データベース接続設定
      this.conn = DbManager.getConnection();
    }else{
      this.conn = conn;
    }
  }
}

package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import bean.IssueBean;
import common.AppLogging;

/**
 * 銘柄情報テーブルアクセスクラス
 * @author Yasumasa
 */
public class Issues extends PostgresBase{

  // 全カラムを定義
  private String tableCols = "section, issue_code, issue_name, sector_code33, sector_code17, size_code ";
  
  public Issues(Connection conn) {
    super(conn);
  }

  /**
   * 銘柄情報を登録する
   * @param issues 銘柄情報オブジェクト配列
   * @throws SQLException
   */
  public void insertIssues(ArrayList<IssueBean> issues) throws SQLException {
    PreparedStatement pst;
    this.conn.setAutoCommit(false);
    String sql = "INSERT INTO issues (" 
      + this.tableCols
      + " ) VALUES(?, ?, ?, ? ,?, ?)";
    for(IssueBean elem : issues) {
      pst = this.conn.prepareStatement(sql);
      pst.setString(1, elem.section);
      pst.setInt(2, elem.issue_code);
      pst.setString(3, elem.issue_name);
      pst.setInt(4, elem.sector_code33);
      if(elem.sector_code17 == null) {
        pst.setNull(5, Types.INTEGER);
      }else{
        pst.setInt(5, elem.sector_code17);
      }
      if(elem.size_code == null){
        pst.setNull(6, Types.VARCHAR);
      }else{
        pst.setString(6, elem.size_code);
      }

      pst.executeUpdate();
      Logger.getLogger(AppLogging.LOG_NAME).log(Level.FINER, pst.toString());
    }
    this.conn.commit();
  }

  /**
   * 銘柄情報テーブルをtruncateする
   * @throws SQLException
   */
  public void truncateIssues() throws SQLException {
    Statement stm = this.conn.createStatement();
    String sql = "TRUNCATE issues";
    Logger.getLogger(AppLogging.LOG_NAME).log(Level.FINER, sql);
    int result=stm.executeUpdate(sql);
  }

  /**
   * 銘柄テーブルより全ての行を取得する
   * @return issues 銘柄オブジェクト配列
   * @throws SQLException
   */
  public ArrayList<IssueBean> selectAllIssues() throws SQLException {
    this.conn.setAutoCommit(false);
    Statement stm = this.conn.createStatement();
    stm.setFetchSize(100);
    String sql = "SELECT "
            + this.tableCols
            + " FROM issues "
            + " ORDER BY section, issue_code;";

    Logger.getLogger(AppLogging.LOG_NAME).log(Level.FINER, sql);
    ResultSet rs = stm.executeQuery(sql);
    ArrayList<IssueBean> issues = new ArrayList<>();
    while (rs.next()) {
      IssueBean issue = new IssueBean();
      issue.section = rs.getString("section");
      issue.issue_code = rs.getInt("issue_code");
      issue.issue_name = rs.getString("issue_name");
      issue.sector_code33 = rs.getInt("sector_code33");
      issue.sector_code17 = rs.getInt("sector_code17");
      issue.size_code = rs.getString("size_code");
      issues.add(issue);
    }
    return issues;
  }
}

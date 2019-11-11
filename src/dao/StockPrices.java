package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import bean.StockPrice;
import common.AppLogging;

/**
 * 株価テーブルアクセスクラス
 * @author Yasumasa
 */
public class StockPrices extends PostgresBase{

  // 全カラムを定義
  private String tableCols =
          "trade_date, " +
          "issue_code, " +
          "now_price, " +
          "start_price, " +
          "highest_price, " +
          "lowest_price, " +
          "year_hgihest_price, " +
          "year_lowest_price, " +
          "sales_amount, " +
          "trading_value, " +
          "market_capitalization ";
  
  public StockPrices(Connection conn) {
    super(conn);
  }

  /**
   * 株価データを１レコード登録する
   * @param stockPrice  株価情報テーブル対応オブジェクト
   * @throws SQLException
   */
  public void insertStockPrice(StockPrice stockPrice) throws SQLException {
    PreparedStatement pst;
    this.conn.setAutoCommit(false);
    String sql = "INSERT INTO stock_prices ("
      + this.tableCols
      + " ) VALUES(?, ?, ?, ? ,?, ?, ?, ?, ?, ?, ?)";

    pst = this.conn.prepareStatement(sql);
    pst.setDate(1, common.DateUtil.convertDateToSqlDate(stockPrice.trade_date));
    pst.setInt(2, stockPrice.issue_code);
    pst.setFloat(3, stockPrice.now_price);
    pst.setFloat(4, stockPrice.start_price);
    pst.setFloat(5, stockPrice.highest_price);
    pst.setFloat(6, stockPrice.lowest_price);
    pst.setFloat(7, stockPrice.year_hgihest_price);
    pst.setFloat(8, stockPrice.year_lowest_price);
    pst.setFloat(9, stockPrice.sales_amount);
    pst.setFloat(10, stockPrice.trading_value);
    if(stockPrice.market_capitalization == null) {
      pst.setNull(11, Types.FLOAT);
    }else{
      pst.setFloat(11, stockPrice.market_capitalization);
    }

    Logger.getLogger(AppLogging.LOG_NAME).log(Level.FINER, pst.toString());
    pst.executeUpdate();

    this.conn.commit();
  }

  /**
   * 株価データから取引日リストを取得する
   * @return
   * @throws SQLException
     */
  public ArrayList<Date> getTradeDateList() throws SQLException {
    this.conn.setAutoCommit(false);
    Statement stm = this.conn.createStatement();

    String sql = "SELECT trade_date FROM stock_prices" +
            " GROUP BY trade_date " +
            " ORDER BY trade_date DESC";
    Logger.getLogger(AppLogging.LOG_NAME).log(Level.FINER, sql);

    ResultSet rs = stm.executeQuery(sql);
    ArrayList<Date> tradeDateList = new ArrayList<>();
    while (rs.next()) {
      tradeDateList.add(rs.getDate("trade_date"));
    }
    return tradeDateList;
  }
}

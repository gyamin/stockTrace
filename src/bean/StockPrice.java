package bean;

import java.util.Date;

/**
 * 株価テーブル対応オブジェクト
 * @author Yasumasa
 */
public class StockPrice {
  public Date trade_date;
  public Integer issue_code;
  public Float now_price;
  public Float start_price;
  public Float highest_price;
  public Float lowest_price;
  public Float year_hgihest_price;
  public Float year_lowest_price;
  public Long sales_amount;
  public Long trading_value;
  public Float market_capitalization;
}

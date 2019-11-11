package stocktrace;

import bean.IssueBean;
import bean.PriceBean;
import bean.StockPrice;
import common.AppLogging;
import dao.StockPrices;
import common.AppConfig;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 株価取得/登録処理ワーカー
 * @author Yasumasa
 */
public class StockInfoWorker implements Runnable{

  // ワーカーが対象とする銘柄
  IssueBean issue;

  public StockInfoWorker(IssueBean issue) {
        this.issue = issue;
    }

  @Override
  public void run() {

    try {
      // ランダム秒数待機
      Random rnd = new Random();
      Properties appProperties = AppConfig.getApplicationProperties();
      int sec = rnd.nextInt(Integer.parseInt(appProperties.getProperty("WAIT_RAND_SECONDS")));
      Long waitSeconds = Long.parseLong(appProperties.getProperty("WAIT_MIN_SECONDS")) + sec;
      TimeUnit.SECONDS.sleep(waitSeconds);
      
      // #株価情報取得処理
      NomuraCrawler crw = new NomuraCrawler();
      // urlパラメータ設定
      Map<String, String> urlParams = new HashMap<>();
      urlParams.put("QCODE", Integer.toString(issue.issue_code));
      urlParams.put("TEMPLATE", "nomura_tp_kabu_01");
      urlParams.put("MKTN", "T");
      crw.setUrlParams(urlParams);
      
      // pageアクセスし株価情報取得
      crw.getStockData();
      Logger.getLogger(AppLogging.LOG_NAME).log(Level.FINER, crw.resultPrice.toString());
//      crw.resultPrice.print();
      urlParams.clear();
      
      // #株価情報DB登録処理
      PriceBean priceInfo = crw.resultPrice;
      StockPrice stockPrice = new StockPrice();
      // StockPriceオブジェクトに値を設定
      stockPrice.trade_date = priceInfo.getPriceDateTime();
      stockPrice.issue_code = this.issue.issue_code;
      stockPrice.now_price = priceInfo.getNowPrice();
      stockPrice.start_price = priceInfo.getStartPrice();
      stockPrice.highest_price = priceInfo.getHighestPrice();
      stockPrice.lowest_price = priceInfo.getLowestPrice();
      stockPrice.year_hgihest_price = priceInfo.getYearHighestPrice();
      stockPrice.year_lowest_price = priceInfo.getYearLowestPrice();
      stockPrice.sales_amount = priceInfo.getSalesAmount();
      stockPrice.trading_value = priceInfo.getTradingValue();
      stockPrice.market_capitalization = null;
      // DBにデータ登録
      StockPrices daoStockPrices = new StockPrices(null);
      daoStockPrices.insertStockPrice(stockPrice);
      
      TimeUnit.SECONDS.sleep(1);

      // ログ出力
      String msg = prepErrorMsgHeader();
      Logger.getLogger(AppLogging.LOG_NAME).log(Level.INFO, msg);

    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (IOException e) {
      String msg = prepErrorMsgHeader();
      Logger.getLogger(AppLogging.LOG_NAME).log(Level.SEVERE, msg, e);
//      e.printStackTrace();
    } catch (SQLException e) {
      String msg = prepErrorMsgHeader();
      Logger.getLogger(AppLogging.LOG_NAME).log(Level.SEVERE, msg, e);
//      e.printStackTrace();
    } catch (CrawlerException e) {
      String msg = prepErrorMsgHeader();
      Logger.getLogger(AppLogging.LOG_NAME).log(Level.SEVERE, msg, e);
    }
  }

  /**
   * ログ出力ヘッダー文字列を返す
   * @return msg ログ出力用ヘッダー文字列
     */
  private String prepErrorMsgHeader() {
    String msg = "スレッドID:" + Thread.currentThread().getId()
            + " 銘柄:" + this.issue.issue_code.toString() + " " + this.issue.issue_name;
    return msg;
  }
}

package stocktrace;

import bean.IssueBean;
import common.AppLogging;
import dao.Issues;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 株価情報取得処理実行クラス
 * @author Yasumasa
 */
public class ExecuteStockInfo {

  // スレッドプールの数
  private final int poolSize = 3;

  // 処理対象銘柄
  private ArrayList<IssueBean> executeTargetIssues;

  /**
   * 株価情報処理実行
   */
  public void main() {

    // ロギング共通設定
    AppLogging.setLoggingConfig();

    // ThreadPoolにthreadを作成
    ExecutorService threadPool = Executors.newFixedThreadPool(poolSize);

    try {
      Logger.getLogger(AppLogging.LOG_NAME).log(Level.INFO, "START: 株価情報取得スレッドプール登録");

      // 取得対象の銘柄情報を取得
      if(this.executeTargetIssues == null || this.executeTargetIssues.size() <= 0) {
        // 処理対象の銘柄リストが設定されていない場合は、全てを対象にする
        Issues daoIssues = new Issues(null);
        ArrayList<IssueBean> issues = daoIssues.selectAllIssues();
        this.executeTargetIssues = issues;
      }

      // 対象の銘柄分ループ処理
      while(! this.executeTargetIssues.isEmpty()) {
        // 処理対象の配列位置を取得
        int from = 0;
        int to = getToIndex(this.executeTargetIssues, poolSize);

        // スレッド作成
        for(int i=0; i < to; i++) {
          StockInfoWorker worker = new StockInfoWorker(this.executeTargetIssues.get(i));
          threadPool.execute(worker);
        }
        // 処理済みの配列をクリア
        this.executeTargetIssues.subList(from, to).clear();
      }

      threadPool.shutdown();

      Logger.getLogger(AppLogging.LOG_NAME).log(Level.INFO, "FINISH: 株価情報取得スレッドプール登録");
    } catch (SQLException ex) {
      Logger.getLogger(AppLogging.LOG_NAME).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * 配列から一定数の要素を取得するため配列の終わりの位置を返す
   * （配列の要素数 < 欲しい要素数の場合に、配列の要素数を返すために利用する。)
   * @param list    株価銘柄情報オブジェクト配列
   * @param toPos   取得したい配列の要素数
   * @return toPos  決定された配列の要素数
   */
  private int getToIndex(ArrayList<IssueBean> list, int toPos) {
    if(list.size() < toPos) {
      // 配列要素数が取得したい要素数より小さい場合は、配列の要素数を返す
      return list.size();
    }else{
      return toPos;
    }
  }

  /**
   * 処理対象銘柄リストを設定
   * @param executeTargetIssues
     */
  public void setExecuteTargetIssues(ArrayList<IssueBean> executeTargetIssues) {
    this.executeTargetIssues = executeTargetIssues;
  }
}

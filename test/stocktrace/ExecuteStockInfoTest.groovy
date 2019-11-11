package stocktrace

import bean.IssueBean
import dao.Issues

/**
 * ExecuteStockInfoテストクラス
 */
class ExecuteStockInfoTest extends GroovyTestCase {

    def executor
    def issues

    void setUp() {
        super.setUp()
        // 株価処理実行クラスをプロパティに設定
        this.executor = new ExecuteStockInfo()
        // 銘柄情報をプロパティに設定
        Issues daoIssues = new Issues(null);
        ArrayList<IssueBean> issues = daoIssues.selectAllIssues();
        this.issues = issues
    }

    void tearDown() {

    }

    void testMain() {
        // 処理対象を限定して実行クラスに設定
        ArrayList<IssueBean> targets = this.issues.subList(0, 5)
        this.executor.setExecuteTargetIssues(targets)
        this.executor.main()
    }
}

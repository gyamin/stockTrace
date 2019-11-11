import stocktrace.ExecuteStockInfo;

/**
 * 株価情報収集起動クラス
 */
public class MainRunner {
    /**
     * 株価情報収集処理を呼び出すメインプログラム
     * @param args
     */
    public static void main(String[] args) {
        ExecuteStockInfo executor = new ExecuteStockInfo();
        executor.main();
    }
}

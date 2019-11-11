package stocktrace

import bean.PriceBean
import org.junit.Test
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * NomuraCrawlerテストクラス
 */
class NomuraCrawlerTest extends GroovyTestCase {

    private def crawler0
    private def crawler1

    void setUp() {
        super.setUp()

        // テスト対象クラス0のインスタンス化
        this.crawler0 = new NomuraCrawler()
        // PriceBeanを初期化セット(getStockDateメソッドではメソッド内で初期化)
        this.crawler0.resultPrice = new PriceBean()
        File input0 = new File("./test/html/8411.html")
        Document doc0 = Jsoup.parse(input0, "UTF-8")
        this.crawler0.doc = doc0

        // テスト対象クラス1のインスタンス化
        this.crawler1 = new NomuraCrawler()
        // PriceBeanを初期化セット(getStockDateメソッドではメソッド内で初期化)
        this.crawler1.resultPrice = new PriceBean()
        File input1 = new File("./test/html/1928.html")
        Document doc1 = Jsoup.parse(input1, "UTF-8")
        this.crawler1.doc = doc1
    }

    void tearDown() {

    }

    /**
     * 売買日をテスト
     */
    @Test
    void testSetPriceDateTime() {
        this.crawler0.setPriceDateTime()
        assert new Date("2015/11/20 15:00:00") == this.crawler0.resultPrice.getPriceDateTime()

        this.crawler1.setPriceDateTime()
        assert new Date("2015/11/20 15:00:00") == this.crawler1.resultPrice.getPriceDateTime()
    }

    /**
     * 終値をテスト
     */
    @Test
    void testSetNowPrice() {
        this.crawler0.setNowPrice()
        assert 261.1F == this.crawler0.resultPrice.getNowPrice()

        this.crawler1.setNowPrice()
        assert 2092.5F == this.crawler1.resultPrice.getNowPrice()
    }

    /**
     * 始値をテスト
     */
    @Test
    void testSetStartPrice() {
        this.crawler0.setStartPrice()
        assert 259.4F == this.crawler0.resultPrice.getStartPrice()

        this.crawler1.setStartPrice()
        assert 2088.0F == this.crawler1.resultPrice.getStartPrice()
    }

    /**
     * 高値をテスト
     */
    @Test
    void testSetHighestPrice() {
        this.crawler0.setHighestPrice()
        assert 261.1F == this.crawler0.resultPrice.getHighestPrice()

        this.crawler1.setHighestPrice()
        assert 2092.5F == this.crawler1.resultPrice.getHighestPrice()
    }

    /**
     * 安値をテスト
     */
    @Test
    void testSetLowestPrice() {
        this.crawler0.setLowestPrice()
        assert 257.0F == this.crawler0.resultPrice.getLowestPrice()

        this.crawler1.setLowestPrice()
        assert 2061.0F == this.crawler1.resultPrice.getLowestPrice()
    }

    /**
     * 年初来高値をテスト
     */
    @Test
    void testSetYearHighestPrice() {
        this.crawler0.setYearHighestPrice()
        assert 280.4F == this.crawler0.resultPrice.getYearHighestPrice()

        this.crawler1.setYearHighestPrice()
        assert 2117.5F == this.crawler1.resultPrice.getYearHighestPrice()
    }

    /**
     * 年初来安値をテスト
     */
    @Test
    void testSetYearLowestPrice() {
        this.crawler0.setYearLowestPrice()
        assert 191.0F == this.crawler0.resultPrice.getYearLowestPrice()

        this.crawler1.setYearLowestPrice()
        assert 1472.0F == this.crawler1.resultPrice.getYearLowestPrice()
    }

    /**
     * 売買高をテスト
     */
    @Test
    void testSetSalesAmount() {
        this.crawler0.setSalesAmount()
        def amount0 = new BigDecimal(89764.5);
        assert amount0 * 1000 == this.crawler0.resultPrice.getSalesAmount()

        this.crawler1.setSalesAmount()
        def amount1 = new BigDecimal(3097);
        assert amount1 * 1000 == this.crawler1.resultPrice.getSalesAmount()
    }

    /**
     * 売買代金をテスト
     */
    @Test
    void testSetTradingValue() {
        this.crawler0.setTradingValue()
        assert 23317L * 1000000 == this.crawler0.resultPrice.getTradingValue()

        this.crawler1.setTradingValue()
        assert 6438L * 1000000 == this.crawler1.resultPrice.getTradingValue()
    }

    /**
     * 株価情報取得をテスト
     */
    @Test
    void testGetStockData() {
        def crawler = new NomuraCrawler()
        // URLパラメータを設定し株価情報を取得できること
        def urlParams = ['QCODE':'8411', 'TEMPLATE':'nomura_tp_kabu_01', 'MKTN':'T']
        crawler.setUrlParams(urlParams)
        crawler.getStockData()
        // 結果を取得
        def result = crawler.resultPrice
        assert null != result
    }
}

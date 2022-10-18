package crawler

import crawler.common.Utils
import kotlinx.coroutines.delay
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import crawler.config.ItemConfig
import crawler.db.model.StockValueBean

class Crawler(var itemConfigList: List<ItemConfig>) {
    var code: String = ""

    private fun getUrl(): String {
        val baseUrl =
            "https://quote.nomura.co.jp/nomura/cgi-bin/parser.pl?QCODE=%code%&TEMPLATE=nomura_tp_kabu_01&MKTN=T"
        val regex = Regex("%code%")
        return regex.replace(baseUrl, code)
    }

    suspend fun getStockValue(): StockValueBean {
        // 待機処理 100ms 〜 5000ms のランダム
        val wait = (100..5 * 1000).random()
        delay((wait).toLong())

        val url = getUrl()
        val doc: Document = Jsoup.connect(url).get()
        val stockValueHash = mutableMapOf<String, String>()

        itemConfigList.forEach {
            val rowVal = doc.select(it.cssQuery).text()
            val regex = Regex(it.regexPattern)
            val cleanedVal = regex.replace(rowVal, "")
            stockValueHash[it.name] = cleanedVal
        }

        val stockValue = StockValueBean()
        val utils = Utils()
        stockValue.code = code
        stockValue.tradingDate = utils.covertStringToDate(stockValueHash["trading_date"], "yyyy/MM/dd")
        stockValue.currentPrice = stockValueHash["current_price"]?.toFloatOrNull()
        stockValue.theDayBeforePrice = stockValueHash["the_day_before_price"]?.toFloatOrNull()
        stockValue.startPrice = stockValueHash["start_price"]?.toFloatOrNull()
        stockValue.highPrice = stockValueHash["high_price"]?.toFloatOrNull()
        stockValue.lowPrice = stockValueHash["low_price"]?.toFloatOrNull()
        stockValue.yearHighPrice = stockValueHash["year_high_price"]?.toFloatOrNull()
        stockValue.yearHighPriceDate = utils.covertStringToDate(stockValueHash["year_high_price_date"], "yyyy/MM/dd")
        stockValue.yearLowPrice = stockValueHash["year_low_price"]?.toFloatOrNull()
        stockValue.yearLowPriceDate = utils.covertStringToDate(stockValueHash["year_low_price_date"], "yyyy/MM/dd")
        stockValue.tradingUnit = stockValueHash["trading_unit"]?.toIntOrNull()

        return stockValue
    }
}
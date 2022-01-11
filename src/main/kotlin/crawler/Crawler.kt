package crawler

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
        // 待機処理
        val second = (1..5).random()
        val milliSecond = (0..9).random()
        val wait = second * 1000 + milliSecond * 100
        println("待機: ${wait}ms")
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
        stockValue.currentPrice = stockValueHash["current_price"]?.toFloatOrNull()
        stockValue.theDayBeforePrice = stockValueHash["the_day_before_price"]?.toFloatOrNull()
        stockValue.startPrice = stockValueHash["start_price"]?.toFloatOrNull()
        stockValue.theDayBeforePrice = stockValueHash["high_price"]?.toFloatOrNull()
        stockValue.theDayBeforePrice = stockValueHash["low_price"]?.toFloatOrNull()
        stockValue.theDayBeforePrice = stockValueHash["year_high_price"]?.toFloatOrNull()
        stockValue.theDayBeforePrice = stockValueHash["year_high_price_date"]?.toFloatOrNull()
        stockValue.theDayBeforePrice = stockValueHash["year_low_price"]?.toFloatOrNull()
        stockValue.theDayBeforePrice = stockValueHash["year_low_price_date"]?.toFloatOrNull()
        stockValue.theDayBeforePrice = stockValueHash["trading_unit"]?.toFloatOrNull()

        return stockValue
    }
}
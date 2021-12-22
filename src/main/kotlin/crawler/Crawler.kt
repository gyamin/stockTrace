package crawler

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import crawler.data.config.ItemConfig
import crawler.data.model.StockValueBean

class Crawler(val url: String, var itemConfigList: List<ItemConfig>) {

    fun getStockValue(): StockValueBean {
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
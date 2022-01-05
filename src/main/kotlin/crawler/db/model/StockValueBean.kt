package crawler.db.model

class StockValueBean() {
    var currentPrice: Float? = null
    var theDayBeforePrice: Float? = null
    var startPrice: Float? = null
    var highPrice: Float? = null
    var lowPrice: Float? = null
    var yearHighPrice: Float? = null
    var yearHighPriceDate: String? = ""
    var yearLowPrice: Float? = null
    var yearLowPriceDate: String? = ""
    var tradingUnit: Float? = null
}

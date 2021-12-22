package crawler.data.model

class ItemBean {
    lateinit var code: String
    lateinit var trading_name: String
    var market_type: Int? = null
    var market_name: String? = null
    var sector33_code: Float? = null
    var yearHighPrice: Float? = null
    var yearHighPriceDate: String? = ""
    var yearLowPrice: Float? = null
    var yearLowPriceDate: String? = ""
    var tradingUnit: Float? = null
}
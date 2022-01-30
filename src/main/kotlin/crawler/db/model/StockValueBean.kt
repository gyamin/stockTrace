package crawler.db.model

import java.time.LocalDate
import java.time.LocalDateTime

class StockValueBean() {
    var code: String = ""
    var tradingDate: LocalDate? = null
    var currentPrice: Float? = null
    var theDayBeforePrice: Float? = null
    var startPrice: Float? = null
    var highPrice: Float? = null
    var lowPrice: Float? = null
    var yearHighPrice: Float? = null
    var yearHighPriceDate: LocalDate? = null
    var yearLowPrice: Float? = null
    var yearLowPriceDate: LocalDate? = null
    var tradingUnit: Int? = null
    var createdAt: LocalDateTime? = null
    var updatedAt: LocalDateTime? = null
}

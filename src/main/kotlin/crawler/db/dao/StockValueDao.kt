package crawler.db.dao

import crawler.db.model.StockValueBean
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface StockValueDao {
    @SqlUpdate("""INSERT INTO stock_values (code, trading_date, current_price, the_day_before_price, start_price, high_price, low_price, year_high_price, year_high_price_date, year_low_price, year_low_price_date, trading_unit) 
        VALUES (:code, :tradingDate, :currentPrice, :theDayBeforePrice, :startPrice, :highPrice, :lowPrice, :yearHighPrice, :yearHighPriceDate, :yearLowPrice, :yearLowPriceDate, :tradingUnit)
    """)
    fun insertStockValue(@BindBean stockValueBean: StockValueBean)
}
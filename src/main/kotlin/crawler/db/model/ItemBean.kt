package crawler.db.model

import org.jdbi.v3.core.mapper.reflect.ColumnName

data class ItemBean (
    var code: String?,
    @ColumnName("trading_name")
    var tradingName: String?,
    var marketType: String?,
    var sector33Code: Int?,
    var sector33Name: String?,
    var sector17Code: Int?,
    var sector17Name: String?,
    var scaleCode: Int?,
    var scaleName: String?,
    var createdAt: String?,
    var updatedAt: String?
)
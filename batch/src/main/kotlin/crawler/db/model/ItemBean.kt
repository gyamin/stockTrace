package crawler.db.model

import org.jdbi.v3.core.mapper.reflect.ColumnName

data class ItemBean (
    var code: String,
    @ColumnName("trading_name")
    var tradingName: String?,
    @ColumnName("market_type")
    var marketType: String?,
    @ColumnName("sector33_code")
    var sector33Code: Int?,
    @ColumnName("sector33_name")
    var sector33Name: String?,
    @ColumnName("sector17_code")
    var sector17Code: Int?,
    @ColumnName("sector17_name")
    var sector17Name: String?,
    @ColumnName("scale_code")
    var scaleCode: Int?,
    @ColumnName("scale_name")
    var scaleName: String?,
    @ColumnName("created_at")
    var createdAt: String?,
    @ColumnName("updated_at")
    var updatedAt: String?
)
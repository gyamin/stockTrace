package crawler.data.model

import org.jdbi.v3.core.mapper.reflect.ColumnName

data class ItemBean (
    val code: String,
    @ColumnName("trading_name")
    val tradingName: String,
    val marketType: String?,
    val sector33Code: Int?,
    val sector33Name: String?,
    val sector17Code: Int?,
    val sector17Name: String?,
    val scaleCode: Int?,
    val scaleName: String?,
    val createdAt: String?,
    val updatedAt: String?
)
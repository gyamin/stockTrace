package crawler.db.dao

import crawler.db.model.ItemBean
import org.jdbi.v3.sqlobject.kotlin.RegisterKotlinMapper
import org.jdbi.v3.sqlobject.statement.SqlQuery

interface ItemDao {
    @SqlQuery("select code, trading_name, market_type, sector33_code, sector33_name, sector17_code, sector17_name, scale_code, scale_name, created_at, updated_at from items")
    @RegisterKotlinMapper(ItemBean::class)
    fun getAll(): List<ItemBean>
}
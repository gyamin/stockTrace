package crawler.db.dao

import crawler.db.DbConnection
import crawler.db.model.ItemBean
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ItemDaoTest {
    @Test
    internal fun testInsertItemBean() {
        val item = ItemBean(
            "1301","極洋", "市場第一部（内国株）",
            50, "水産・農林業", 1, "食品",
            7, "TOPIX Small 2", null, null
        )
        val dbConnection = DbConnection()
        val jdbi = dbConnection.getConnection()
        val dao = jdbi.onDemand<ItemDao>()
        jdbi.useHandle<Exception> { handle ->
            handle.begin()
            dao.insertItemBean(item)
            val selectItem = dao.getByCode("1301")
            assertEquals("極洋", selectItem.tradingName)
            handle.rollback()
        }
    }
}
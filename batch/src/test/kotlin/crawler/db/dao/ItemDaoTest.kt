package crawler.db.dao

import crawler.db.DbConnection
import crawler.db.model.ItemBean
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ItemDaoTest {
    @Test
    internal fun testInsertUpdateItemBean() {
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
            // データ削除
            dao.deleteByCode("1301")
            // データ挿入
            dao.insertItemBean(item)
            val selectItem = dao.getByCode("1301")
            assertEquals("極洋", selectItem.tradingName)
            // データ更新
            val item2 = item.copy(code = "1302", tradingName = "極洋2")
            dao.updateByCode(item2, "1301")
            val selectItem2 = dao.getByCode("1302")
            assertEquals("極洋2", selectItem2.tradingName)
            handle.rollback()
        }
    }
}
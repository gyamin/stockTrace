package crawler

import crawler.db.DbConnection
import crawler.db.dao.ItemBeanMapper
import crawler.db.model.ItemBean
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class CrawlerRunnerTest {

    @Test
    internal fun testRunCrawling(): Unit = runBlocking {
        // Daoを使わずに、RowMapperを利用してデータ取得
        val dbConnection = DbConnection()
        val jdbi = dbConnection.getConnection()
        val handle = jdbi.open()
        val qry = """
            SELECT code, trading_name, market_type, sector33_code, sector33_name, 
                    sector17_code, sector17_name, scale_code, scale_name, created_at, updated_at FROM items limit 10
        """.trimIndent()
        val items = handle.createQuery(qry).map(ItemBeanMapper()).list()
        // クローリング処理 10件で実行
        val crawlerRunner = CrawlerRunner(items as List<ItemBean>?)
        crawlerRunner.runCrawling()
    }
}

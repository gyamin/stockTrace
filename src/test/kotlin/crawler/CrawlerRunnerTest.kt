package crawler

import crawler.db.DbConnection
import crawler.db.dao.ItemDao
import crawler.db.model.ItemBean
import kotlinx.coroutines.runBlocking
import org.jdbi.v3.core.kotlin.mapTo
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CrawlerRunnerTest {

    @Test
    internal fun testRunCrawling() = runBlocking {
        val dbConnection = DbConnection()
        val jdbi = dbConnection.getConnection()
        val items = jdbi.useHandle<Exception> { handle ->
            val qry = """
                SELECT code, trading_name, market_type, sector33_code, sector33_name, 
                    sector17_code, sector17_name, scale_code, scale_name, created_at, updated_at FROM items limit 10
            """.trimIndent()
            handle.createQuery(qry).mapTo<ItemBean>().list()
        }

        val crawlerRunner = CrawlerRunner(null)
        crawlerRunner.runCrawling()
    }
}
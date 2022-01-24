package crawler

import crawler.db.DbConnection
import crawler.db.model.ItemBean
import kotlinx.coroutines.runBlocking
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import org.junit.jupiter.api.Test
import java.sql.ResultSet
import java.sql.SQLException

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
        // クローリング処理 10件で実行してみたら
        val crawlerRunner = CrawlerRunner(items as List<ItemBean>?)
        crawlerRunner.runCrawling()
    }
}

internal class ItemBeanMapper : RowMapper<ItemBean?> {
    @Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): ItemBean {
        return ItemBean(rs.getString("code"),
            rs.getString("trading_name"),
            rs.getString("market_type"),
            rs.getInt("sector33_code"),
            rs.getString("sector33_name"),
            rs.getInt("sector17_code"),
            rs.getString("sector17_name"),
            rs.getInt("scale_code"),
            rs.getString("scale_name"),
            rs.getString("created_at"),
            rs.getString("updated_at")
        )
    }
}
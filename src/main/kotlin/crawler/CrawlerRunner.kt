package crawler

import crawler.config.NomuraConfig
import crawler.db.DbConnection
import crawler.db.dao.ItemDao
import crawler.db.model.ItemBean
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import org.jdbi.v3.sqlobject.kotlin.onDemand
import java.time.LocalDateTime

class CrawlerRunner(items: List<ItemBean>?) {
    var itemsList = items

    private fun getItems():List<ItemBean> {
        // 銘柄一覧を取得
        val dbConnection = DbConnection()
        val jdbi = dbConnection.getConnection()
        val dao = jdbi.onDemand<ItemDao>()
        val items = dao.getAll()
        return items
    }

    suspend fun runCrawling() {
        if(itemsList.isNullOrEmpty()) {
            itemsList = getItems()
        }

        // 設定値の数で分割
        val chunkedItems = itemsList!!.chunked(5)

        // クローリング実行
        var config = NomuraConfig()
        coroutineScope {
            chunkedItems.forEach {
                val deferred = it.map {
                    async {
                        var crawler = Crawler(config.itemConfigList)
                        crawler.code = it.code.toString()
                        println(LocalDateTime.now())
                        val ret = crawler.getStockValue()
                        println("${it.tradingName} : ${ret.currentPrice}")
                        println(LocalDateTime.now())
                    }
                }
                deferred.awaitAll()
            }
        }
    }
}
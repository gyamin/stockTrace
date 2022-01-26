package crawler

import crawler.config.NomuraConfig
import crawler.db.DbConnection
import crawler.db.dao.ItemDao
import crawler.db.model.ItemBean
import crawler.db.model.StockValueBean
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import org.jdbi.v3.sqlobject.kotlin.onDemand
import java.time.LocalDateTime
import org.apache.logging.log4j.kotlin.logger

class CrawlerRunner(items: List<ItemBean>?) {
    val logger = logger("Main")
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
            // 銘柄リストが指定されていない場合、全銘柄を対象とする
            itemsList = getItems()
        }

        // 設定値の数で分割
        val chunkedItems = itemsList!!.chunked(5)

        // クローリング実行
        val resultList = mutableListOf<StockValueBean>()
        var config = NomuraConfig()
        coroutineScope {
            chunkedItems.forEach {
                val deferred = it.map {
                    async {
                        var crawler = Crawler(config.itemConfigList)
                        crawler.code = it.code
                        logger.debug("START: ${it.code} ${it.tradingName}")
                        val ret = crawler.getStockValue()
                        logger.debug("END: ${it.code} ${it.tradingName}")
                        resultList.add(ret)
                    }
                }
                deferred.awaitAll()
            }
        }
    }
}
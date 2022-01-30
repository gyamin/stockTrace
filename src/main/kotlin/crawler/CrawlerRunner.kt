package crawler

import crawler.config.NomuraConfig
import crawler.db.dao.ItemDao
import crawler.db.dao.StockValueDao
import crawler.db.model.ItemBean
import crawler.db.model.StockValueBean
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.apache.logging.log4j.kotlin.logger
import org.jdbi.v3.core.Jdbi

class CrawlerRunner(jdbi: Jdbi, items: List<ItemBean>?) {
    val logger = logger("Main")
    var itemsList = items
    val jdbi = jdbi

    private fun getItems():List<ItemBean> {
        // 銘柄一覧を取得
        val itemDao = jdbi.onDemand<ItemDao>()
        val items = itemDao.getAll()
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

        // クローリングデータ保存
        val stockValueDao = jdbi.onDemand<StockValueDao>()
        resultList.forEach {
            jdbi.useHandle<Exception> { handle ->
                handle.begin()
                stockValueDao.insertStockValue(it)
                handle.commit()
            }
        }
    }
}
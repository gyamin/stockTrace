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
import org.jdbi.v3.core.statement.UnableToExecuteStatementException

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

    private fun registerValues(stockValueList :List<StockValueBean>) {
        val stockValueDao = jdbi.onDemand<StockValueDao>()
        stockValueList.forEach {
            try {
                jdbi.useHandle<Exception> { handle ->
                    handle.begin()
                    stockValueDao.insertStockValue(it)
                    handle.commit()
                }
            }catch (e: UnableToExecuteStatementException) {
                logger.warn("SQL実行エラー: ${e.message}")
                return@forEach
            }
        }
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
                try {
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
                    // 非同期処理待ち受け
                    deferred.awaitAll()
                    // クローリングデータ登録
                    registerValues(resultList)
                }catch (e: Exception) {
                    logger.error("エラー: ${e.message}")
                    coroutineContext
                }
            }
        }
    }
}
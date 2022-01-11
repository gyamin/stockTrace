import crawler.Crawler
import crawler.config.NomuraConfig
import crawler.db.DbConnection
import crawler.db.dao.ItemDao
import kotlinx.coroutines.*
import org.jdbi.v3.sqlobject.kotlin.onDemand
import java.time.LocalDateTime

suspend fun main(args: Array<String>) {
    // 銘柄一覧を取得
    val dbConnection = DbConnection()
    val jdbi = dbConnection.getConnection()
    val dao = jdbi.onDemand<ItemDao>()
    val items = dao.getAll()

    // クローリング実行
    //  とりあえず、itemsに５件データ取得して、並列処理で実行してみる
    var config = NomuraConfig()
    coroutineScope {
        val deferred = items.map {
            async {
                var crawler = Crawler(config.itemConfigList)
                crawler.code = it.code
                println(LocalDateTime.now())
                val ret = crawler.getStockValue()
                println("${it.tradingName} : ${ret.currentPrice}")
                println(LocalDateTime.now())
            }
        }
        deferred.awaitAll()
    }
}
import crawler.Crawler
import crawler.config.NomuraConfig
import crawler.db.DbConnection
import crawler.db.dao.ItemDao
import org.jdbi.v3.sqlobject.kotlin.onDemand
import java.time.LocalDateTime

suspend fun main(args: Array<String>) {
    // 銘柄一覧を取得
    val dbConnection = DbConnection()
    val jdbi = dbConnection.getConnection()
    val dao = jdbi.onDemand<ItemDao>()
    val items = dao.getAll()

    // クローリング実行
    var config = NomuraConfig()
    var crawler = Crawler(config.itemConfigList)
    for (item in items) {
        // 株価データ取得
        crawler.code = item.code
        println(LocalDateTime.now())
        var result = crawler.getStockValue()
        println("${item.tradingName} : ${result.currentPrice}")
        println(LocalDateTime.now())
    }
}
import crawler.Crawler
import crawler.config.NomuraConfig
import crawler.db.DbConnection
import crawler.db.dao.ItemDao
import org.jdbi.v3.sqlobject.kotlin.onDemand

fun main(args: Array<String>) {
    // 銘柄一覧を取得
    val dbConnection = DbConnection()
    val jdbi = dbConnection.getConnection()
    val dao = jdbi.onDemand<ItemDao>()
    val items = dao.getAll()

    // クローリング実行
    var config = NomuraConfig()
    var crawler = Crawler(config.itemConfigList)
    for (item in items) {
        crawler.code = item.code
        var result = crawler.getStockValue()
        print(item.tradingName + ": ")
        println(result.currentPrice)
    }
}
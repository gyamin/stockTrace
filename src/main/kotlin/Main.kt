import crawler.MasterRegister
import crawler.CrawlerRunner
import crawler.db.DbConnection
import kotlin.system.exitProcess

suspend fun main(args: Array<String>) {

    if(args.size === 0) {
        print("処理種別が指定されていません。")
        exitProcess(9)
    }

    if(args[0] == "crawling") {
        // クローリング処理実行
        val dbConnection = DbConnection()
        val jdbi = dbConnection.getConnection()
        val crawlerRunner = CrawlerRunner(jdbi, null)
        crawlerRunner.runCrawling()
    }else if(args[0] == "registerMaster") {
        // マスタデータ更新処理
        val masterRegister = MasterRegister()
        masterRegister.registerItemMaster()
    }
}
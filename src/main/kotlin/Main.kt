import crawler.MasterRegister
import crawler.CrawlerRunner
import crawler.LogAnalyzer
import crawler.db.DbConnection
import org.apache.logging.log4j.kotlin.logger
import java.time.LocalDate
import java.util.*
import kotlin.system.exitProcess

suspend fun main(args: Array<String>) {

    val logger = logger("Main")

    if(args.isEmpty()) {
        print("処理種別が指定されていません。")
        exitProcess(9)
    }

    if(args[0] == "crawling") {
        logger.info("START: crawling")
        try {
            // クローリング処理実行
            val dbConnection = DbConnection()
            val jdbi = dbConnection.getConnection()
            val crawlerRunner = CrawlerRunner(jdbi, null)
            crawlerRunner.runCrawling()
        } catch (e: Exception) {
            logger.error("${e.message.toString()}")
        }

    }else if(args[0] == "registerMaster") {
        logger.info("START: registerMaster")
        try {
            // マスタデータ更新処理
            val masterRegister = MasterRegister()
            masterRegister.registerItemMaster()
        } catch (e: Exception) {
            logger.error("${e.message.toString()}")
        }

    }else if(args[0] == "logAnalyze") {
        logger.info("START: logAnalyze")
        try {
            // ログデータ取得処理
            val logAnalyzer = LogAnalyzer()
            logAnalyzer.analyzeLog(LocalDate.now())
        } catch (e: Exception) {
            logger.error("${e.message.toString()}")
        }
    }
}
package crawler

import java.io.File
import java.time.LocalDate

class LogAnalyzer {

    fun analyzeLog(targetDate: LocalDate) {
        var success = mutableListOf<Map<Int, String>>()
        var failed = mutableListOf<Map<Int, String>>()

        val path = System.getProperty("user.dir")
        println("Working Directory = $path")

        // ログファイルデータ読み込み
        val fileName = "logs/stockTrace_20220718.log"
        File(fileName).forEachLine {
            println(it)
        }

    }
}
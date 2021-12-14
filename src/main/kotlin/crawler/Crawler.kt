package crawler

import crawler.data.config.ItemConfig
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class Crawler(val url: String, var itemConfigList: List<ItemConfig>) {

    fun getDocument() {
        val doc: Document = Jsoup.connect(url).get()

        itemConfigList.forEach {
            val rowVal = doc.select(it.cssQuery).text()
            val regex = Regex(it.regexPattern)
            val cleanedVal = regex.replace(rowVal, "")
            print("${it.name} | ")
            print("$rowVal | ")
            println(cleanedVal)
        }
    }
}
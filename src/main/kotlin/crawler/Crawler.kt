package crawler

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class Crawler(var brandNumber: Int, var siteUrl: Char) {
    fun getStockValue() {

        val doc: Document = Jsoup.connect("https://en.wikipedia.org/").get()

    }
}
package crawler

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class Crawler(var brandNumber: Int, var siteUrl: String) {
    fun getDocument(): Document {
        val doc: Document = Jsoup.connect(siteUrl).get()
        val price = doc.select(".grid-16").text()
        return doc
    }
}
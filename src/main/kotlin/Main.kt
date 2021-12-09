import crawler.Crawler
import crawler.data.ItemConfig
import crawler.data.ItemConfigList

fun main(args: Array<String>) {

    var itemConfigList = mutableListOf<ItemConfig>()
    itemConfigList.add(ItemConfig("price", "aaa", ""))


    var crawler = Crawler(brandNumber = 0, siteUrl = "https://quote.nomura.co.jp/nomura/cgi-bin/parser.pl?QCODE=7272&TEMPLATE=nomura_tp_kabu_01&MKTN=T")
    crawler.getDocument()
}
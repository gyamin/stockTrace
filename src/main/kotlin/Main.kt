import crawler.Crawler
import crawler.data.config.NomuraConfig

fun main(args: Array<String>) {

    var config = NomuraConfig()
    var crawler = Crawler("https://quote.nomura.co.jp/nomura/cgi-bin/parser.pl?QCODE=7272&TEMPLATE=nomura_tp_kabu_01&MKTN=T", config.itemConfigList)
    crawler.getDocument()
}
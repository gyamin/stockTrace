import crawler.Crawler
fun main(args: Array<String>) {
    var crawler = Crawler(brandNumber = 0, siteUrl = "https://quote.nomura.co.jp/nomura/cgi-bin/parser.pl?QCODE=7272&TEMPLATE=nomura_tp_kabu_01&MKTN=T")
    crawler.getDocument()
}
import crawler.Crawler
import crawler.data.config.NomuraConfig
import crawler.data.model.StockValueBean
import java.sql.DriverManager

fun main(args: Array<String>) {

    val con = DriverManager.getConnection("jdbc:postgresql://localhost:15432/loc_stock_trace", "admin", "pwd12345!")
    print(con)

    var config = NomuraConfig()
    var crawler = Crawler("https://quote.nomura.co.jp/nomura/cgi-bin/parser.pl?QCODE=8411&TEMPLATE=nomura_tp_kabu_01&MKTN=T", config.itemConfigList)
    val stockValue = crawler.getStockValue()

}
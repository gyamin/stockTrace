import crawler.data.model.ItemBean
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.core.kotlin.mapTo
import org.jdbi.v3.sqlobject.kotlin.RegisterKotlinMapper
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.jdbi.v3.sqlobject.statement.SqlQuery


interface ItemDao {
    @SqlQuery("select code, trading_name, market_type, sector33_code, sector33_name, sector17_code, sector17_name, scale_code, scale_name, created_at, updated_at from items")
    @RegisterKotlinMapper(ItemBean::class)
    fun list(): List<ItemBean>
}

fun main(args: Array<String>) {
    // パターン1
//    val con = DriverManager.getConnection("jdbc:postgresql://localhost:15432/loc_stock_trace", "admin", "pwd12345!")
//    val itemList = mutableListOf<ItemBean>()
//    val stmt = con.createStatement()
//    val rs = stmt.executeQuery("SELECT * FROM items")
//    while (rs.next()) {
//        val item = ItemBean()
//        item.code = rs.getString("code")
//        item.tradingName = rs.getString("trading_name")
//        item.sector33Code = rs.getInt("sector33_code")
//        item.sector33Name = rs.getString("sector33_name")
//        item.sector17Code = rs.getInt("sector17_code")
//        item.sector17Name = rs.getString("sector17_name")
//        itemList.add(item)
//    }

    // jdbi -1 KotlinPlugin
    val jdbi = Jdbi.create("jdbc:postgresql://localhost:15432/loc_stock_trace", "admin", "pwd12345!")
        .installPlugin(KotlinPlugin())

    jdbi.useHandle<RuntimeException> { handle: Handle? ->
         val items = handle?.createQuery("select code, trading_name, market_type, sector33_code, sector33_name, sector17_code, sector17_name, scale_code, scale_name, created_at, updated_at from items")?.mapTo<ItemBean>()?.list()
        println(items.toString())
    }

    // jdbi -2 KotlinSqlObjectPlugin
    val jdbi2 = Jdbi.create("jdbc:postgresql://localhost:15432/loc_stock_trace", "admin", "pwd12345!")
        .installPlugin(KotlinSqlObjectPlugin())
    val dao = jdbi2.onDemand<ItemDao>()
    val items2 = dao.list()
    println(items2.toString())

}
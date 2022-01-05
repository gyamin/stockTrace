import crawler.db.DbConnection
import crawler.db.dao.ItemDao
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.onDemand

fun main(args: Array<String>) {
    val dbConnection = DbConnection()
    val jdbi = dbConnection.getConnection()
    val dao = jdbi.onDemand<ItemDao>()
    val items2 = dao.getAll()
    println(items2.toString())
}
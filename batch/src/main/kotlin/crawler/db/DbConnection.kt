package crawler.db

import crawler.common.Utils
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin

class DbConnection {
    fun getConnection(): Jdbi {
        val utils = Utils()
        val properties = utils.readProperties()
        return Jdbi.create(properties.getProperty("url"), properties.getProperty("user"), properties.getProperty("password"))
            .installPlugin(KotlinSqlObjectPlugin())
    }
}
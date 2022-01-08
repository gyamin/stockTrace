package crawler.db

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import java.io.File
import java.util.Properties

class DbConnection {
    fun getConnection(): Jdbi {
        val properties = Properties().apply {
            val file = File("application.properties")
            file.inputStream().use(this::load)
        }
        return Jdbi.create(properties.getProperty("url"), properties.getProperty("user"), properties.getProperty("password"))
            .installPlugin(KotlinSqlObjectPlugin())
    }
}
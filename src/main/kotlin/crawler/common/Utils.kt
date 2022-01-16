package crawler.common

import java.io.File
import java.util.*

class Utils {
    fun redProperties():Properties {
        return Properties().apply {
            val file = File("application.properties")
            file.inputStream().use(this::load)
        }
    }
}
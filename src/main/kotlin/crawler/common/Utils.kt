package crawler.common

import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

class Utils {
    fun readProperties():Properties {
        return Properties().apply {
            val file = File("application.properties")
            file.inputStream().use(this::load)
        }
    }

    fun covertStringToDate(letter: String?, format:String):LocalDate? {
        return try {
            val formatter = DateTimeFormatter.ofPattern(format)
            LocalDate.parse(letter, formatter)
        } catch(e: DateTimeParseException) {
            null
        }
    }
}
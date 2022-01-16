package crawler

import com.opencsv.CSVIterator
import com.opencsv.CSVReader
import crawler.db.model.ItemBean
import java.io.File
import java.io.FileReader

class MasterRegister {

    fun readItemCsv(filePath: String): List<ItemBean> {
        val file = File(filePath)
        val fileReader = FileReader(file)
        val csvIter = CSVIterator(CSVReader(fileReader))

        var itemBeanList = mutableListOf<ItemBean>()
        for ((index, row) in csvIter.withIndex()) {
            if(index === 0){
                continue
            }
            val itemBean = ItemBean(
                row[1].toString(),
                row[2].toString(),
                row[3].toString(),
                row[4].toIntOrNull(),
                row[5].toString(),
                row[6].toIntOrNull(),
                row[7].toString(),
                row[8].toIntOrNull(),
                row[9].toString(),
                null,
                null
            )
            itemBeanList.add(itemBean)
        }
        return itemBeanList
    }

}
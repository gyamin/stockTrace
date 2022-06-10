package crawler

import com.opencsv.CSVIterator
import com.opencsv.CSVReader
import crawler.common.Utils
import crawler.db.DbConnection
import crawler.db.dao.ItemDao
import crawler.db.model.ItemBean
import org.jdbi.v3.sqlobject.kotlin.onDemand
import java.io.File
import java.io.FileReader

class MasterRegister {

    fun readItemCsv(filePath: String): List<ItemBean> {
        val file = File(filePath)
        val fileReader = FileReader(file)
        val csvIter = CSVIterator(CSVReader(fileReader))

        var itemBeanList = mutableListOf<ItemBean>()
        for ((index, row) in csvIter.withIndex()) {
            if(index == 0){
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

    fun registerItemMaster() {
        val utils = Utils()
        val prop = utils.readProperties()
        val itemBeanList = readItemCsv(prop.getProperty("trading_item_file"))

        val dbConnection = DbConnection()
        val jdbi = dbConnection.getConnection()
        jdbi.useHandle<Exception> { handle ->
            val dao = jdbi.onDemand<ItemDao>()
            handle.begin()
            for (item in itemBeanList) {
                val selectedItem = dao.getByCode(item.code)
                if(selectedItem == null) {
                    // 該当レコードがない場合、新規追加
                    dao.insertItemBean(item)
                }else{
                    // レコードがある場合、更新
                    dao.updateByCode(item, item.code)
                }
            }
            handle.commit()
        }
    }
}
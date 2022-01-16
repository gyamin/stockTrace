package crawler

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MasterRegisterTest {
    @Test
    internal fun testReadItemCsv() {
        val masterRegister = MasterRegister()
        val itemBeanList = masterRegister.readItemCsv("./_doc/db/data/data_j.csv")
        assertEquals(4137, itemBeanList.size)
        val item0 = itemBeanList[0]
        assertEquals("極洋", item0.tradingName)
    }

    @Test
    internal fun testRegisterItemMaster() {
        val masterRegister = MasterRegister()
        masterRegister.registerItemMaster()
    }
}
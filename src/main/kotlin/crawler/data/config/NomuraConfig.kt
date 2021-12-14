package crawler.data.config

class NomuraConfig {
    private val siteUrl: String = ""
    val itemConfigList = mutableListOf<ItemConfig>()

    constructor() {
        itemConfigList.add(ItemConfig("current_price", ".grid-16", ",|↑|↓"))
        itemConfigList.add(ItemConfig("the_day_before_price", "div.row:nth-child(7) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(2) > tr:nth-child(4) > td:nth-child(2)", ","))
        itemConfigList.add(ItemConfig("start_price", "div.row:nth-child(7) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(2)", ",|\\(\\d{2}:\\d{2}\\)|\\ "))
        itemConfigList.add(ItemConfig("high_price", "div.row:nth-child(7) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(2) > tr:nth-child(2) > td:nth-child(2)", ",|\\(\\d{2}:\\d{2}\\)|\\ "))
        itemConfigList.add(ItemConfig("low_price", "div.row:nth-child(7) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(2) > tr:nth-child(3) > td:nth-child(2)", ",|\\(\\d{2}:\\d{2}\\)|\\ "))
        itemConfigList.add(ItemConfig("year_high_price", "table.tbl:nth-child(2) > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(2)", ",|\\(\\d{4}/\\d{2}/\\d{2}\\)|\\ "))
        itemConfigList.add(ItemConfig("year_high_price_date", "table.tbl:nth-child(2) > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(2)", "^.*.\\(|\\)\$"))
        itemConfigList.add(ItemConfig("year_low_price", "table.tbl:nth-child(2) > tbody:nth-child(2) > tr:nth-child(2) > td:nth-child(2)", ",|\\(\\d{4}/\\d{2}/\\d{2}\\)|\\ "))
        itemConfigList.add(ItemConfig("year_low_price_date", "table.tbl:nth-child(2) > tbody:nth-child(2) > tr:nth-child(2) > td:nth-child(2)", "^.*.\\(|\\)\$"))
    }

}
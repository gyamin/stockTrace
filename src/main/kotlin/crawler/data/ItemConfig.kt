package crawler.data

data class ItemConfig(val name: String, val cssQuery: String, val regexPattern: String) {}
data class ItemConfigList(val itemConfigList: List<ItemConfig>) {}
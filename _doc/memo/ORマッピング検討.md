
# 

## 参考
- [段階的に理解する O/R マッピング](https://qiita.com/ts7i/items/c23e50b5ee29887c446c)

## 《パターン1》プリミティブにレコードデータをBeanオブジェクトにマッピングする

- Item(銘柄)オブジェクト
```kotlin
class ItemBean {
    var code: String = ""
    var tradingName: String = ""
    var marketType: Int? = null
    var sector33Code: Int? = null
    var sector33Name: String? = ""
    var sector17Code: Int? = null
    var sector17Name: String? = ""
    var scaleCode: Int? = null
    var scaleName: String? = ""
    var createdAt: Date? = null
    var updatedAt: Date? = null
}
```

- プリミティブにマッピング
```kotlin
    val con = DriverManager.getConnection("jdbc:postgresql://localhost:15432/loc_stock_trace", "admin", "pwd12345!")
    val itemList = mutableListOf<ItemBean>()
    val stmt = con.createStatement()
    val rs = stmt.executeQuery("SELECT * FROM items")
    while (rs.next()) {
        val item = ItemBean()
        item.code = rs.getString("code")
        item.tradingName = rs.getString("trading_name")
        item.sector33Code = rs.getInt("sector33_code")
        item.sector33Name = rs.getString("sector33_name")
        item.sector17Code = rs.getInt("sector17_code")
        item.sector17Name = rs.getString("sector17_name")
        itemList.add(item)
    }
```

## 《パターン2》jdbiを利用したマッピング
- [jdbi](https://jdbi.org/)
なかなか情報がないけど、、とりあえず、サンプルを公式ドキュメントを眺めながら、試行錯誤。

```kotlin
import org.jdbi.v3.core.mapper.reflect.ColumnName

data class ItemBean (
    val code: String,
    @ColumnName("trading_name")
    val tradingName: String,
    val marketType: String?,
    val sector33Code: Int?,
    val sector33Name: String?,
    val sector17Code: Int?,
    val sector17Name: String?,
    val scaleCode: Int?,
    val scaleName: String?,
    val createdAt: String?,
    val updatedAt: String?
)
```

```kotlin
    val jdbi = Jdbi.create("jdbc:postgresql://localhost:15432/loc_stock_trace", "admin", "pwd12345!")
    .installPlugin(KotlinPlugin())

    jdbi.useHandle<RuntimeException> { handle: Handle? ->
        val items = handle?.createQuery("select code, trading_name, market_type, sector33_code, sector33_name, sector17_code, sector17_name, scale_code, scale_name, created_at, updated_at from items")?.mapTo<ItemBean>()?.list()
        println(items.toString())
    }
```

実行結果
```
[ItemBean(code=1301, tradingName=極洋, marketType=市場第一部（内国株）, sector33Code=50, sector33Name=水産・農林業, sector17Code=1, sector17Name=食品 , scaleCode=7, scaleName=TOPIX Small 2, createdAt=2021-12-20 16:56:25.497919, updatedAt=2021-12-20 16:56:25.497919), ItemBean(code=1305, tradingName=ダイワ上場投信－トピックス, marketType=ETF・ETN, sector33Code=null, sector33Name=null, sector17Code=null, sector17Name=null, scaleCode=null, scaleName=null, createdAt=2021-12-28 05:59:50.63231, updatedAt=2021-12-28 05:59:50.63231)]
```
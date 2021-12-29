
# 

## 参考
- [段階的に理解する O/R マッピング](https://qiita.com/ts7i/items/c23e50b5ee29887c446c)

## 1. プリミティブにレコードデータをBeanオブジェクトにマッピングする

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

## 2. jdbiを利用したマッピング (KotlinPlugin)
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

### トラブル

#### kotlin.jvm.KotlinReflectionNotSupportedError
```
Exception in thread "main" kotlin.jvm.KotlinReflectionNotSupportedError: Kotlin reflection implementation is not found at runtime. Make sure you have kotlin-reflect.jar in the classpath
	at kotlin.jvm.internal.ClassReference.error(ClassReference.kt:88)
	at kotlin.jvm.internal.ClassReference.getConstructors(ClassReference.kt:21)
	at org.jdbi.v3.core.kotlin.KotlinMapperKt.findConstructor(KotlinMapper.kt:310)
	at org.jdbi.v3.core.kotlin.KotlinMapperKt.access$findConstructor(KotlinMapper.kt:1)
	at org.jdbi.v3.core.kotlin.KotlinMapper.<init>(KotlinMapper.kt:50)
	at org.jdbi.v3.core.kotlin.KotlinMapper.<init>(KotlinMapper.kt:61)
	at org.jdbi.v3.core.kotlin.KotlinMapper.<init>(KotlinMapper.kt:61)
	at org.jdbi.v3.core.kotlin.KotlinMapperFactory.build(KotlinMapperFactory.kt:41)
	at org.jdbi.v3.core.mapper.RowMappers.lambda$findFor$0(RowMappers.java:168)
	at java.base/java.util.stream.ReferencePipeline$7$1.accept(ReferencePipeline.java:273)
	at java.base/java.util.Spliterators$ArraySpliterator.tryAdvance(Spliterators.java:958)
	at java.base/java.util.stream.ReferencePipeline.forEachWithCancel(ReferencePipeline.java:129)
	at java.base/java.util.stream.AbstractPipeline.copyIntoWithCancel(AbstractPipeline.java:502)
	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:488)
	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
	at java.base/java.util.stream.FindOps$FindOp.evaluateSequential(FindOps.java:150)
	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
	at java.base/java.util.stream.ReferencePipeline.findFirst(ReferencePipeline.java:647)
	at org.jdbi.v3.core.mapper.RowMappers.findFor(RowMappers.java:169)
	at org.jdbi.v3.core.mapper.Mappers.findFor(Mappers.java:107)
	at org.jdbi.v3.core.statement.StatementContext.findMapperFor(StatementContext.java:230)
	at org.jdbi.v3.core.result.ResultBearing.lambda$mapTo$1(ResultBearing.java:130)
	at org.jdbi.v3.core.result.ResultBearing$1.scanResultSet(ResultBearing.java:58)
	at org.jdbi.v3.core.statement.Query.scanResultSet(Query.java:56)
	at org.jdbi.v3.core.result.ResultBearing.mapTo(ResultBearing.java:129)
	at org.jdbi.v3.core.result.ResultBearing.mapTo(ResultBearing.java:86)
	at MainKt.main$lambda-0(Main.kt:38)
	at org.jdbi.v3.core.HandleConsumer.lambda$asCallback$0(HandleConsumer.java:32)
	at org.jdbi.v3.core.Jdbi.withHandle(Jdbi.java:342)
	at org.jdbi.v3.core.Jdbi.useHandle(Jdbi.java:358)
	at MainKt.main(Main.kt:33)
```

build.gradle.kts に org.jetbrains.kotlin:kotlin-reflect を追加で解決
```kotlin
dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jsoup:jsoup:1.14.3")
    implementation("org.postgresql:postgresql:42.3.1")
    implementation("org.jdbi:jdbi3-kotlin:3.26.0")
    implementation("org.jdbi:jdbi3-kotlin-sqlobject:3.26.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.32") ⇨　追加
}
```

## 3. jdbiを利用したマッピング (KotlinSqlObjectPlugin)

```kotlin
interface ItemDao {
    @SqlQuery("select code, trading_name, market_type, sector33_code, sector33_name, sector17_code, sector17_name, scale_code, scale_name, created_at, updated_at from items")
    @RegisterKotlinMapper(ItemBean::class)
    fun list(): List<ItemBean>
}

fun main(args: Array<String>) {
    val jdbi2 = Jdbi.create("jdbc:postgresql://localhost:15432/loc_stock_trace", "admin", "pwd12345!")
        .installPlugin(KotlinSqlObjectPlugin())
    val dao = jdbi2.onDemand<ItemDao>()
    val items2 = dao.list()
    println(items2.toString())
}
```

実行結果
```
[ItemBean(code=1301, tradingName=極洋, marketType=市場第一部（内国株）, sector33Code=50, sector33Name=水産・農林業, sector17Code=1, sector17Name=食品 , scaleCode=7, scaleName=TOPIX Small 2, createdAt=2021-12-20 16:56:25.497919, updatedAt=2021-12-20 16:56:25.497919), ItemBean(code=1305, tradingName=ダイワ上場投信－トピックス, marketType=ETF・ETN, sector33Code=null, sector33Name=null, sector17Code=null, sector17Name=null, scaleCode=null, scaleName=null, createdAt=2021-12-28 05:59:50.63231, updatedAt=2021-12-28 05:59:50.63231)]
```

### build.gradle.kts への依存性追加
```kotlin
dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jsoup:jsoup:1.14.3")
    implementation("org.postgresql:postgresql:42.3.1")
    implementation("org.jdbi:jdbi3-kotlin:3.26.0")
    implementation("org.jdbi:jdbi3-kotlin-sqlobject:3.26.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.32")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("com.opencsv:opencsv:5.5")
    implementation("org.apache.logging.log4j:log4j-api:2.15.0")
    implementation("org.apache.logging.log4j:log4j-core:2.15.0")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.1.0")
    implementation("org.slf4j:slf4j-log4j12:1.7.36")
}
```

### ログ出力
`crawler/CrawlerRunner.kt` 参照


## エラー対処
- `SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".` がログに表示される
```
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
```
- 対処
dependencies に slf4j-log4j12 を追加する。
```kotlin
dependencies {
    implementation("org.slf4j:slf4j-log4j12:1.7.36")
}
```
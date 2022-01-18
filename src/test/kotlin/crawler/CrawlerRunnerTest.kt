package crawler

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CrawlerRunnerTest {

    @Test
    internal suspend fun testRunCrawling() {
        val crawlerRunner = CrawlerRunner()
        crawlerRunner.runCrawling()
    }
}
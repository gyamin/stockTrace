import crawler.MasterRegister
import crawler.Runner

suspend fun main(args: Array<String>) {

    if(args.size === 0) {
        print("処理種別が指定されていません。")
    }

    if(args[0] === "crawling") {
        // クローリング処理実行
        val runner = Runner()
        runner.runCrawling()
    }else if(args[0] === "registerMaster") {
        // マスタデータ更新処理
        val masterRegister = MasterRegister()
    }
}
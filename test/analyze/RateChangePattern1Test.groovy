package analyze

import analyze.pattern.RateChangePattern1;

/**
 * RateChangePattern1テストクラス
 */
class RateChangePattern1Test extends GroovyTestCase {
    def rateChange
    def csvFile

    void setUp() {
        super.setUp()
        // 株価処理実行クラスをプロパティに設定
        this.rateChange = new RateChangePattern1()
        // ダミー分析情報をプロパティに設定
        def result = [] as ArrayList
        def data = [
                'section':'東証1部',
                'issue_code':'1000',
                'issue_name':'ダミー銘柄',
                'today' : "100",
                'yesterday' : "101",
                'five' : "102",
                'ten' : "103",
                'thirty' : "104",
                'sixty' : "105",
                'ninety' : "106",
                'hundredtwenty' : "107",
                'hundredfifty' : "108"
        ]
        result += data
        this.rateChange.result = result

        // 出力ファイル名
        this.csvFile = System.getProperty("user.dir") + "/workspace/analyze/result/test.csv"
    }

    void tearDown() {
        def targetFile = new File(this.csvFile);
        targetFile.delete()
    }

    /**
     * isExistsDate関数テスト
     */
    void testIsExistsDate() {
        // カレンダオブジェクト利用例
        //month - カレンダ内の MONTH カレンダフィールドの設定に使用する値。Month 値は 0 から始まる (1 月 は 0 になる)
        def oneDate = new GregorianCalendar(2015, 11, 1)
        oneDate.add(Calendar.DAY_OF_MONTH, 1);
        def sqlDate = new java.sql.Date(oneDate.getTimeInMillis());

        def tradeDateList = [] as ArrayList
        tradeDateList += new java.sql.Date(new GregorianCalendar(2015, 11, 1).getTimeInMillis())
        tradeDateList += new java.sql.Date(new GregorianCalendar(2015, 11, 2).getTimeInMillis())
        tradeDateList += new java.sql.Date(new GregorianCalendar(2015, 11, 3).getTimeInMillis())
        tradeDateList += new java.sql.Date(new GregorianCalendar(2015, 11, 4).getTimeInMillis())
        tradeDateList += new java.sql.Date(new GregorianCalendar(2015, 11, 5).getTimeInMillis())
        tradeDateList += new java.sql.Date(new GregorianCalendar(2015, 11, 6).getTimeInMillis())

        // 0を指定した場合、配列に要素があるので、対象の要素が取得できる
        assertEquals(this.rateChange.isExistsDate(tradeDateList, 0), tradeDateList[0])
        // 10を指定した場合、配列に要素がないので、java.sql.date(0)が取得できる
        assertEquals(this.rateChange.isExistsDate(tradeDateList, 10), new java.sql.Date(0))
    }

    /**
     * outputResult関数テスト
     */
    void testOutputResult() {
        this.rateChange.setOutPutFilePath(this.csvFile)
        this.rateChange.outputResult()

        def outPutFile = new File(this.csvFile);
        assertEquals(outPutFile.exists(), true)
    }
}

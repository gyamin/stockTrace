package analyze.pattern;

import java.io.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import common.AppConfig;
import common.AppLogging;
import dao.AnalyzePrices;
import dao.StockPrices;

/**
 * 株価変化率パターン実装クラス
 */
public class RateChangePattern1 implements PatternInterface{

    private final String patternName = "株価変化率";
    private String outPutFilePath;
    private ArrayList<HashMap> result;

    /**
     * 分析結果を取得する
     * @throws IOException
     * @throws SQLException
     */
    public void getAnalyzeResult() throws IOException, SQLException {

        // 株価情報テーブルより取引日の一覧を取得
        StockPrices stockPrices = new StockPrices(null);
        ArrayList<Date> tradeDateList = stockPrices.getTradeDateList();
        // 分析対象の日付を特定し配列に保持
        ArrayList<Date> targetDates = new ArrayList();
        targetDates.add(isExistsDate(tradeDateList, 0));
        targetDates.add(isExistsDate(tradeDateList, 1));
        targetDates.add(isExistsDate(tradeDateList, 4));
        targetDates.add(isExistsDate(tradeDateList, 9));
        targetDates.add(isExistsDate(tradeDateList, 19));
        targetDates.add(isExistsDate(tradeDateList, 29));
        targetDates.add(isExistsDate(tradeDateList, 39));
        targetDates.add(isExistsDate(tradeDateList, 49));
        targetDates.add(isExistsDate(tradeDateList, 59));
        targetDates.add(isExistsDate(tradeDateList, 89));
        targetDates.add(isExistsDate(tradeDateList, 119));
        targetDates.add(isExistsDate(tradeDateList, 149));

        // 分析SQLを実行し結果を取得
        AnalyzePrices analyzePrices = new AnalyzePrices(null, this.patternName);
        this.result = analyzePrices.getAnalyzePattern1(targetDates);

        Logger.getLogger(AppLogging.LOG_NAME).log(Level.INFO, "対象日:" + targetDates.toString());
        Logger.getLogger(AppLogging.LOG_NAME).log(Level.INFO, "取得件数:" + this.result.size());
    }

    /**
     * 解析結果をcsv出力する
     * @throws IOException
     */
    public void outputResult() throws IOException {
        // 結果ファイルを生成
        this.setOutPutFilePath("");
        OutputStreamWriter writer =new OutputStreamWriter(
                new FileOutputStream(this.outPutFilePath),"Shift_JIS");

        // 改行コードを取得
        String br = System.getProperty("line.separator");
        // ヘッダーを出力
        String header = "市場,銘柄コード,銘柄名,終値,前日値,5日前,10日前,20日前,30日前,40日前,50日前,60日前,90日前,120日前,150日前" + br;
        writer.write(header);

        // 解析結果をループ処理しcsvファイルに出力
        for(HashMap map : this.result){
            StringBuffer buff = new StringBuffer();
            buff.append(map.get("section") + ",");
            buff.append(map.get("issue_code") + ",");
            buff.append(map.get("issue_name") + ",");
            buff.append(map.get("today") + ",");
            buff.append(map.get("yesterday") + ",");
            buff.append(map.get("five") + ",");
            buff.append(map.get("ten") + ",");
            buff.append(map.get("twenty") + ",");
            buff.append(map.get("thirty") + ",");
            buff.append(map.get("forty") + ",");
            buff.append(map.get("fifty") + ",");
            buff.append(map.get("sixty") + ",");
            buff.append(map.get("ninety") + ",");
            buff.append(map.get("hundredtwenty") + ",");
            buff.append(map.get("hundredfifty") + br);
            writer.write(buff.toString());
        }
        writer.close();
    }

    /**
     * 出力ファイルのフルパスを生成
     */
    private void setOutPutFilePath(String filePath) {
        // ファイルパスが設定されている場合は何もしない
        if(this.outPutFilePath != null){return;}

        if(filePath.isEmpty()) {
            // 引数が空の場合はファイル名生成
            Properties appProperties = AppConfig.getApplicationProperties();
            java.util.Date sysDate = new java.util.Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy'年'MM'月'dd'日'");
            String fileName = format.format(sysDate) + ".csv";
            this.outPutFilePath = appProperties.getProperty("ANALYZE_RESULT_DIR") + fileName;
        }else{
            // 引数が設定されている場合は引数をファイル名に設定
            this.outPutFilePath = filePath;
        }
    }

    /**
     * 出力ファイルパスを返す
     * @return
     */
    public String getOutPutFilePath() {
        return this.outPutFilePath;
    }

    /**
     * 指定されたインデックスが存在するか調べ、存在しない場合はダミー日付を返す
     * @param tradeDateList
     * @param i
     * @return
     */
    private Date isExistsDate(ArrayList<Date> tradeDateList, int i) {
        Date date = null;
        try {
            date = tradeDateList.get(i);
            return date;
        } catch (IndexOutOfBoundsException e) {
            return new Date(0);
        }
    }
}

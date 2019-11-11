package dao;

import common.AppLogging;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 分析SQLを実行し結果を取得するクラス
 */
public class AnalyzePrices extends PostgresBase {

    public AnalyzePrices(Connection conn, String patternName) {
        super(conn);
        this.patternName = patternName;
    }

    private String patternName;

    public ArrayList<HashMap> getAnalyzePattern1(ArrayList<java.sql.Date> tradeDateList) throws IOException, SQLException {

        // SQL取得 jarファイル内のsqlファイルを開くためgetResourceAsStreamを利用
        BufferedReader reader = null;
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("analyze/sql/" + this.patternName + ".sql");
        reader = new BufferedReader(new InputStreamReader(stream));

        String buff;
        StringBuilder sql = new StringBuilder();
        while((buff = reader.readLine()) != null){
            sql.append(buff);
        }
//        String csvFilePath = System.getProperty("user.dir") + "/src/analyze/sql/" + this.patternName + ".sql";
//        byte[] fileContentBytes = Files.readAllBytes(Paths.get(csvFilePath));
//        String sql = new String(fileContentBytes, StandardCharsets.UTF_8);

        // DBアクセス
        PreparedStatement pst;
        this.conn.setAutoCommit(false);

        pst = this.conn.prepareStatement(sql.toString());
        pst.setDate(1, tradeDateList.get(0));
        pst.setDate(2, tradeDateList.get(1));
        pst.setDate(3, tradeDateList.get(2));
        pst.setDate(4, tradeDateList.get(3));
        pst.setDate(5, tradeDateList.get(4));
        pst.setDate(6, tradeDateList.get(5));
        pst.setDate(7, tradeDateList.get(6));
        pst.setDate(8, tradeDateList.get(7));
        pst.setDate(9, tradeDateList.get(8));
        pst.setDate(10, tradeDateList.get(9));
        pst.setDate(11, tradeDateList.get(10));
        pst.setDate(12, tradeDateList.get(11));

        pst.setDate(13, tradeDateList.get(0));
        pst.setDate(14, tradeDateList.get(1));
        pst.setDate(15, tradeDateList.get(2));
        pst.setDate(16, tradeDateList.get(3));
        pst.setDate(17, tradeDateList.get(4));
        pst.setDate(18, tradeDateList.get(5));
        pst.setDate(19, tradeDateList.get(6));
        pst.setDate(20, tradeDateList.get(7));
        pst.setDate(21, tradeDateList.get(8));
        pst.setDate(22, tradeDateList.get(9));
        pst.setDate(23, tradeDateList.get(10));
        pst.setDate(24, tradeDateList.get(11));

        Logger.getLogger(AppLogging.LOG_NAME).log(Level.FINER, pst.toString());
        ResultSet rs = pst.executeQuery();

        ArrayList<HashMap> results = new ArrayList<>();
        while (rs.next()) {
            HashMap row = new HashMap();
            row.put("section",rs.getString("section"));
            row.put("issue_code",rs.getString("issue_code"));
            row.put("issue_name",rs.getString("issue_name"));
            row.put("today",rs.getString("today"));
            row.put("yesterday",rs.getString("yesterday"));
            row.put("five",rs.getString("five"));
            row.put("ten",rs.getString("ten"));
            row.put("twenty",rs.getString("twenty"));
            row.put("thirty",rs.getString("thirty"));
            row.put("forty",rs.getString("forty"));
            row.put("fifty",rs.getString("fifty"));
            row.put("sixty",rs.getString("sixty"));
            row.put("ninety",rs.getString("ninety"));
            row.put("hundredtwenty",rs.getString("hundredtwenty"));
            row.put("hundredfifty",rs.getString("hundredfifty"));
            results.add(row);
        }
        return results;
    }
}

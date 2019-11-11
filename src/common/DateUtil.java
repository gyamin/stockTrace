package common;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日付ユーティリティ
 * @author Yasumasa
 */
public class DateUtil {

    /**
     * 指定したフォーマットでシステム日付文字列を返す
     * @param format
     * @return
     */
    public static String getDateByFormat(String format) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(calendar.getTime());
    }

    /**
    * java.util.Date型の値をjava.sql.Date型の値に変換する
    * @param date java.sql.Date型に変換したいjava.util.Date型の値
    * @return sqlDate
    */
    public static java.sql.Date convertDateToSqlDate(java.util.Date date) {
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return sqlDate;
    }
}

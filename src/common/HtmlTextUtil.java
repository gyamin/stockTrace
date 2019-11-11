package common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Htmlテキストの文字列操作ユーティリティ
 * @author Yasumasa
 */
public class HtmlTextUtil {

  /**
   * テキストから正規表現にマッチする文字列を削除する
   * @param text    htmlテキスト
   * @param regex   削除対象となる文字列の正規表現
   * @return eraseText
   */
  public static String eraseMatchText(String text, String regex) {
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(text);
    String eraseText = m.replaceAll("");
    return eraseText;
  }
  
  /**
   * テキストから正規表現にマッチする文字列を指定された文字に置換する
   * @param text      置換処理を行う文字列
   * @param regex     置換対象文字列
   * @param altText   置換後文字列
   * @return eraseText
   */
  public static String replaceMatchText(String text, String regex, String altText) {
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(text);
    String eraseText = m.replaceAll(altText);
    return eraseText;
  }

}

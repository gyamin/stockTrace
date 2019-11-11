package stocktrace;

/**
 * 東証個別銘柄株価HTMLページの各種値のCSS Path定義を設定
 * @author Yasumasa
 */
public class JpxHtmlPathDefine {
  // 終値
  public static final String nowPrice     = "body > table:nth-child(4) > tbody > tr > "
          + "td:nth-child(2) > table:nth-child(3) > tbody > tr:nth-child(4) > td > "
          + "table:nth-child(13) > tbody > tr:nth-child(1) > td:nth-child(2) > b";
  // 始値
  public static final String startPrice   = "body > table:nth-child(4) > tbody > tr > "
          + "td:nth-child(2) > table:nth-child(3) > tbody > tr:nth-child(4) > td > "
          + "table:nth-child(13) > tbody > tr:nth-child(3) > td:nth-child(2) > font";
  // 高値
  public static final String highestPrice = "body > table:nth-child(4) > tbody > tr > "
          + "td:nth-child(2) > table:nth-child(3) > tbody > tr:nth-child(4) > td > "
          + "table:nth-child(13) > tbody > tr:nth-child(4) > td:nth-child(2) > font";
  // 安値
  public static final String lowestPrice  = "body > table:nth-child(4) > tbody > tr > "
          + "td:nth-child(2) > table:nth-child(3) > tbody > tr:nth-child(4) > td > "
          + "table:nth-child(13) > tbody > tr:nth-child(4) > td:nth-child(4) > font";
  // 年初来高値
  public static final String yearHighestPrice = "body > table:nth-child(4) > tbody > tr > "
          + "td:nth-child(2) > table:nth-child(3) > tbody > tr:nth-child(4) > td > "
          + "table:nth-child(13) > tbody > tr:nth-child(5) > td:nth-child(2) > font";
  // 年初来安値
  public static final String yearLowestPrice  = "body > table:nth-child(4) > tbody > tr > "
          + "td:nth-child(2) > table:nth-child(3) > tbody > tr:nth-child(4) > td > "
          + "table:nth-child(13) > tbody > tr:nth-child(5) > td:nth-child(4) > font";
  // 売買高
  public static final String salesAmount      = "body > table:nth-child(4) > tbody > tr > "
          + "td:nth-child(2) > table:nth-child(3) > tbody > tr:nth-child(4) > td > "
          + "table:nth-child(13) > tbody > tr:nth-child(6) > td:nth-child(2) > font";
  // 売買代金
  public static final String tradingValue     = "body > table:nth-child(4) > tbody > tr > "
          + "td:nth-child(2) > table:nth-child(3) > tbody > tr:nth-child(4) > td > "
          + "table:nth-child(13) > tbody > tr:nth-child(6) > td:nth-child(4) > font";
  // 時価総額
  public static final String marketCapitalization = "body > table:nth-child(4) > tbody > tr > "
          + "td:nth-child(2) > table:nth-child(3) > tbody > tr:nth-child(4) > td > "
          + "table:nth-child(13) > tbody > tr:nth-child(7) > td:nth-child(2) > font";
  
}

package stocktrace;

import java.util.ArrayList;

/**
 * 野村證券株価検索HTMLページの各種値のCSS Path定義を設定
 * @author Yasumasa
 */
public class NomuraHtmlPathDefine {
  // 取引日
  public static final String priceDateTime    = "#content > div.unit.stock_summary.mt0_small.mt0_medium > div.stock_summary-wrap > div > div.stock_summary-right.fr > div > p"; 
  // 終値
  public static final String nowPrice         = "#content > div.unit.stock_summary.mt0_small.mt0_medium > div.stock_summary-wrap > div > div.stock_summary-right.fr > div > div.stock_summary-info_now > dl > dd > span";
  // 始値
  public static final String startPrice       = "#content > div:nth-child(4) > div > div:nth-child(1) > div > table:nth-child(1) > tbody > tr:nth-child(1) > td";
  // 高値
  public static final String highestPrice     = "#content > div:nth-child(4) > div > div:nth-child(1) > div > table:nth-child(1) > tbody > tr:nth-child(2) > td";
  // 安値
  public static final String lowestPrice      = "#content > div:nth-child(4) > div > div:nth-child(1) > div > table:nth-child(1) > tbody > tr:nth-child(3) > td";
  // 年初来高値
  public static final String yearHighestPrice = "#content > div:nth-child(4) > div > div:nth-child(1) > div > table:nth-child(2) > tbody > tr:nth-child(1) > td";
  // 年初来安値
  public static final String yearLowestPrice  = "#content > div:nth-child(4) > div > div:nth-child(1) > div > table:nth-child(2) > tbody > tr:nth-child(2) > td";
  // 売買高
  public static final String salesAmount      = "#content > div:nth-child(4) > div > div:nth-child(1) > div > table:nth-child(1) > tbody > tr:nth-child(5) > td";
  // 売買代金
  public static final String tradingValue     = "#content > div:nth-child(4) > div > div:nth-child(1) > div > table:nth-child(1) > tbody > tr:nth-child(6) > td";
  // 時価総額
  public static final String marketCapitalization = "";
  
  // 株価情報日時取得のための正規表現置換
  public static final ArrayList<String> priceDateTimeRegexs;
    // <p class="stock_summary-info_time">2015/10/02&nbsp;15:00</p>
    static {
      ArrayList<String> list = new ArrayList<>();
      list.add("<(\"[^\"]*\"|'[^']*'|[^'\">])*>");    //<*>を削除
      priceDateTimeRegexs = list;
    }  
  
  // 終値取得のための正規表現置換
  public static final ArrayList<String> nowPriceRegexs;
    // <span class="stock_summary-number">3,529<span class="plus">↑</span> </span>
    static {
      ArrayList<String> list = new ArrayList<>();
      list.add("<(\"[^\"]*\"|'[^']*'|[^'\">])*>");    //<*>を削除
      list.add("[^0-9.]");                            //数字.以外を削除
      nowPriceRegexs = list;
    }

  // 始値取得のための正規表現置換
  public static final ArrayList<String> startPriceRegexs;
    // <td class="def-number right">3,488<span class="small"> (09:00)</span></td>
    static {
      ArrayList<String> list = new ArrayList<>();
      list.add("<(\"[^\"]*\"|'[^']*'|[^'\">])*>");      //<*>を削除
      list.add("\\((\"[^\"]*\"|'[^']*'|[^'\">])*\\)");  //(*)を削除
      list.add("[^0-9.]");                              //数字.以外を削除
      startPriceRegexs = list;
    }
  
  // 高値取得のための正規表現置換
  public static final ArrayList<String> highestPriceRegexs;
    // <td class="def-number right">3,545<span class="small"> (09:03)</span></td>
    static {
      ArrayList<String> list = new ArrayList<>();
      list.add("<(\"[^\"]*\"|'[^']*'|[^'\">])*>");      //<*>を削除
      list.add("\\((\"[^\"]*\"|'[^']*'|[^'\">])*\\)");  //(*)を削除
      list.add("[^0-9.]");                              //数字.以外を削除
      highestPriceRegexs = list;
    }

  // 安値取得のための正規表現置換
  public static final ArrayList<String> lowestPriceRegexs;
    // <td class="def-number right">3,437<span class="small"> (10:40)</span></td>
    static {
      ArrayList<String> list = new ArrayList<>();
      list.add("<(\"[^\"]*\"|'[^']*'|[^'\">])*>");      // <*>を削除
      list.add("\\((\"[^\"]*\"|'[^']*'|[^'\">])*\\)");  // (*)を削除
      list.add("[^0-9.]");                              //数字.以外を削除
      lowestPriceRegexs = list;
    }

  // 年初来高値取得のための正規表現置換
  public static final ArrayList<String> yearHighestPriceRegexs;
    // <td class="def-number right">4,700.0<br class="none_large"><span class="small"> (2015/05/28)</span></td>
    static {
      ArrayList<String> list = new ArrayList<>();
      list.add("<(\"[^\"]*\"|'[^']*'|[^'\">])*>");      // <*>を削除
      list.add("\\((\"[^\"]*\"|'[^']*'|[^'\">])*\\)");  // (*)を削除
      list.add("[^0-9.]");                              //数字.以外を削除
      yearHighestPriceRegexs = list;
    }

  // 年初来安値取得のための正規表現置換
  public static final ArrayList<String> yearLowestPriceRegexs;
    // <td class="def-number right">2,807.0<br class="none_large"><span class="small"> (2015/01/16)</span></td>
    static {
      ArrayList<String> list = new ArrayList<>();
      list.add("<(\"[^\"]*\"|'[^']*'|[^'\">])*>");      // <*>を削除
      list.add("\\((\"[^\"]*\"|'[^']*'|[^'\">])*\\)");  // (*)を削除
      list.add("[^0-9.]");                              //数字.以外を削除
      yearLowestPriceRegexs = list;
    }
  
  // 売買高のための正規表現置換
  public static final ArrayList<String> salesAmountRegexs;
    // <td class="def-number right">1,077.6 千株</td>
    static {
      ArrayList<String> list = new ArrayList<>();
      list.add("<(\"[^\"]*\"|'[^']*'|[^'\">])*>");      // <*>を削除
      list.add("\\p{InCJKUnifiedIdeographs}|,| ");      // 漢字、スペース、,を削除
      salesAmountRegexs = list;
    }
 
  // 売買代金のための正規表現置換
  public static final ArrayList<String> tradingValueRegexs;
    // <td class="def-number right">3,772 百万円</td>
    static {
      ArrayList<String> list = new ArrayList<>();
      list.add("<(\"[^\"]*\"|'[^']*'|[^'\">])*>");      // <*>を削除
      list.add("\\p{InCJKUnifiedIdeographs}|,| ");      // 漢字、スペース、,を削除
      tradingValueRegexs = list;
    }  
    
}

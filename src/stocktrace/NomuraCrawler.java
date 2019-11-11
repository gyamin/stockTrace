package stocktrace;

import bean.PriceBean;
import java.io.IOException;

import common.AppLogging;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import common.AppConfig;
import common.HtmlTextUtil;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 野村證券株価検索より株価情報を取得する
 * @author Yasumasa
 */
public class NomuraCrawler {
  private String url = "http://quote.nomura.co.jp/nomura/cgi-bin/parser.pl";
  private String userAgent  = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:40.0) Gecko/20100101 Firefox/40.0";
  private final NomuraHtmlPathDefine htmlPathDefine;
  private Document doc;
  private Map<String, String> urlParams = null;
  public PriceBean resultPrice;

  public NomuraCrawler() {
    this.htmlPathDefine = new NomuraHtmlPathDefine();
  }
  
  public void setUrlParams(Map<String, String>  urlParams) {
    this.urlParams = urlParams;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
  
  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }
  
  public void getStockData() throws IOException, CrawlerException {
    // パラメータを組み立て
    this.buildUrlWithUrlParams();
    this.resultPrice = new PriceBean();
    Logger.getLogger(AppLogging.LOG_NAME).log(Level.FINER, this.url);

    // リトライ回数取得
    Properties appProperties = AppConfig.getApplicationProperties();
    Integer retry = Integer.valueOf(appProperties.getProperty("CRAWLER_RETRY_COUNT"));

    // page取得 失敗の場合リトライ回数だけリトライ
    for(int retryCount=0; retry >= retryCount; retryCount++) {
      try {
        // リトライ処理の場合はスリープする
        if(retryCount > 0) {
          try {
            TimeUnit.SECONDS.sleep(1);
          } catch (InterruptedException ex0) {
            Logger.getLogger(AppLogging.LOG_NAME).log(Level.SEVERE, null, ex0);
          }
        }

        // htmlドキュメント取得
        this.doc = Jsoup.connect(this.url).userAgent(this.userAgent).get(); 
        
        // 日時
        setPriceDateTime();
        // 現在値
        setNowPrice();
        // 始値
        setStartPrice();
        // 高値
        setHighestPrice();
        // 安値
        setLowestPrice();
        // 年初来高値
        setYearHighestPrice();
        // 年初来安値
        setYearLowestPrice();
        // 売買高
        setSalesAmount();
        // 売買代金
        setTradingValue();
//        // 時価総額
//        setMarketCapitalization();

//        this.resultPrice.print();
        break;
      }catch(org.jsoup.HttpStatusException ex){
        // httpアクセスに失敗した場合
        String msg = this.url + " へのhttpアクセスに失敗しました。";
        Logger.getLogger(AppLogging.LOG_NAME).log(Level.SEVERE, msg, ex);
        throw new CrawlerException(ex.toString());
      } catch (ParseException ex1) {
        String msg = "htmlのパースに失敗しました。";
        Logger.getLogger(AppLogging.LOG_NAME).log(Level.SEVERE, msg, ex1);
        throw new CrawlerException(ex1.toString());
      }
    }
  }
  
  /**
   * urlParamsを利用し、urlにgetパラメータを設定する
   */
  private void buildUrlWithUrlParams() {
    if(this.urlParams != null) {
      this.urlParams.entrySet().stream().forEach((e) -> {
        if(! this.url.contains("?")) {
          // urlに?が存在しない場合、パラメータ1個目は?で指定
          this.url = this.url + "?" + e.getKey() + "=" + e.getValue();
        } else {
          this.url = this.url + "&" + e.getKey() + "=" + e.getValue();
        }
      });
    }
  }

  /**
   *
   * @param text
   * @param regexs
   * @return
     */

  private String stripTextByRegex(String text, ArrayList<String> regexs) {
    for(String regex : regexs) {
      text = HtmlTextUtil.eraseMatchText(text, regex); 
    }
    return text;
  }

  /**
   * 株価日時を取得し、結果に格納する
   * @throws ParseException
     */
  private void setPriceDateTime() throws ParseException {
    Elements priceDateTime = doc.select(NomuraHtmlPathDefine.priceDateTime);
    String text = priceDateTime.toString();
    text = this.stripTextByRegex(text, NomuraHtmlPathDefine.priceDateTimeRegexs);
    // text = "2015/10/02&nbsp;15:00"
    text = HtmlTextUtil.replaceMatchText(text, "&nbsp;", " ");
    // text = "2015/10/02 15:00"
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    Date formatDate = sdf.parse(text);
    this.resultPrice.setPriceDateTime(formatDate);
  }
  
  /**
   * 現在値を取得し、結果に格納する
   */
  private void setNowPrice() {
    Elements nowPrice = doc.select(NomuraHtmlPathDefine.nowPrice);
    String text = nowPrice.toString();
    text = this.stripTextByRegex(text, NomuraHtmlPathDefine.nowPriceRegexs);
    this.resultPrice.setNowPrice(Float.parseFloat(text));
  }

  /**
   * 始値を取得し、結果に格納する
   */
  private void setStartPrice() {
    Elements startPrice = doc.select(NomuraHtmlPathDefine.startPrice);
    String text = startPrice.toString();
    text = this.stripTextByRegex(text, NomuraHtmlPathDefine.startPriceRegexs);
    this.resultPrice.setStartPrice(Float.parseFloat(text));
  }

  /**
   * 高値を取得し、結果に格納する
   */
  private void setHighestPrice() {
    Elements highestPrice = doc.select(NomuraHtmlPathDefine.highestPrice);
    String text = highestPrice.toString();
    text = this.stripTextByRegex(text, NomuraHtmlPathDefine.highestPriceRegexs);
    this.resultPrice.setHighestPrice(Float.parseFloat(text));
  }

  /**
   * 安値を取得し、結果に格納する
   */
  private void setLowestPrice() {
    Elements lowestPrice = doc.select(NomuraHtmlPathDefine.lowestPrice);
    String text = lowestPrice.toString();
    text = this.stripTextByRegex(text, NomuraHtmlPathDefine.lowestPriceRegexs);
    this.resultPrice.setLowestPrice(Float.parseFloat(text));
  }
  
  /**
   * 年初来高値を取得し、結果に格納する
   */
  private void setYearHighestPrice() {
    Elements yearHighestPrice = doc.select(NomuraHtmlPathDefine.yearHighestPrice);
    String text = yearHighestPrice.toString();
    text = this.stripTextByRegex(text, NomuraHtmlPathDefine.yearHighestPriceRegexs);
    this.resultPrice.setYearHighestPrice(Float.parseFloat(text));
  }

  /**
   * 年初来安値を取得し、結果に格納する
   */
  private void setYearLowestPrice() {
    Elements yearLowestPrice = doc.select(NomuraHtmlPathDefine.yearLowestPrice);
    String text = yearLowestPrice.toString();
    text = this.stripTextByRegex(text, NomuraHtmlPathDefine.yearLowestPriceRegexs);
    this.resultPrice.setYearLowestPrice(Float.parseFloat(text));
  }

  /**
   * 売買高を取得し、結果に格納する
   */
  private void setSalesAmount() {
    Elements salesAmount = doc.select(NomuraHtmlPathDefine.salesAmount);
    String text = salesAmount.toString();
    text = this.stripTextByRegex(text, NomuraHtmlPathDefine.salesAmountRegexs);
    // 単位は千
    BigDecimal val = new BigDecimal(text);
    this.resultPrice.setSalesAmount(val.multiply(BigDecimal.valueOf(1000)).longValue());
//    this.resultPrice.setSalesAmount((long)(Float.parseFloat(text) * 1000));
  }

  /**
   * 売買代金を取得し、結果に格納する
   */
  private void setTradingValue() {
    Elements tradingValue = doc.select(NomuraHtmlPathDefine.tradingValue);
    String text = tradingValue.toString();
    text = this.stripTextByRegex(text, NomuraHtmlPathDefine.salesAmountRegexs);
    // 単位は100万円
    BigDecimal val = new BigDecimal(text);
    this.resultPrice.setTradingValue(val.multiply(BigDecimal.valueOf(100 * 10000)).longValue());
//    this.resultPrice.setTradingValue((long)(Float.parseFloat(text) * 100 * 10000));
  }

  /**
   * 時価総額を取得し、結果に格納する
   */
  private void setMarketCapitalization() {
    // 時価総額 <font size="2">24,725,793,857,128.0<br></font>
    Elements marketCapitalization = doc.select(JpxHtmlPathDefine.marketCapitalization);
    // <font>タグを削除
    String tmp = marketCapitalization.toString();
    String regex = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
    tmp = HtmlTextUtil.eraseMatchText(tmp, regex);   
    // , を削除
    regex = ",";
    tmp = HtmlTextUtil.eraseMatchText(tmp, regex);
    // 空白削除
    tmp = tmp.trim();
    this.resultPrice.setMarketCapitalization(Float.parseFloat(tmp));
  }
}

/**
 * クローラ処理例外
 */
class CrawlerException extends Exception {
  public CrawlerException(String msg) {
    super(msg);
  }
}

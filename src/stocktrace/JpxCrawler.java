package stocktrace;

import bean.PriceBean;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import common.HtmlTextUtil;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yasumasa
 */
public class JpxCrawler {
  private final Integer retry = 5;
  private String url = "http://quote.jpx.co.jp/jpx/template/quote.cgi?F=tmp/stock_detail";
  private String userAgent  = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:40.0) Gecko/20100101 Firefox/40.0";
  private Document doc;
  private Map<String, String> urlParams = null;
  public PriceBean resultPrice;
  
  public void setUrlParams(Map<String, String>  urlParams) {
    this.urlParams = urlParams;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
  
  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }
  
  public void getStockData() throws IOException {
    // パラメータを組み立て
    this.buildUrlWithUrlParams();
    this.resultPrice = new PriceBean();
    System.out.println(this.url);
    
    for(int retryCount=0; this.retry >= retryCount; retryCount++) {
      try {
        this.doc = Jsoup.connect(this.url).userAgent(this.userAgent).get(); 
        
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
        // 時価総額
        setMarketCapitalization();

        this.resultPrice.print();   
      }catch(org.jsoup.HttpStatusException ex){
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ex1) {
          Logger.getLogger(JpxCrawler.class.getName()).log(Level.SEVERE, null, ex1);
        }
      }   
    }
  }
  
  /**
   * urlParamsを利用し、urlにgetパラメータを設定する
   */
  private void buildUrlWithUrlParams() {
    if(this.urlParams!= null) {
      this.urlParams.entrySet().stream().forEach((e) -> {
        this.url = this.url + "&" + e.getKey() + "=" + e.getValue();
      });
    }
  }
  
  /**
   * 現在値を取得し、結果に格納する
   */
  private void setNowPrice() {
    // 現在値html取得 <br>7,251 (15:00)</br>
    Elements nowPrice = doc.select(JpxHtmlPathDefine.nowPrice);
    // <br>タグを削除
    String tmp = nowPrice.toString();
    String regex = "</*b>";
    tmp = HtmlTextUtil.eraseMatchText(tmp, regex);   
    // (xx:xx)を削除
    regex = "\\([0-9]*:[0-9]*\\)";
    tmp = HtmlTextUtil.eraseMatchText(tmp, regex);
    // , を削除
    regex = ",";
    tmp = HtmlTextUtil.eraseMatchText(tmp, regex);
    // 空白削除
    tmp = tmp.trim();
    this.resultPrice.setNowPrice(Float.parseFloat(tmp));
  }

  /**
   * 始値を取得し、結果に格納する
   */
  private void setStartPrice() {
    // 始値html取得 <font size="2">7,251<br></font>
    Elements startPrice = doc.select(JpxHtmlPathDefine.startPrice);
    // <font>タグを削除
    String tmp = startPrice.toString();
    String regex = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
    tmp = HtmlTextUtil.eraseMatchText(tmp, regex);   
    // , を削除
    regex = ",";
    tmp = HtmlTextUtil.eraseMatchText(tmp, regex);
    // 空白削除
    tmp = tmp.trim();
    this.resultPrice.setStartPrice(Float.parseFloat(tmp));
  }

  /**
   * 高値を取得し、結果に格納する
   */
  private void setHighestPrice() {
    // 高値html取得 <font size="2">7,251<br></font>
    Elements highestPrice = doc.select(JpxHtmlPathDefine.highestPrice);
    // <font>タグを削除
    String tmp = highestPrice.toString();
    String regex = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
    tmp = HtmlTextUtil.eraseMatchText(tmp, regex);   
    // , を削除
    regex = ",";
    tmp = HtmlTextUtil.eraseMatchText(tmp, regex);
    // 空白削除
    tmp = tmp.trim();
    this.resultPrice.setHighestPrice(Float.parseFloat(tmp));
  }

  /**
   * 安値を取得し、結果に格納する
   */
  private void setLowestPrice() {
    // 安値html取得 <font size="2">7,251<br></font>
    Elements lowestPrice = doc.select(JpxHtmlPathDefine.lowestPrice);
    // <font>タグを削除
    String tmp = lowestPrice.toString();
    String regex = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
    tmp = HtmlTextUtil.eraseMatchText(tmp, regex);   
    // , を削除
    regex = ",";
    tmp = HtmlTextUtil.eraseMatchText(tmp, regex);
    // 空白削除
    tmp = tmp.trim();
    this.resultPrice.setLowestPrice(Float.parseFloat(tmp));
  }
  
  /**
   * 年初来高値を取得し、結果に格納する
   */
  private void setYearHighestPrice() {
    // 年初来高値 <font size="2">8,783 (15/03/24)</font>
    Elements yearHighestPrice = doc.select(JpxHtmlPathDefine.yearHighestPrice);
    // <font>タグを削除
    String tmp = yearHighestPrice.toString();
    String regex = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
    tmp = HtmlTextUtil.eraseMatchText(tmp, regex);
    regex = "\\((\"[^\"]*\"|'[^']*'|[^'\">])*\\)";
    tmp = HtmlTextUtil.eraseMatchText(tmp, regex);   
    // , を削除
    regex = ",";
    tmp = HtmlTextUtil.eraseMatchText(tmp, regex);
    // 空白削除
    tmp = tmp.trim();
    this.resultPrice.setYearHighestPrice(Float.parseFloat(tmp));
  }

  /**
   * 年初来安値を取得し、結果に格納する
   */
  private void setYearLowestPrice() {
    // 年初来安値 <font size="2">8,783 (15/03/24)</font>
    Elements yearLowestPrice = doc.select(JpxHtmlPathDefine.yearLowestPrice);
    // <font>タグを削除
    String tmp = yearLowestPrice.toString();
    String regex = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
    tmp = HtmlTextUtil.eraseMatchText(tmp, regex);
    regex = "\\((\"[^\"]*\"|'[^']*'|[^'\">])*\\)";
    tmp = HtmlTextUtil.eraseMatchText(tmp, regex);   
    // , を削除
    regex = ",";
    tmp = HtmlTextUtil.eraseMatchText(tmp, regex);
    // 空白削除
    tmp = tmp.trim();
    this.resultPrice.setYearLowestPrice(Float.parseFloat(tmp));
  }

  /**
   * 売買高を取得し、結果に格納する
   */
  private void setSalesAmount() {
    // 売買高 <font size="2">10,209,100株<br></font>
    Elements salesAmount = doc.select(JpxHtmlPathDefine.salesAmount);
    // <font>タグを削除
    String tmp = salesAmount.toString();
    String regex = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
    tmp = HtmlTextUtil.eraseMatchText(tmp, regex);
    regex = "株";
    tmp = HtmlTextUtil.eraseMatchText(tmp, regex);   
    // , を削除
    regex = ",";
    tmp = HtmlTextUtil.eraseMatchText(tmp, regex);
    // 空白削除
    tmp = tmp.trim();
    this.resultPrice.setSalesAmount(Long.parseLong(tmp));
  }

  /**
   * 売買代金を取得し、結果に格納する
   */
  private void setTradingValue() {
    // 売買代金 <font size="2">73,770,460,500<br></font>
    Elements tradingValue = doc.select(JpxHtmlPathDefine.tradingValue);
    // <font>タグを削除
    String tmp = tradingValue.toString();
    String regex = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
    tmp = HtmlTextUtil.eraseMatchText(tmp, regex);  
    // , を削除
    regex = ",";
    tmp = HtmlTextUtil.eraseMatchText(tmp, regex);
    // 空白削除
    tmp = tmp.trim();
    this.resultPrice.setTradingValue(Long.parseLong(tmp));
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

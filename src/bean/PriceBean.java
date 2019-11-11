package bean;

import java.util.Date;

/**
 * 株価情報オブジェクト
 * @author Yasumasa
 */
public class PriceBean {
  private Date priceDateTime;
  private Float nowPrice;
  private Float startPrice;
  private Float highestPrice;
  private Float lowestPrice;
  private Float yearHighestPrice;
  private Float yearLowestPrice;
  private Long salesAmount;
  private Long tradingValue;
  private Float marketCapitalization;

  /**
   * 設定されている株価情報をprintする
   */
  public void print() {
    String br = System.getProperty("line.separator");
    StringBuilder buff = new StringBuilder();
    buff.append("priceDateTime: ").append(this.getPriceDateTime()).append(br);
    buff.append("nowPrice: ").append(this.nowPrice).append(br);
    buff.append("startPrice: ").append(this.startPrice).append(br);
    buff.append("highestPrice: ").append(this.highestPrice).append(br);
    buff.append("lowestPrice: ").append(this.lowestPrice).append(br);
    buff.append("yearHighestPrice: ").append(this.yearHighestPrice).append(br);
    buff.append("yearLowestPrice: ").append(this.yearLowestPrice).append(br);
    buff.append("salesAmount: ").append(this.salesAmount).append(br);
    buff.append("tradingValue: ").append(this.tradingValue).append(br);
    buff.append("marketCapitalization: ").append(this.marketCapitalization).append(br);
    System.out.println(buff.toString());
  }
  
  /**
   * @return the nowPrice
   */
  public float getNowPrice() {
    return nowPrice;
  }

  /**
   * @param nowPrice the nowPrice to set
   */
  public void setNowPrice(float nowPrice) {
    this.nowPrice = nowPrice;
  }

  /**
   * @return the startPrice
   */
  public float getStartPrice() {
    return startPrice;
  }

  /**
   * @param startPrice the startPrice to set
   */
  public void setStartPrice(float startPrice) {
    this.startPrice = startPrice;
  }

  /**
   * @return the highestPrice
   */
  public float getHighestPrice() {
    return highestPrice;
  }

  /**
   * @param highestPrice the highestPrice to set
   */
  public void setHighestPrice(float highestPrice) {
    this.highestPrice = highestPrice;
  }

  /**
   * @return the lowestPrice
   */
  public float getLowestPrice() {
    return lowestPrice;
  }

  /**
   * @param lowestPrice the lowestPrice to set
   */
  public void setLowestPrice(float lowestPrice) {
    this.lowestPrice = lowestPrice;
  }

  /**
   * @return the yearHighestPrice
   */
  public float getYearHighestPrice() {
    return yearHighestPrice;
  }

  /**
   * @param yearHighestPrice the yearHighestPrice to set
   */
  public void setYearHighestPrice(float yearHighestPrice) {
    this.yearHighestPrice = yearHighestPrice;
  }

  /**
   * @return the yearLowestPrice
   */
  public float getYearLowestPrice() {
    return yearLowestPrice;
  }

  /**
   * @param yearLowestPrice the yearLowestPrice to set
   */
  public void setYearLowestPrice(float yearLowestPrice) {
    this.yearLowestPrice = yearLowestPrice;
  }

  /**
   * @return the salesAmount
   */
  public long getSalesAmount() {
    return salesAmount;
  }

  /**
   * @param salesAmount the salesAmount to set
   */
  public void setSalesAmount(long salesAmount) {
    this.salesAmount = salesAmount;
  }

  /**
   * @return the tradingValue
   */
  public long getTradingValue() {
    return tradingValue;
  }

  /**
   * @param tradingValue the tradingValue to set
   */
  public void setTradingValue(long tradingValue) {
    this.tradingValue = tradingValue;
  }

  /**
   * @return the marketCapitalization
   */
  public float getMarketCapitalization() {
    return marketCapitalization;
  }

  /**
   * @param marketCapitalization the marketCapitalization to set
   */
  public void setMarketCapitalization(float marketCapitalization) {
    this.marketCapitalization = marketCapitalization;
  }

  /**
   * @return the priceDateTime
   */
  public Date getPriceDateTime() {
    return priceDateTime;
  }

  /**
   * @param priceDateTime the priceDateTime to set
   */
  public void setPriceDateTime(Date priceDateTime) {
    this.priceDateTime = priceDateTime;
  }
}

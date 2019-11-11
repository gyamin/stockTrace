package common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * アプリケーション設定
 * @author nogami
 */
public class AppConfig {

  // アプリケーション設定プロパティ
  public static final String APP_PROPERTIES = "application.properties";
  // ログ設定プロパティ
  public static final String LOG_PROPERTIES = "logging.properties";

  /**
   * アプリケーションプロパティを返す
   * @return properties アプリケーションプロパティ
   */
  public static Properties getApplicationProperties() {
    Properties properties = new Properties();
    try {
      InputStream inputStream = AppConfig.class.getClassLoader().getResourceAsStream(APP_PROPERTIES);
      properties.load(inputStream);
      inputStream.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return properties;
  }
    
}

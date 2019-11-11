package common;

import java.io.IOException;
import java.util.logging.LogManager;

/**
 * アプリケーションログクラス
 * @author Yasumasa
 */
public class AppLogging {

  public static final String LOG_NAME = "application_log";

  /**
   * プロパティファイルからログ設定を行う
   */
  public static void setLoggingConfig() {
    try {
      LogManager.getLogManager().readConfiguration(
        AppLogging.class.getClassLoader().getResourceAsStream(AppConfig.LOG_PROPERTIES));
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

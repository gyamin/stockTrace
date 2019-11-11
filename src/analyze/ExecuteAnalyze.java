package analyze;

import analyze.pattern.PatternInterface;
import analyze.pattern.RateChangePattern1;
import common.DateUtil;
import common.AppConfig;
import common.MailSendUtil;
import common.AppLogging;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 株価解析処理実行
 */
public class ExecuteAnalyze {
    public static void main(String args[]) {
        // アプリケーションコンフィグ取得
        Properties appProperties = AppConfig.getApplicationProperties();

        // ロギング共通設定
        AppLogging.setLoggingConfig();

        PatternInterface rateChange = new RateChangePattern1();

        try {
            Logger.getLogger(AppLogging.LOG_NAME).log(Level.INFO, "解析処理 開始");
            rateChange.getAnalyzeResult();
            rateChange.outputResult();
            Logger.getLogger(AppLogging.LOG_NAME).log(Level.INFO, "解析処理 終了");

            // メールの送信
            Logger.getLogger(AppLogging.LOG_NAME).log(Level.INFO, "メール送信処理 開始");
            MailSendUtil mail = new MailSendUtil();

            // 送信先
            InternetAddress[] toAddress = new InternetAddress[1];
            toAddress[0] = new InternetAddress(appProperties.getProperty("ANALYZE_RESULT_MAIL_TOADDR"));
            mail.setToAddress(toAddress);

            // タイトル
            String title = DateUtil.getDateByFormat("yyy年MM月dd日 E曜日");
            title += " 株価変化率結果";

            mail.setSubject(title);
            // 本文
            StringBuilder text = new StringBuilder();
            text.append(appProperties.getProperty("ANALYZE_RESULT_MAIL_TOADDR"));
            text.append(System.getProperty("line.separator"));
            text.append(DateUtil.getDateByFormat("yyy年MM月dd日(E)"));
            text.append(" 株価変化率解析結果を送付します。");
            text.append(System.getProperty("line.separator"));

            mail.setMainText(text.toString());

            // 添付ファイル
            mail.setAttachFilePath(rateChange.getOutPutFilePath());

            mail.sendMail();
            Logger.getLogger(AppLogging.LOG_NAME).log(Level.INFO, "メール送信処理 終了");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AddressException e) {
            e.printStackTrace();
        }
    }
}

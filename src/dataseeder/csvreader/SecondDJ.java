package dataseeder.csvreader;

import bean.IssueBean;
import common.AppConfig;
import common.AppLogging;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * second-d-j.csvを読み込み、銘柄オブジェクト配列を作成する
 * @author Yasumasa
 */
public class SecondDJ implements IssueInterface {
  // データソースCSVファイル
  private final String fileName = "second-d-j.csv";

  /**
   * second-d-j.csvを読み込み、銘柄オブジェクト配列を作成し、リターンする
   */
  @Override
  public ArrayList<IssueBean> getImportBeansFromFile() {
    FileReader file = null;
    try {
      Properties appProperties = AppConfig.getApplicationProperties();
      file = new FileReader(System.getProperty("user.dir") + "/env/database/seeds/csv/" + fileName);
      BufferedReader br = new BufferedReader(file);
      String line;
      ArrayList<IssueBean> issues = new ArrayList<>();
      while((line = br.readLine()) != null) {
        // ,区切りで文字列を配列に格納
        String[] cols = line.split(",");
        IssueBean issue = new IssueBean();
        // issueオブジェクトに値を設定
        issue.section = "東証2部";
        issue.issue_code = Integer.parseInt(cols[1]);
        issue.issue_name = cols[2];
        issue.sector_code33 = Integer.parseInt(cols[3]);
        issues.add(issue);
      }
      return issues;
      
    } catch (FileNotFoundException ex) {
      String msg = this.fileName + " が存在しません。";
      Logger.getLogger(AppLogging.LOG_NAME).log(Level.SEVERE, msg , ex);
    } catch (IOException ex) {
      Logger.getLogger(AppLogging.LOG_NAME).log(Level.SEVERE, null , ex);
    } finally {
      try {
        file.close();
      } catch (IOException ex) {
        Logger.getLogger(AppLogging.LOG_NAME).log(Level.SEVERE, null , ex);
      }
    }
    return null;
  }
}

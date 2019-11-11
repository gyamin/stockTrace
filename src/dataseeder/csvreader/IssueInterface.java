package dataseeder.csvreader;

import bean.IssueBean;

import java.util.ArrayList;

/**
 * csv読み込みクラスインターフェイス
 * @author Yasumasa
 */
public interface IssueInterface {
  public ArrayList<IssueBean> getImportBeansFromFile();
}

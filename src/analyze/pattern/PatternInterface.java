package analyze.pattern;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 株価解析パターンインターフェイス
 */
public interface PatternInterface {
    public void getAnalyzeResult() throws IOException, SQLException;
    public void outputResult() throws IOException;
    public String getOutPutFilePath();
}

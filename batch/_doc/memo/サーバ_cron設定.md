# cron設定

## cron設定(log4j2でログを取得する)
```
$ crontab -e
...
## stocktrace crawling
30 00 * * * cd /opt/apps/stocktrace/; ~/.sdkman/candidates/java/current/bin/java -jar stockTrace-1.0-SNAPSHOT.jar crawling
```


## cron設定(標準出力、エラー出力をログに取得する)
```
$ crontab -e
...
## stocktrace crawling
30 00 * * * cd /opt/apps/stocktrace/; ~/.sdkman/candidates/java/current/bin/java -jar stockTrace-1.0-SNAPSHOT.jar crawling > stockTrace_exec.log 2>&1
```

## トラブル対応

### cronでコマンド実行のログが出ない
ubuntuではデフォルトログ出力されないとのこと。rsyslogの設定を変更する。
```
$ cd /etc/rsyslog.d/
$ sudo cp -p 50-default.conf 50-default.conf_$(date "+%Y%m%d_%H%M%S") 
$ sudo systemctl restart rsyslog

$ cat /var/log/cron.log 
Jun 11 11:16:01 ik1-418-41237 CRON[296023]: (ubuntu) CMD (cd /opt/apps/stocktrace/; java -jar stockTrace-1.0-SNAPSHOT.jar crawling > stockTrace_exec.log 2>&1)
```

### ログ出力されたけど、、0バイト
エラー出力がされていないから。2>&1 をつける。
```
$ ls -l
合計 14936
-rw-rw-r-- 1 ubuntu ubuntu      175  6月  9 00:59 application.properties
-rw-rw-r-- 1 ubuntu ubuntu   502946  6月  9 01:02 data_j.csv
-rwxrwxr-x 1 ubuntu ubuntu 14785562  6月 11 01:42 stockTrace-1.0-SNAPSHOT.jar
-rw-rw-r-- 1 ubuntu ubuntu        0  6月 11 10:59 stockTrace_exec.log
```

### java: not found
javaはsdkmanで設定しているので、cronでは~/.zshrcが読み込まれないので、コマンドが見つからない。
以下のようにjavaをフルパスで指定。
```
## stocktrace crawling
30 00 * * * cd /opt/apps/stocktrace/; ~/.sdkman/candidates/java/current/bin/java -jar stockTrace-1.0-SNAPSHOT.jar crawling > stockTrace_exec.log 2>&1
```
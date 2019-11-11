# 環境構築

## ミドルウェアインストール

### PostgreSQLのインストール

#### PosgreSQL9.3リポジトリ登録
```
# wget http://yum.postgresql.org/9.3/redhat/rhel-6-x86_64/pgdg-centos93-9.3-1.noarch.rpm
# rpm -ivh pgdg-centos93-9.3-1.noarch.rpm
```

#### インストール
[yumリポジトリ](http://yum.postgresql.org/repopackages.php)
```
# yum install postgresql93-server
```

#### データベースクラスタの作成
```
$ sudo su - postgres
$ /usr/pgsql-9.3/bin/initdb 
データベースシステム内のファイルの所有者は"postgres"ユーザでした。
このユーザがサーバプロセスを所有しなければなりません。
...
```

#### データベースの起動
```
$ sudo /etc/init.d/postgresql-9.3 start
postgresql-9.3 サービスを開始中:                           [  OK  ]
```

### Javaのインストール
[JDKのダウンロード](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
[JDKのインストール](http://docs.oracle.com/javase/jp/8/docs/technotes/guides/install/linux_jdk.html#BJFJJEFG)
```
$ ls -l
合計 204356
-rw-r--r-- 1 sysuser sysuser 160102255 11月 10 00:53 2015 jdk-8u65-linux-x64.rpm
$ sudo rpm -ivh jdk-8u65-linux-x64.rpm 
準備中...                ########################################### [100%]
   1:jdk1.8.0_65            ########################################### [100%]
Unpacking JAR files...
	tools.jar...
	plugin.jar...
	javaws.jar...
	deploy.jar...
	rt.jar...
	jsse.jar...
	charsets.jar...
	localedata.jar...
	jfxrt.jar...

$ java -version
java version "1.8.0_65"
Java(TM) SE Runtime Environment (build 1.8.0_65-b17)
Java HotSpot(TM) 64-Bit Server VM (build 25.65-b01, mixed mode)
```

## アプリケーション初期設定

### リポジトリ設定
```
$ pwd
/home/sysuser/batch
$ git clone git@bitbucket.org:Gyamin/stocktrace.git
...

```

### データベースの構築
`stockTrace/env/database/db_role.sql`を実行し、データーベスを作成する。

```
$ sudo su - postgres
$ psql
psql (9.3.10)
"help" でヘルプを表示します.

postgres=# CREATE ROLE developer WITH login password 'developerPwd';
CREATE ROLE
postgres=# CREATE DATABASE stock_trace owner=developer;
CREATE DATABASE

```

### テーブルの作成
```
$ id
uid=500(sysuser) gid=500(sysuser) 所属グループ=500(sysuser)
$ psql -U developer stock_trace;
psql (9.3.10)
"help" でヘルプを表示します.

stock_trace=> \i table.sql
CREATE TABLE
CREATE TABLE

stock_trace=> \dt
               リレーションの一覧
 スキーマ |     名前     |    型    |  所有者
----------+--------------+----------+-----------
 public   | issues       | テーブル | developer
 public   | stock_prices | テーブル | developer
```

### アプリケーションの更新
```
$ git fetch
$ git merge origin/master
Updating ef861ba..9f80890
...
```

### データベース接続設定変更

#### 設定変更
```
$ vim src/common/AppConfig.java 
  7 public class AppConfig {
  8     // データベース接続設定
  9     public static final String DB_HOST = "localhost";
 10     public static final String DB_PORT = "5432";
 11     public static final String DB_USER = "developer";
 12     public static final String DB_PASSWD = "";
 13     public static final String DB_NAME = "stock_trace";
 ...
```
### 処理の実行

#### 銘柄マスタ登録
```
$ java -classpath stockTrace.jar:./lib/* dataseeder.ExecuteDataSeed

```
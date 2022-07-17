#!/bin/bash

# 初期化
shellName=$(basename $0)
execDir=$(cd $(dirname $0); pwd)
rootDir=$(cd ${execDir}; cd "./.."; pwd)

# DB接続設定
dbHost="localhost"
dbUser="stocktrace"
dbPort="15432"
dbName="loc_stocktrace"

function execSql() {
    sqlFilePath=$1
    dateTime=$(date "+%Y/%m/%d %H:%M:%S")
    echo "${dateTime}: ${sqlFilePath}"
    psql -h ${dbHost} -U ${dbUser} -d ${dbName} -f ${sqlFilePath}
}

# ---------------------------
# Main
# ---------------------------

## パラメータチェック
### 名前付きパラメータ取得 -s=sql, -t=type
while getopts s:t: OPT
do
  case $OPT in
    s) sql=$OPTARG
       ;;
    p) type=$OPTARG
       ;;
  esac
done

### 実行sqlファイル確認
if [ -z "${sql}" ];then
  echo "Usage: ${fileName} [-s sql_file_name]" 1>&2
  exit 1
fi

sqlFilePath=$(find ${execDir}/sql -name "${sql}.sql")

if [ -z "${sqlFilePath}" ];then
  echo "${sql} does not exits" 1>&2
  exit 1
fi

## 処理実行
execSql ${sqlFilePath}
# ---------------------------
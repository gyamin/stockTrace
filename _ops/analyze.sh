#!/bin/bash

# 初期化
shellName=$(basename $0)
execDir=$(cd $(dirname $0); pwd)
rootDir=$(cd ${execDir}; cd "./.."; pwd)

# ---------------------------
# Main
# ---------------------------

## パラメータチェック
### 名前付きパラメータ取得 -s=sql, -t=type
while getopts s:t:e: OPT
do
  case $OPT in
    s) sql=$OPTARG
       ;;
    t) type=$OPTARG
       ;;
    e) env=$OPTARG
       ;;
  esac
done

### 実行sqlファイル確認
if [ -z "${sql}" ]; then
  echo "Usage: ${fileName} [-s sql_file_name]" 1>&2
  exit 1
fi

sqlFilePath=$(find ${execDir}/sql -name "${sql}.sql")

if [ -z "${sqlFilePath}" ];then
  echo "${sql}.sql file does not exits" 1>&2
  exit 1
fi

### env確認
if [[ ! "${env}" =~ dev|stg|prd ]]; then
  echo "-e must be dev|stg|prd" 1>&2
  exit 1
fi

source .pgenv.${env}

## 処理実行
dateTime=$(date "+%Y%m%d_%H%M%S")
psql -h ${DB_HOST} -U ${DB_USER} -d ${DB_NAME} -f ${sqlFilePath} -A -F,
# ---------------------------
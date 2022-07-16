#!/bin/bash

# 設定
shellName=$(basename $0)
execDir=$(cd $(dirname $0); pwd)
rootDir=$(cd ${execDir}; cd "./.."; pwd)
deployDir="/opt/apps/stocktrace"

function message() {
    body=$1
    dateTime=$(date "+%Y/%m/%d %H:%M:%S")
    echo "${dateTime}: ${body}"
}

# ビルド処理
function build() {
  env=$1
  message "[${shellName}] build...${env}"

  cd ${rootDir}
  ./gradlew build -x test
  ret=$?
  if [ ${ret} -ne 0 ]; then
    echo "ビルドに失敗しました。"
    exit 9
  fi
  message "ビルド完了"

  # jarファイルの配置(上書き)
  jarDir="${rootDir}/build/libs"
  pgName=$(find ${jarDir} -name "stockTrace-[0-9].[0-9]-SNAPSHOT.jar" | xargs basename)
  rm -f ${deployDir}/${pgName}
  cp -f ${jarDir}/${pgName} ${deployDir}/${pgName}
  message "jarファイル配置完了"

  # プロパティファイル配置(上書き)
  rm -f ${deployDir}/application.properties
  cp -f ${rootDir}/application.properties.${env} ${deployDir}/application.properties
  message "プロパティファイル配置完了"
}

# メイン処理
# ---------------------------
## パラメータチェック
while getopts e: OPT
do
  case $OPT in
    "e" ) env="$OPTARG"  ;;
    * ) echo "Usage: ${fileName} [-e dev|stg|prd]" 1>&2
    exit 1 ;;
  esac
done

if [ -z "${env}" ];then
  echo "Usage: ${fileName} [-e dev|stg|prd]" 1>&2
  exit 1
fi

## 処理実行
build ${env}
# ---------------------------
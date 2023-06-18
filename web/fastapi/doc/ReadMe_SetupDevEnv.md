# 開発環境セットアップ(開発者環境)

## Python FastAPI

### 前提
- 端末: MacOSを想定
- Pyenvがインストールされ、利用するバージョンのpythonがインストールされている

### セットアップ

python
```
% pwd
../stocktrace/web/fastapi/src
% pyenv local 3.10.10
% python -V
Python 3.10.10
```

venv
```
% python -m venv .venv
% source .venv/bin/activate
```

FastAPI導入
```
% pip install fastapi
% pip install 'uvicorn[standard]'
```

### FastAPIアプリケーション実行
起動
```
% uvicorn main:app --reload
INFO:     Will watch for changes in these directories: ['/Users/nogami/Develop/repos/gyamin/stocktrace/web/fastapi/src']
INFO:     Uvicorn running on http://127.0.0.1:8000 (Press CTRL+C to quit)
INFO:     Started reloader process [20180] using WatchFiles
...
```

アクセス
```
% curl http://127.0.0.1:8000                   
{"Hello":"World"}
% curl "http://127.0.0.1:8000/items/5?q=fafafa"
{"item_id":5,"q":"fafafa"}%
```


## PyCharmセットアップ
IDEとしてPyCharmを利用する場合のセットアップ方法

### Python Interpreter 設定
Cmd + , で、Setting画面を表示、Python Interpreter で以下のようvenvで設定したPython環境を指定する。
![2023-05-21 13.42.34.png](img%2F2023-05-21%2013.42.34.png)
![2023-05-21 13.42.54.png](img%2F2023-05-21%2013.42.54.png)


### 実行設定
Edit Configuration から、以下のようにmain.pyを実行するように設定する。
(FastAPIもPyCharmでサポートされている)
![2023-05-21 13.48.23.png](img%2F2023-05-21%2013.48.23.png)

Run / Debug 実行
上記設定ができたら、PyCharmよりプログラム実行
```..repos/gyamin/stocktrace/web/fastapi/src/.venv/bin/python /Users/nogami/Library/Application Support/JetBrains/Toolbox/apps/PyCharm-P/ch-0/231.9011.38/PyCharm.app/Contents/plugins/python/helpers/pydev/pydevd.py --module --multiprocess --qt-support=auto --client 127.0.0.1 --port 62338 --file uvicorn web.fastapi.src.main:app --reload 
Connected to pydev debugger (build 231.9011.38)
INFO:     Will watch for changes in these directories: ['/Users/nogami/Develop/repos/gyamin/stocktrace']
INFO:     Uvicorn running on http://127.0.0.1:8000 (Press CTRL+C to quit)
```

### ブレークポイント設定
ブレークポイント設定して、Debug実行、HTTPクラアントからアクセスして、該当箇所を実行するとデバッグできる。
![2023-05-21 13.53.49.png](img%2F2023-05-21%2013.53.49.png)



### pipパッケージ管理


```
% pip install fastapi
% pip install 'uvicorn[standard]'
% pip install jinja2
% pip install sqlalchemy
% pip install psycopg2-binary
% pip install python-dotenv
% pip install python-multipart
```

#### パッケージ一覧をファイル出力
```
% pip freeze > requirements.txt
```

#### パッケージ一覧ファイルからパッケージインストール
```
% pip install -r requirements.txt
```

## webフロント開発

### CSS開発
sass導入
```
% cd assets
% nodenv local 16.20.0
% node -v
v16.20.0

% npm init -y 
% npm install --save-dev webpack webpack-cli
% npm install --save-dev css-loader sass-loader sass mini-css-extract-plugin
```

### JS開発
vue導入
```
% npm install --save-dev vue
% npm install --save-dev @babel/core @babel/preset-env babel-loader
```


### ビルド
```
% npm run build
...
  ./src/index.js 55 bytes [built] [code generated]
  css ./node_modules/css-loader/dist/cjs.js!./node_modules/sass-loader/dist/cjs.js!./src/css/header.scss 26 bytes [built] [code generated]
  css ./node_modules/css-loader/dist/cjs.js!./node_modules/sass-loader/dist/cjs.js!./src/css/footer.scss 26 bytes [built] [code generated]
webpack 5.74.0 compiled successfully in 303 ms
```

## dokcer環境

### flaskとdbの2コンテナ起動
```
% docker-compose up -d  
...
[+] Running 2/2
 ✔ Container stock_trace_python  Started                                                                                                                                                                                        0.3s 
 ✔ Container stock_trace_db      Started   
```

### dbコンテナのみ起動
```
% docker-compose up -d stock_trace_db
...
[+] Running 2/2
 ✔ Network docker_default    Created                                                                                                                                                                                            0.0s 
 ✔ Container stock_trace_db  Started   
```
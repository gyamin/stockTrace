# トラブルシューティング

## PhCharmで実行時にパッケージ解決がうまくされない

### 問題
ルーティング設定を外に出そうとして、実行すると
```
from fastapi import FastAPI
from router import web

app = FastAPI()
app.include_router(web.router)
```

routerという名前のモジュールはないとエラー表示
```
/Users/xxx/Develop/repos/gyamin/stocktrace/web/fastapi/src/.venv/bin/python /Users/nogami/Library/Application Support/JetBrains/Toolbox/apps/PyCharm-P/ch-0/231.9011.38/PyCharm.app/Contents/plugins/python/helpers/pydev/pydevd.py --module --multiprocess --qt-support=auto --client 127.0.0.1 --port 63388 --file uvicorn web.fastapi.src.main:app --reload 
Connected to pydev debugger (build 231.9011.38)
...
INFO:     Will watch for changes in these directories: ['/Users/xxx/Develop/repos/gyamin/stocktrace']
  File "/Users/xxx/Develop/repos/gyamin/stocktrace/web/fastapi/src/main.py", line 5, in <module>
    from router import web
ModuleNotFoundError: No module named 'router'
```

### 解決方法
- 実行設定でWorking Directory を main.py と同じディレクトリに設定する。
- Project Structure で src ディレクトリを Sourceに設定する。
![2023-05-21 16.14.14.png](img%2F2023-05-21%2016.14.14.png)
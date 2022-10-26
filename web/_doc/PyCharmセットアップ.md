# PyCharmセットアップ

## インタープリタにpyenvを利用する
Preference > Project > Python Interpreter を開き、Add Interpreter から
Add Local Interpreterを選択する。
![](img/2022-10-26_15.45.51.png)

pyenvで構築したpython環境を指定する。
![](img/2022-10-26_15.46.06.png)

※指定したpython環境に必要なライブラリなどをインストールしておくこと。
```
(stocktrace) % pyenv versions
  system
  3.10.7
  3.10.7/envs/stocktrace
* stocktrace (set by /Users/xxx/xxx/stocktrace/web/src/.python-version)
% pip list                   
Package         Version
--------------- -------
click           8.1.3
Flask           2.2.2
itsdangerous    2.1.2
Jinja2          3.1.2
MarkupSafe      2.1.1
pip             22.3
psycopg2-binary 2.9.5
python-dotenv   0.21.0
setuptools      63.2.0
SQLAlchemy      1.4.42
Werkzeug        2.2.2
```

ブレークポイントを設定し、該当箇所を呼び出すとデバッグできる。
![](img/2022-10-26_16.13.24.png)

## インタープリタにDockerコンテナを利用する
Preference > Project > Python Interpreter を開き、Add Interpreter から
Add On Docker Compose... を選択する。
![](img/2022-10-26_16.14.33.png)

docker-compose.yml ファイルを指定する。
![](img/2022-10-26_16.15.43.png)

Serviceで対象のコンテナを選択する。
![](img/2022-10-26_16.15.51.png)
![](img/2022-10-26_16.15.59.png)

System Interpreterを選択すればいいみたい。
![](img/2022-10-26_16.16.03.png)

Dokcer Compose インタープリターが選択される。
![](img/2022-10-26_16.16.12.png)

Run/Debug設定でインタープリタに設定したコンテナを指定し、Additional Optionsでport設定をDockerfile設定と合わせる。
![](img/2022-10-26_16.16.57.png)

infra/docker/python/Dockerfile
```
...
WORKDIR /app
EXPOSE 5001
CMD ["python", "app.py"]
```

infra/docker/docker-compose.yaml
```
  stock_trace_python :
    container_name: stock_trace_python
    build: ./python
    volumes:
      - ./../../web/src:/app
    ports:
      - "5001:5001"
```

ブレークポイントを設定して、Debugを開始する。
![](img/2022-10-26_16.36.37.png)
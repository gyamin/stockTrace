# PyCharmセットアップ

## インタープリタにpyenvを利用する
Preference > Project > Python Interpreter を開き、Add Interpreter から
Add Local Interpreterを選択する。
![](img/2022-10-26_15.45.51.png)

プロジェクトに作成した.venvディレクトリを指定
![](img%2F2023-04-26%201.14.34.png)


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
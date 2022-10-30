# SQLAlchemyナレッジ

## SAWarning: implicitly coercing SELECT object to scalar subquery

### 概要
`.where(tbl_stock_values.c.trading_date == select(func.max(tbl_stock_values.c.trading_date)))` と副問い合わせを行う
SQLを指定したところ、タイトルの警告が表示された。

#### 実装
```
    s = select(
        tbl_stock_values.c.trading_date,
        ...
    ).where(tbl_stock_values.c.trading_date == select(func.max(tbl_stock_values.c.trading_date)))
    j = join(tbl_stock_values, tbl_items, tbl_stock_values.c.code == tbl_items.c.code)

    stmt = s.select_from(j).order_by("ratio").limit(num)

    rows = self.conn.execute(stmt).fetchall()
```

#### ワーニング
```
sotck_values.py:67: SAWarning: implicitly coercing SELECT object to scalar subquery; please use the .scalar_subquery() method to produce a scalar subquery.
  ).where(tbl_stock_values.c.trading_date == select(func.max(tbl_stock_values.c.trading_date)))
```

### 対応方法
以下のように`.scalar_subquery()`でサブクエリであることを明示しておく
```
max_date = select(func.max(tbl_stock_values.c.trading_date)).scalar_subquery()

s = select(
    tbl_stock_values.c.trading_date,
    ...
).where(tbl_stock_values.c.trading_date == max_date)
```
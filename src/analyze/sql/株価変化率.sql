SELECT
  section,
  issue_code,
  issue_name,
  today,
  (1 - round((yesterday / today), 4)) * 100 AS  "yesterday",
  (1 - round((five / today), 4)) * 100  AS "five",
  (1 - round((ten / today), 4)) * 100  AS "ten",
  (1 - round((twenty / today), 4)) * 100  AS "twenty",
  (1 - round((thirty / today), 4)) * 100  AS "thirty",
  (1 - round((forty / today), 4)) * 100  AS "forty",
  (1 - round((fifty / today), 4)) * 100  AS "fifty",
  (1 - round((sixty / today), 4)) * 100  AS "sixty",
  (1 - round((ninety / today), 4)) * 100  AS "ninety",
  (1 - round((hundredtwenty / today), 4)) * 100  AS "hundredtwenty",
  (1 - round((hundredfifty / today), 4)) * 100  AS "hundredfifty"
FROM
  (
    SELECT
      issues.section AS "section",
      stock_prices.issue_code AS "issue_code",
      max(issues.issue_name)  AS "issue_name",
      max(CASE stock_prices.trade_date
          WHEN ?
            THEN stock_prices.now_price
          ELSE NULL END)      AS "today",
      max(CASE stock_prices.trade_date
          WHEN ?
            THEN stock_prices.now_price
          ELSE NULL END)      AS "yesterday",
      max(CASE stock_prices.trade_date
          WHEN ?
            THEN stock_prices.now_price
          ELSE NULL END)      AS "five",
      max(CASE stock_prices.trade_date
          WHEN ?
            THEN stock_prices.now_price
          ELSE NULL END)      AS "ten",
      max(CASE stock_prices.trade_date
          WHEN ?
            THEN stock_prices.now_price
          ELSE NULL END)      AS "twenty",
      max(CASE stock_prices.trade_date
          WHEN ?
            THEN stock_prices.now_price
          ELSE NULL END)      AS "thirty",
      max(CASE stock_prices.trade_date
          WHEN ?
            THEN stock_prices.now_price
          ELSE NULL END)      AS "forty",
      max(CASE stock_prices.trade_date
          WHEN ?
            THEN stock_prices.now_price
          ELSE NULL END)      AS "fifty",
      max(CASE stock_prices.trade_date
          WHEN ?
            THEN stock_prices.now_price
          ELSE NULL END)      AS "sixty",
      max(CASE stock_prices.trade_date
          WHEN ?
            THEN stock_prices.now_price
          ELSE NULL END)      AS "ninety",
      max(CASE stock_prices.trade_date
          WHEN ?
            THEN stock_prices.now_price
          ELSE NULL END)      AS "hundredtwenty",
      max(CASE stock_prices.trade_date
          WHEN ?
            THEN stock_prices.now_price
          ELSE NULL END)      AS "hundredfifty"
    FROM stock_prices
      INNER JOIN issues
        ON stock_prices.issue_code = issues.issue_code
    WHERE stock_prices.trade_date IN ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    GROUP BY
      issues.section,
      stock_prices.issue_code
  ) AS BASE
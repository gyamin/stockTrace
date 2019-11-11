CREATE TABLE issues (
    section         VARCHAR(100) ,
    issue_code      INTEGER ,
    issue_name      VARCHAR(1000) NOT NULL,
    sector_code33   INTEGER NOT NULL ,
    sector_code17   INTEGER ,
    size_code       VARCHAR(3) ,
    CONSTRAINT section_issue_code PRIMARY KEY(section, issue_code)
);

CREATE TABLE stock_prices (
    trade_date          DATE NOT NULL ,
    issue_code          INTEGER NOT NULL ,
    now_price           NUMERIC(11,2) ,
    start_price         NUMERIC(11,2) ,
    highest_price       NUMERIC(11,2) ,
    lowest_price        NUMERIC(11,2) ,
    year_hgihest_price  NUMERIC(11,2) ,
    year_lowest_price   NUMERIC(11,2) ,
    sales_amount        NUMERIC(15) ,
    trading_value       NUMERIC(15) ,
    market_capitalization  NUMERIC(20,2) ,
    CONSTRAINT create_date_issue_code PRIMARY KEY(trade_date, issue_code)
);

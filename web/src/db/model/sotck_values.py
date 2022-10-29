from sqlalchemy import Table, Column, Integer, String, MetaData, ForeignKey, text, join
from sqlalchemy.types import Date, DateTime, Numeric
from sqlalchemy.sql import select
from db.model.items import Items


class StockValues:

    def __init__(self, conn):
        self.conn = conn
        metadata = MetaData()
        self.tbl_stock_values = Table('stock_values', metadata,
                                      Column('code', String, primary_key=True),
                                      Column('trading_date', Date, primary_key=True),
                                      Column('current_price', Numeric(11, 1)),
                                      Column('the_day_before_price', Numeric(11, 1)),
                                      Column('start_price', Numeric(11, 1)),
                                      Column('high_price', Numeric(11, 1)),
                                      Column('low_price', Numeric(11, 1)),
                                      Column('year_high_price', Numeric(11, 1)),
                                      Column('year_high_price_date', Date),
                                      Column('year_low_price', Numeric(11, 1)),
                                      Column('year_low_price_date', Date),
                                      Column('trading_unit', String),
                                      Column('created_at', DateTime),
                                      Column('updated_at', DateTime),
                                      )

    def get_rate_up_top_n(self, num):
        sql = text(
            """
            select
                trading_date,
                i.code,
                trading_name,
                current_price,
                (current_price - the_day_before_price) as diff,
                ((current_price - the_day_before_price) / current_price) * 100 ratio
            from stock_values sv
            inner join items i on sv.code = i.code
            where trading_date = (select MAX(trading_date) from stock_values)
                and (current_price - the_day_before_price) is not null
            order by ratio desc
            limit :num
        """
        )

        rows = self.conn.execute(sql, {"num": num}).fetchall()

        return rows

    def get_rate_down_bottom_n(self, num):
        tbl_stock_values = self.tbl_stock_values
        tbl_items = Items(self.conn).tbl_items
        s = select(
            tbl_stock_values.c.trading_date,
            tbl_items.c.code,
            tbl_items.c.trading_name,
            tbl_stock_values.c.current_price
        ).where(tbl_stock_values.c.trading_date == '2022-10-20')
        j = join(tbl_stock_values, tbl_items, tbl_stock_values.c.code == tbl_items.c.code)
        stmt = s.select_from(j)
        print(stmt)
        rows = self.conn.execute(stmt).fetchall()

        return rows

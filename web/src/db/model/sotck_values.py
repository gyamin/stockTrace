from sqlalchemy import Table, Column, Integer, String, MetaData, ForeignKey, text
from sqlalchemy.types import Date, DateTime, Numeric
from sqlalchemy.sql import select


class StockValues:

    def __init__(self, conn):
        self.conn = conn
        metadata = MetaData()
        self.postalCode = Table('stock_values', metadata,
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

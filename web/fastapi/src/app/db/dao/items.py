from sqlalchemy import Table, Column, Integer, String, MetaData, ForeignKey, text
from sqlalchemy.types import Date, DateTime, Numeric


class Items:

    def __init__(self, conn):
        self.conn = conn
        metadata = MetaData()
        self.tbl_items = Table('items', metadata,
                               Column('code', String, primary_key=True),
                               Column('trading_name', String),
                               Column('market_type', String),
                               Column('sector33_code', Integer),
                               Column('sector33_name', String),
                               Column('sector17_code', Integer),
                               Column('sector17_name', String),
                               Column('scale_code', Integer),
                               Column('scale_name', String),
                               Column('created_at', DateTime),
                               Column('updated_at', DateTime),
                               )

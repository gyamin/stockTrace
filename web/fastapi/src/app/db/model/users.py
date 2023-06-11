from sqlalchemy import Table, Column, Integer, String, MetaData, ForeignKey, text
from sqlalchemy.types import Date, DateTime, Numeric


class Users:

    def __init__(self, conn):
        self.conn = conn
        metadata = MetaData()
        self.tbl_users = Table('users', metadata,
                               Column('user_id', String, primary_key=True),
                               Column('password', String),
                               Column('family_name', String),
                               Column('first_name', String),
                               Column('birth_day', DateTime),
                               Column('email', String),
                               Column('telephone', String),
                               Column('created_at', DateTime),
                               Column('updated_at', DateTime),
                               Column('deleted_at', DateTime)
                               )

from sqlalchemy import Table, Column, Integer, String, MetaData, ForeignKey, text
from sqlalchemy.types import Date, DateTime, Numeric
from sqlalchemy.sql import select


class UserAuthInfo:
    metadata = MetaData()
    meta_user_auth_info = Table('user_auth_info', metadata,
                                Column('user_id', String, primary_key=True),
                                Column('access_token', String),
                                Column('access_token_expired_at', DateTime),
                                Column('session_id', String),
                                Column('session_id_expired_at', DateTime)
                                )

    def __init__(self, conn):
        self.conn = conn

    def get_user_token(self, access_token: String):
        sql = text(
            """
            select
            from user_auth_info uai
            inner join users u on uai.user_id = u.user_id
            where uai.access_token = :access_token
            limit 1
        """
        )

        rows = self.conn.execute(sql, {"access_token": access_token}).fetchall()
        return rows

    def get_user_session(self, session_id: String):
        sql = text(
            """
            select
            from user_auth_info ut
            inner join users u on ut.user_id = u.user_id
            where ut.session_id = :session_id
            limit 1
        """
        )

        rows = self.conn.execute(sql, {"session_id": session_id}).fetchall()
        return rows

    def create_user_token(self, user_auth_info: {}):
        ret = self.conn.execute(self.meta_user_auth_info.insert(), user_auth_info)
        print(ret)

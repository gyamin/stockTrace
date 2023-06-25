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

    def get_user_by_id_pw(self, login_id: String, password: String):
        """
        id, pwが合致するユーザを取得する
        :param login_id:
        :param password:
        :return:
        """

        sql = text(
            """
            select
            *
            from users
            where login_id = :login_id
            and password = :password
            """
        )

        rows = self.conn.execute(sql, {"login_id": login_id, "password": password}).fetchall()
        return rows

    def get_user_by_id(self, user_id: str):
        """
        :param user_id:
        :return:
        """
        sql = """
            select * from users where user_id = :user_id
        """

        rows = self.conn.execute(text(sql), {"user_id": user_id}).fetchall()
        return rows

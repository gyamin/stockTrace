import datetime
from fastapi import Request, Cookie
from typing import Union
from app.http.exceptions.AuthenticationPageException import AuthenticationPageException
from app.http.exceptions.ApplicationException import ApplicationException
from app.db import database
from app.com.logging import logger
from app.db.dao.user_auth_info import UserAuthInfo
from app.db.dao.users import Users


async def check_token(request: Request):
    token = request.headers.get("authorization")

    if token is None:
        raise AuthenticationPageException("Authentication Failed token is not specified.")

    # ユーザ特定
    token = token.replace('Bearer ', '')
    conn = database.engine.connect()
    rows = UserAuthInfo(conn).get_user_token(token)
    if len(rows) < 1:
        raise AuthenticationPageException("Authentication Failed token:" + token + '" does not exist.')
    user_tokens = database.convert_dic(rows)
    print(user_tokens)


async def check_session_id(request: Request, session_id: Union[str, None] = Cookie(default=None)):

    if not session_id:
        raise ApplicationException('0401', "Authentication Failed session_id does not exist.")

    # ユーザ特定
    logger.info('session_id:' + session_id)
    conn = database.engine.connect()
    model_user_auth_info = UserAuthInfo(conn)
    rows = model_user_auth_info.get_user_session(session_id)

    if len(rows) < 1:
        # セッションidに合致するレコードがない
        raise ApplicationException('0401', "Authentication Failed there is no row that matches session_id")

    user_auth_info = database.convert_dic(rows)[0]
    logger.info(user_auth_info)

    if user_auth_info['session_id_expired_at'] < datetime.datetime.now():
        # セッションidが有効期限切れ
        raise AuthenticationPageException('Authentication Failed session_id is expired.')
    else:
        #   セッション時間を毎回DB更新しないため、一定単位に丸め処理した時間でセッション時間を管理する
        extended_session_id_expired_at = get_expired_datetime(user_auth_info['session_id_expired_at'])
        if user_auth_info['session_id_expired_at'] != extended_session_id_expired_at:
            # セッション切れ時間延長
            user_auth_info['session_id_expired_at'] = extended_session_id_expired_at
            model_user_auth_info.create_or_update_user_token(user_auth_info)
            conn.commit()

    # request.__userにユーザ情報を埋め込む
    model_users = Users(conn)
    rows = model_users.get_user_by_id(user_auth_info['user_id'].hex)
    user = database.convert_dic(rows)[0]
    request.__user = user
    return user_auth_info


def get_expired_datetime(session_id_expired_at: datetime):
    # セッション時間延長
    new_session_id_expired_at = datetime.datetime.now() + datetime.timedelta(minutes=120)
    # 丸め時間単位
    round_minutes: int = 15
    # 丸め時間単位で丸める
    rounded_time = new_session_id_expired_at.replace(
        minute=new_session_id_expired_at.minute - new_session_id_expired_at.minute % round_minutes,
        second=0,
        microsecond=0
    )

    return rounded_time

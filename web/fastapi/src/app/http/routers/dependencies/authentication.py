import datetime
from fastapi import Request, Cookie
from typing import Union
from app.http.exceptions.AuthenticationPageException import AuthenticationPageException
from app.db import database
from app.com.logging import logger
from app.db.dao.user_auth_info import UserAuthInfo


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
        raise AuthenticationPageException("Authentication Failed session_id does not exist.")

    # ユーザ特定
    logger.info('session_id:' + session_id)
    conn = database.engine.connect()
    rows = UserAuthInfo(conn).get_user_session(session_id)
    if len(rows) < 1:
        # セッションidに合致するレコードがない
        raise AuthenticationPageException('Authentication Failed session_id does not exist.')

    user = database.convert_dic(rows)
    logger.info(user[0])

    if user[0]['session_id_expired_at'] < datetime.datetime.now():
        # セッションidが有効期限切れ
        raise AuthenticationPageException('Authentication Failed session_id is expired.')

    request.__user = user[0]
    return user[0]

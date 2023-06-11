from fastapi import Request, Cookie
from typing import Union
from app.exceptions.AuthenticationPageException import AuthenticationPageException
from app.db import database
from app.db.model.user_auth_info import UserAuthInfo


async def check_token_header(request: Request):
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


async def check_session_id(session_id: Union[str, None] = Cookie(default=None)):
    if not session_id:
        raise AuthenticationPageException("Authentication Failed session_id does not exist.")

    # ユーザ特定
    conn = database.engine.connect()
    rows = UserAuthInfo(conn).get_user_session(session_id)
    if len(rows) < 1:
        raise AuthenticationPageException("Authentication Failed session_id:" + session_id + '" does not exist.')
    user_session_id = database.convert_dic(rows)
    print(user_session_id)

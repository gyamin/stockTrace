from fastapi.requests import Request
import datetime
from starlette import status
from fastapi.responses import RedirectResponse
from fastapi.templating import Jinja2Templates
from app.db import database
from app.db.dao import users, user_auth_info
from app.com import auth_tool
from app.http.exceptions.ApplicationException import ApplicationException

templates = Jinja2Templates(directory="app/templates")


class LoginService:
    @staticmethod
    def login(request, login_id, password):

        # パスワードハッシュ化
        hs = auth_tool.get_hash(password)

        # id/pwに合致するユーザ検索
        conn = database.engine.connect()
        model_users = users.Users(conn)
        users_rs = model_users.get_user_by_id_pw(login_id, hs)

        if len(users_rs) < 1 or len(users_rs) > 1:
            response = templates.TemplateResponse("login.html", {'request': request, 'message': 'ID/PWに誤りがあります。'})
            return response

        if len(users_rs) == 1:
            # ユーザが存在した場合
            user = users_rs[0]

            # トークン、セッション生成
            token = auth_tool.create_random_string()
            session = auth_tool.create_random_string()

            # 有効期間設定
            current_time = datetime.datetime.now()
            expired_at = current_time + datetime.timedelta(days=1)

            model_user_auth_info = user_auth_info.UserAuthInfo(conn)

            user_token = {
                "user_id": user.user_id,
                "access_token": token,
                "access_token_expired_at": expired_at,
                "session_id": session,
                "session_id_expired_at": expired_at,
            }

            ret = model_user_auth_info.create_or_update_user_token(user_token)
            conn.commit()

            response = RedirectResponse('/auth/top', status_code=status.HTTP_302_FOUND)
            response.set_cookie(key="session_id", value=user_token.get('session_id'))
            response.set_cookie(key="session_id_expired_at", value=user_token.get('session_id_expired_at'))

            return response

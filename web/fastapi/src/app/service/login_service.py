from fastapi.requests import Request
import datetime
from app.db import database
from app.db.model import users, user_auth_info
from app.com import auth_tool


class LoginService:
    @staticmethod
    def login(self, request: Request):
        login_id = request.get('login_id')
        # パスワードハッシュ化
        hs = auth_tool.get_hash(request.get('password'))

        conn = database.engine.connect()

        model_users = users.Users(conn)
        user = model_users.get_user_by_id_pw(login_id, hs)

        if len(user) == 1:
            # ユーザが存在した場合
            # トークン、セッション生成
            token = auth_tool.create_random_string()
            session = auth_tool.create_random_string()

            # 有効期間設定
            current_time = datetime.datetime.now()
            expired_at = current_time + datetime.timedelta(days=1)

            model_user_auth_info = user_auth_info.UserAuthInfo(conn)
            model_user_auth_info.create_user_token(
                {
                    "user_id": user.user_id,
                    "access_token": token,
                    "access_token_expired_at": expired_at,
                    "session_id": session,
                    "session_id_expired_at": expired_at,
                }
            )

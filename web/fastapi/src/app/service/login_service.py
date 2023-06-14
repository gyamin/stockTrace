from fastapi.requests import Request
import datetime
from app.db import database
from app.db.dao import users, user_auth_info
from app.com import auth_tool


class LoginService:
    @staticmethod
    def login(login_id, password):
        # パスワードハッシュ化
        hs = auth_tool.get_hash(password)

        conn = database.engine.connect()

        model_users = users.Users(conn)
        users_rs = model_users.get_user_by_id_pw(login_id, hs)

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
            ret = model_user_auth_info.create_user_token(
                {
                    "user_id": user.user_id,
                    "access_token": token,
                    "access_token_expired_at": expired_at,
                    "session_id": session,
                    "session_id_expired_at": expired_at,
                }
            )
            conn.commit()

            return ret






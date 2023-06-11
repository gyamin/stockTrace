from fastapi.requests import Request
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
            # ユーザが存在、セッション生成
            token = auth_tool.create_token_string()

            model_user_auth_info = user_auth_info.UserAuthInfo(conn)
            user_auth_info = User
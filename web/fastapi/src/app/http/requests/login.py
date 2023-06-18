from pydantic import BaseModel


class LoginForm(BaseModel):
    login_id: str
    password: str

from fastapi import Request
from app.exceptions.AuthenticationException import AuthenticationException


async def check_token_header(request: Request):
    x_token = request.headers.get("x-token")
    if x_token is None:
        raise AuthenticationException("Authentication Failed")

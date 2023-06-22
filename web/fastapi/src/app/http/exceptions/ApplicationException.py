from fastapi import status
from fastapi.requests import Request
from fastapi.responses import Response, RedirectResponse, JSONResponse
from fastapi.templating import Jinja2Templates

templates = Jinja2Templates(directory="app/templates")


class ApplicationException(Exception):
    def __init__(self, code: str = '0000', debug_message: str = ""):
        self.code = code
        self.debug_message = debug_message

        self.code_map = {
            '0000': {'message': "", 'status': status.HTTP_400_BAD_REQUEST},
            '0001': {'message': "ログインID、パスワードに誤りがあります。", 'status': status.HTTP_400_BAD_REQUEST},
            '0401': {'message': "このページにはアクセスできません。再度ログインしてください。", 'status': status.HTTP_403_FORBIDDEN},
        }

    def treat_exception(self, request: Request):
        # ApplicationException 共通処理
        message = self.code_map[self.code]['message']
        status = self.code_map[self.code]['status']
        content_type = request.headers.get("content-type")
        if content_type == "application/json":
            return JSONResponse(
                content={'code': self.code, 'message': message, 'debug_message': self.debug_message}, status_code=status
            )
        else:
            return templates.TemplateResponse(
                "errors/4xx.html",
                {"request": request, 'code': self.code, 'message': message, 'debug_message': self.debug_message},
                status_code=status
            )

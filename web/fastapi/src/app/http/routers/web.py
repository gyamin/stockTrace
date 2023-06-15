from fastapi import APIRouter, Depends, Form
from fastapi.requests import Request
from fastapi.responses import Response, RedirectResponse
from fastapi.templating import Jinja2Templates
from starlette import status
from app.service import top_service
from app.service import login_service
from app.http.routers.dependencies.authentication import check_token, check_session_id
import json

router = APIRouter()

auth_page_router = APIRouter(
    prefix="/auth",
    tags=["auth_page"],
    dependencies=[Depends(check_session_id)]
)

auth_api_router = APIRouter(
    prefix="/auth",
    tags=["auth_api"],
    dependencies=[Depends(check_token)]
)

templates = Jinja2Templates(directory="app/templates")


# routing that is not required authentication 認証が不要なルーティング
@router.get("/login", tags=["webpage"])
async def login_page(request: Request):
    return templates.TemplateResponse("login.html", {"request": request})


@router.post("/login", tags=["webpage"])
async def exec_login(login_id: str = Form(), passwd: str = Form()):
    # ログイン処理
    service = login_service.LoginService()
    auth_info = service.login(login_id, passwd)

    response = RedirectResponse('/auth/top', status_code=status.HTTP_302_FOUND)
    response.set_cookie(key="session_id", value=auth_info.get('session_id'))
    response.set_cookie(key="session_id_expired_at", value=auth_info.get('session_id_expired_at'))

    return response


@router.get("/hello-html", tags=["test"])
async def hello_page(request: Request, response: Response):
    return templates.TemplateResponse("test.html", {"request": request})


# routing that is required authentication 認証を必要とするルーティング
@auth_page_router.get("/top", tags=["webpage"])
async def top_page(request: Request):
    view_data = top_service.TopService.get_index_data()
    return templates.TemplateResponse("top.html", {"request": request, "json_data": json.dumps(view_data, default=str)})


@auth_page_router.get("/test", tags=["test"])
async def test():
    return {"Auth": "test"}

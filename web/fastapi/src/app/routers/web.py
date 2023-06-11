from fastapi import APIRouter, Depends
from fastapi.requests import Request
from fastapi.templating import Jinja2Templates
from app.service import top_service
from app.routers.dependencies.authentication import check_token_header
from app.com.logging import logger
import json

router = APIRouter()

auth_router = APIRouter(
    prefix="/auth",
    tags=["auth-test"],
    dependencies=[Depends(check_token_header)]
)

templates = Jinja2Templates(directory="app/templates")


# routing that is not required authentication 認証が不要なルーティング
@router.get("/hello-html", tags=["test"])
async def hello_page(request: Request):
    logger.info("/hello-html")
    return templates.TemplateResponse("test.html", {"request": request})


@router.get("/top", tags=["webpage"])
async def top_page(request: Request):
    view_data = top_service.TopService.get_index_data()
    return templates.TemplateResponse("top.html", {"request": request, "json_data": json.dumps(view_data, default=str)})


# routing that is required authentication 認証を必要とするルーティング
@auth_router.get("/test", tags=["test"])
async def test():
    return {"Auth": "test"}

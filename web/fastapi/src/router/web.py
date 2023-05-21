from fastapi import APIRouter
from fastapi.requests import Request
from fastapi.templating import Jinja2Templates
from service import top_service
import json

router = APIRouter()
templates = Jinja2Templates(directory="templates")


@router.get("/hello-html", tags=["webpage"])
async def hello_page(request: Request):
    return templates.TemplateResponse("test.html", {"request": request})


@router.get("/top", tags=["webpage"])
async def top_page(request: Request):
    view_data = top_service.TopService.get_index_data()
    return templates.TemplateResponse("top.html", {"request": request, "json_data": json.dumps(view_data, default=str)})

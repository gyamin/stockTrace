from fastapi import FastAPI, Request
from fastapi.responses import JSONResponse
from fastapi.staticfiles import StaticFiles
from fastapi.templating import Jinja2Templates
from app.http.routers import web
from app.http.exceptions.AuthenticationPageException import AuthenticationPageException

app = FastAPI()

app.mount("/static", StaticFiles(directory="static"), name="static")
templates = Jinja2Templates(directory="app/templates")

# routers
app.include_router(web.router)
app.include_router(web.auth_router)


# exceptions
@app.exception_handler(AuthenticationPageException)
async def authentication_exception_handler(request: Request, exception: AuthenticationPageException):
    return templates.TemplateResponse(
        "errors/4xx.html", {"request": request, "message": exception.message}, status_code=403)


@app.exception_handler(Exception)
async def exception_handler(request: Request, exception: Exception):
    content_type = request.headers.get("content-type")
    if content_type == "application/json":
        return JSONResponse(content={'message': exception.message}, status_code=500)
    else:
        return templates.TemplateResponse(
            "errors/5xx.html", {"request": request, "message": "12345"}, status_code=500)


@app.get("/")
def read_root():
    return {"Hello": "World"}

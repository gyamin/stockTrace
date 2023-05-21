from fastapi import FastAPI, Request, Response
from fastapi.staticfiles import StaticFiles
from fastapi.templating import Jinja2Templates
from app.routers import web
from app.exceptions.AuthenticationException import AuthenticationException

app = FastAPI()

app.mount("/static", StaticFiles(directory="static"), name="static")
templates = Jinja2Templates(directory="app/templates")

# routers
app.include_router(web.router)
app.include_router(web.auth_router)


# exceptions
@app.exception_handler(AuthenticationException)
async def authentication_exception_handler(request: Request, exception: AuthenticationException):
    return templates.TemplateResponse("errors/4xx.html", {"request": request, "message": exception.message}, status_code=403)

@app.get("/")
def read_root():
    return {"Hello": "World"}

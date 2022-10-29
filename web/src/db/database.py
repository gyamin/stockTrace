from sqlalchemy import create_engine
from . import config

engine = create_engine(config.URL)

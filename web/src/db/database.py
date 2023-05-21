from sqlalchemy import create_engine
from . import config

engine = create_engine(config.URL)


def convert_dic(rows):
    results = []
    for row in rows:
        buff = {}
        for field in row._fields:
            buff[field] = row._mapping[field]
        results.append(buff)
    return results

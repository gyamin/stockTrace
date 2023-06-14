import logging

logging.config.fileConfig('.logging.conf', disable_existing_loggers=False)
logging.getLogger('sqlalchemy.engine').setLevel(logging.INFO)

logger = logging.getLogger('root')

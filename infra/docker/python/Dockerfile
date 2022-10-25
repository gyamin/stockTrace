FROM python:3.10-buster

RUN apt-get update
RUN apt-get -y install locales && \
    localedef -f UTF-8 -i ja_JP ja_JP.UTF-8
ENV LANG ja_JP.UTF-8
ENV LANGUAGE ja_JP:ja
ENV LC_ALL ja_JP.UTF-8
ENV TZ JST-9
ENV TERM xterm

RUN apt-get install -y vim
RUN pip install --upgrade pip
RUN pip install --upgrade setuptools

# Flask app Libraries
RUN pip install Flask
RUN pip install SQLAlchemy
RUN pip install psycopg2
RUN pip install python-dotenv

WORKDIR /app
CMD ["python", "app.py"]
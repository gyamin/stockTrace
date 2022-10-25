from flask import Flask, render_template, redirect
from controller.top import bp_top

app = Flask(__name__)

# コントローラ登録
app.register_blueprint(bp_top)


@app.route("/")
def top():
    return redirect('/top')


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000)

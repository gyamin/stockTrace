from flask import Blueprint, render_template
from db import database
from db.model.sotck_values import StockValues

bp_top = Blueprint('top', __name__)

@bp_top.route('/top')
def top():
    conn = database.engine.connect()
    model_stock_values = StockValues(conn)
    view_data = {}
    view_data["top_100"] = model_stock_values.get_rate_up_top_n(100)
    view_data["bottom_100"] = model_stock_values.get_rate_up_top_n(100)

    return render_template('top.html', title="トップ")

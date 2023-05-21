from app.db import database
from app.db.model.sotck_values import StockValues


class TopService:

    @staticmethod
    def get_index_data():
        conn = database.engine.connect()
        model_stock_values = StockValues(conn)

        view_data = {}
        rows = model_stock_values.get_rate_up_top_n(100)
        view_data["top_100"] = database.convert_dic(rows)

        rows = model_stock_values.get_rate_down_bottom_n(100)
        view_data["bottom_100"] = database.convert_dic(rows)

        rows = model_stock_values.get_n_days_result(7)
        view_data["days_results"] = database.convert_dic(rows)

        return view_data
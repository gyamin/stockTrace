from flask import Blueprint, render_template

bp_top = Blueprint('top', __name__)

@bp_top.route('/top')
def top():




    return render_template('top.html', title="トップ")

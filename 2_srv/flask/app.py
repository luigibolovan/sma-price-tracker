from flask import Flask
from scrappers.cel_scraper import get_cel_data
from scrappers.emag_scraper import get_emag_data
import json

app = Flask(__name__)


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/emag/<product>')
def get_emag_data_for(product):
    return json.dumps(get_emag_data(product))


@app.route('/cel/<product>')
def get_cel_data_for(product):
    return json.dumps(get_cel_data(product))


if __name__ == '__main__':
    app.run()

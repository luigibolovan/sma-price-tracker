import requests
from bs4 import BeautifulSoup as beautifulsoup
import nltk

product = input("product: ")
tokens = nltk.word_tokenize(product)

URL = 'https://www.emag.ro/search/'

for token in tokens:
    URL += token + '%20'

page = requests.get(URL)
soup = beautifulsoup(page.content, features="html.parser")

card_grid = soup.find("div", id="card_grid", recursive=True)

### thumbnail
thumbnails = []

thumbnailTags = card_grid.find_all("img", {"class": "lozad"}, recursive=True)
for thumbnail in thumbnailTags:
    thumbnails.append(thumbnail['data-src'])
print(thumbnails)

### product title and link
titles = []
links = []

titleLinkTags = card_grid.find_all("a", {"class": "product-title js-product-url"}, recursive=True)
for title_link in titleLinkTags:
    titles.append(title_link.get_text())
    links.append(title_link['href'])
print(titles)
print(links)

### price
prices = []

priceTags = card_grid.find_all("p", {"class": "product-new-price"}, recursive=True)
for price in priceTags:
    prices.append(price.get_text())
print(prices)
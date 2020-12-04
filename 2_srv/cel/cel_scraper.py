import requests
from bs4 import BeautifulSoup
import nltk

product = input("product: ")
tokens = nltk.word_tokenize(product)

URL = 'https://www.cel.ro/cauta/'

for token in tokens:
    URL += token + '+'

page = requests.get(URL)

soup = BeautifulSoup(page.content, features='html.parser')

productListing = soup.find("div", {"class": "productlisting"}, recursive=True)

### thumbnail
thumbnails = []

imgTags = productListing.find_all("img", {'itemprop': 'image'}, recursive=True)
for img in imgTags:
    thumbnails.append(img['content'])
print(thumbnails)

### title and link
titles = []
links = []

titleLinks = productListing.find_all("a", {'class': 'productListing-data-b product_link product_name'}, recursive=True)
for title_link in titleLinks:
    titles.append(title_link.get_text())
    links.append(title_link['href'])
print(titles)
print(links)

### price
prices = []

priceTags = productListing.find_all("b", {'productprice': '1'}, recursive=True)
for price in priceTags:
    prices.append(price.get_text())

print(prices)

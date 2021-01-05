import requests
from bs4 import BeautifulSoup
import nltk

URL = 'https://www.cel.ro/cauta/'


def get_container(product):
    global URL
    tokens = nltk.word_tokenize(product)
    for token in tokens:
        URL += token + '+'

    page = requests.get(URL)
    soup = BeautifulSoup(page.content, features='html.parser')
    container = soup.find("div", {"class": "productlisting"}, recursive=True)

    return container


# thumbnail
def get_thumbnails(container):
    thumbnails = []
    imgTags = container.find_all("img", {'itemprop': 'image'}, recursive=True)
    for img in imgTags:
        thumbnails.append(img['content'])
    return thumbnails


# title and link
def get_title_and_links(container):
    titles = []
    links = []

    titleLinks = container.find_all("a", {'class': 'productListing-data-b product_link product_name'}, recursive=True)
    for title_link in titleLinks:
        titles.append(title_link.get_text())
        links.append(title_link['href'])
    return titles, links


# price
def get_prices(container):
    prices = []

    priceTags = container.find_all("b", {'productprice': '1'}, recursive=True)
    for price in priceTags:
        print(price.get_text())
        prices.append(price.get_text())

    return prices


def get_cel_data(product):
    container = get_container(product)
    product_thumbnails = get_thumbnails(container)
    product_titles, product_links = get_title_and_links(container)
    product_prices = get_prices(container)

    key_lists = ['title', 'product_url', 'product_thumbnail', 'product_price']

    list_of_dicts = []  # a.k.a json array :)
    for element in list(zip(product_titles, product_links, product_thumbnails, product_prices)):
        list_of_dicts.append(dict(zip(key_lists, element)))

    print(list_of_dicts)
    URL = 'https://www.cel.ro/cauta/'
    return list_of_dicts

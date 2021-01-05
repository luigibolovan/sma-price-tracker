import requests
from bs4 import BeautifulSoup as beautifulsoup
import nltk

URL = 'https://www.emag.ro/search/'


def get_container(product):
    global URL
    tokens = nltk.word_tokenize(product)
    for token in tokens:
        URL += token + '%20'
    page = requests.get(URL)
    soup = beautifulsoup(page.content, features="html.parser")
    print(soup.prettify())
    card_grid = soup.find("div", id="card_grid", recursive=True)

    return card_grid


# thumbnail
def get_thumbnails(container):
    thumbnails = []

    thumbnail_tags = container.find_all("img", {"class": "lozad"}, recursive=True)
    for thumbnail in thumbnail_tags:
        thumbnails.append(thumbnail['data-src'])
    return thumbnails


# product title and link
def get_product_titles_and_links(container):
    titles = []
    links = []

    title_link_tags = container.find_all("a", {"class": "product-title js-product-url"}, recursive=True)

    for title_link in title_link_tags:
        titles.append(title_link.get_text())
        links.append(title_link['href'])

    return titles, links


# price
def get_prices(container):
    prices = []

    priceTags = container.find_all("p", {"class": "product-new-price"}, recursive=True)
    for price in priceTags:
        text = price.get_text()
        pret = text[:len(text) - 4]  # " Lei"
        pret_wout_bani = pret[:len(pret) - 2]

        pret_wout_dots = list(filter('.'.__ne__, pret_wout_bani))
        final_prices = ''.join(pret_wout_dots)
        print(final_prices)
        prices.append(final_prices)

    return prices


def get_emag_data(product):
    container = get_container(product)
    product_thumbnails = get_thumbnails(container)
    product_titles, product_links = get_product_titles_and_links(container)
    product_prices = get_prices(container)

    key_lists = ['title', 'product_url', 'product_thumbnail', 'product_price']

    list_of_dicts = []  # a.k.a json array :)
    for element in list(zip(product_titles, product_links, product_thumbnails, product_prices)):
        list_of_dicts.append(dict(zip(key_lists, element)))

    print(list_of_dicts)
    URL = 'https://www.emag.ro/search/'
    return list_of_dicts

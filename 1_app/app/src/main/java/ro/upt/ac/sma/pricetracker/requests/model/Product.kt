package ro.upt.ac.sma.pricetracker.requests.model

data class Product(
    val title: String,
    val product_url: String,
    val product_thumbnail: String,
    val product_price: Int
)
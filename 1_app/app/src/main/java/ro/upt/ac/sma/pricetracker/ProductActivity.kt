package ro.upt.ac.sma.pricetracker

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import ro.upt.ac.sma.pricetracker.requests.model.Product
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class ProductActivity : AppCompatActivity() {
    private lateinit var thumbnail: ImageView
    private lateinit var title: TextView
    private lateinit var productURL: TextView
    private lateinit var price: TextView
    private lateinit var vendor: TextView
    private lateinit var favoriteImg: ImageView
    private lateinit var back: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        thumbnail = findViewById(R.id.iv_product_photo)
        title = findViewById(R.id.tv_product_title)
        productURL = findViewById(R.id.tv_product_url)
        price = findViewById(R.id.tv_product_price)
        vendor = findViewById(R.id.tv_product_seller)
        favoriteImg = findViewById(R.id.iv_product_add_to_favorites)
        back = findViewById(R.id.iv_back)

        val productIntent = intent
        val productToShow = productIntent.getParcelableExtra<Product>("product")

        title.text = productToShow!!.title
        productURL.text = productToShow.product_url
        vendor.text = "Vandut de " + productToShow.vendor
        price.text = productToShow.product_price + " lei"

        var resizedBitmap : Bitmap? = null
        val thumbnailDlThread = thread(start = true) {

            val url = URL(productToShow.product_thumbnail)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val thumbnailBitmap: Bitmap = BitmapFactory.decodeStream(connection.inputStream)
            resizedBitmap = Bitmap.createScaledBitmap(thumbnailBitmap, 160, 220, false)
        }
        thumbnailDlThread.join()

        thumbnail.setImageBitmap(resizedBitmap)
    }

    override fun onResume() {
        super.onResume()

        back.setOnClickListener {
            onBackPressed()
        }
    }
}
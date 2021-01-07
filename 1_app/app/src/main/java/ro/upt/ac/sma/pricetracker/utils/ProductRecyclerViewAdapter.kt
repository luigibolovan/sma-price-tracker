package ro.upt.ac.sma.pricetracker.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ro.upt.ac.sma.pricetracker.R
import ro.upt.ac.sma.pricetracker.requests.model.Product
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class ProductRecyclerViewAdapter(
        private val productList: MutableList<Product>,
        private val onClick: (Product) -> (Unit)
) : RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var favoriteImg = itemView.findViewById<ImageView>(R.id.iv_product_item_favorite)
        private var favorite = false
        private var background = itemView.findViewById<CardView>(R.id.card_item)

        fun bind(product: Product) {
            val productTitleTV  = itemView.findViewById<TextView>(R.id.tv_product_item_title)
            productTitleTV.text = product.title

            val productThumbnailIV = itemView.findViewById<ImageView>(R.id.iv_product_item_photo)
            var resizedBitmap : Bitmap? = null
            val thumbnailDlThread = thread(start = true) {

                val url = URL(product.product_thumbnail)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val thumbnailBitmap: Bitmap = BitmapFactory.decodeStream(connection.inputStream)
                resizedBitmap = Bitmap.createScaledBitmap(thumbnailBitmap, 40, 64, false)

            }
            thumbnailDlThread.join()
            productThumbnailIV.setImageBitmap(resizedBitmap)
            val productPriceTV  = itemView.findViewById<TextView>(R.id.tv_product_item_price)
            productPriceTV.text = "Pret " + product.product_price + " lei"

            val productVendorTV = itemView.findViewById<TextView>(R.id.tv_product_item_seller_foo)
            productVendorTV.text = "Vandut de " + product.vendor

            favoriteImg.setOnClickListener {
                if (!favorite) {
                    favoriteImg.setImageResource(R.drawable.heart_favorite_added)
                    favorite = true
                    // add to favorites
                } else {
                    favoriteImg.setImageResource(R.drawable.heart_favorite_not_added)
                    favorite = false
                    // remove from favorites
                }
            }
            background.setOnClickListener {
                onClick(productList[adapterPosition])
            }
        }
    }

}
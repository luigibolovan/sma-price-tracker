package ro.upt.ac.sma.pricetracker.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ro.upt.ac.sma.pricetracker.R
import ro.upt.ac.sma.pricetracker.requests.model.Product

class ProductRecyclerViewAdapter(
        private val productList: MutableList<Product>
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

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(product: Product) {
            val productTitleTV  = itemView.findViewById<TextView>(R.id.tv_product_item_title)
            productTitleTV.text = product.title

            // fmmmmmmmmmmmmmmmmmmmmmmmm
//            val productThumbnailIV = itemView.findViewById<ImageView>(R.id.iv_product_item_photo)
//            thread(start = true) {
//                val url: URL = URL(product.product_url)
//                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
//                connection.doInput = true
//                connection.connect()
//                val thumbnailBitmap: Bitmap = BitmapFactory.decodeStream(connection.inputStream)
//                val resizedBitmap = Bitmap.createScaledBitmap(thumbnailBitmap, 64, 64, false)
//                productThumbnailIV.setImageBitmap(resizedBitmap)
//            }

            val productPriceTV  = itemView.findViewById<TextView>(R.id.tv_product_item_price)
            productPriceTV.text = "Pret " + product.product_price
        }
    }

}
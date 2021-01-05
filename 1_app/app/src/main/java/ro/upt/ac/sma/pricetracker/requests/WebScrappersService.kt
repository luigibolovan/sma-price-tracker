package ro.upt.ac.sma.pricetracker.requests

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ro.upt.ac.sma.pricetracker.requests.model.Product

interface WebScrappersService {
    @GET("{store}/{product}")
    fun getProducts(@Path("store") store: String,
                    @Path("product") product: String) : Call<ArrayList<Product>>

}
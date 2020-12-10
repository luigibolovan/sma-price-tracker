package ro.upt.ac.sma.pricetracker.requests

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ro.upt.ac.sma.pricetracker.requests.model.ProductResults

interface WebScrappersService {

    @GET("/{product}")
    fun getProducts(@Path("product") product : String) : Call<ProductResults>

}
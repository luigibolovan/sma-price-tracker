package ro.upt.ac.sma.pricetracker.requests

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private var store : String = ""

    private val URL = "192.168.100.29/$store"

    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun setStoreName(storeName: String) {
        store = storeName
    }

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}
package ro.upt.ac.sma.pricetracker.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import retrofit2.Call
import ro.upt.ac.sma.pricetracker.R
import ro.upt.ac.sma.pricetracker.requests.ServiceBuilder
import ro.upt.ac.sma.pricetracker.requests.WebScrappersService
import java.util.*
import retrofit2.Callback
import retrofit2.Response
import ro.upt.ac.sma.pricetracker.requests.model.Product
import kotlin.collections.ArrayList

class SearchFragment : Fragment() {
    private lateinit var searchBtn: Button
    private lateinit var checkBoxEmag: CheckBox
    private lateinit var checkBoxCel: CheckBox
    private lateinit var searchEditText : EditText
    private val products: ArrayList<Product> = ArrayList()
    private var storesRequested = 0
    private var responsesReceived = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myView : View = inflater.inflate(R.layout.fragment_search, container, false)

        searchBtn       = myView.findViewById(R.id.btn_search)
        checkBoxEmag    = myView.findViewById(R.id.checkbox_search_emag)
        checkBoxCel     = myView.findViewById(R.id.checkbox_search_cel)
        searchEditText  = myView.findViewById(R.id.et_search_product_name)

        return myView
    }

    override fun onResume() {
        super.onResume()

        searchBtn.setOnClickListener {

            if (checkBoxEmag.isChecked) {
                storesRequested++;
                val request = ServiceBuilder.buildService(WebScrappersService::class.java)
                val call = request.getProducts("emag",getFormattedProductName())

                call.enqueue(object : Callback<ArrayList<Product>>{
                    override fun onResponse(
                        call: Call<ArrayList<Product>>,
                        response: Response<ArrayList<Product>>
                    ) {
                        if (response.isSuccessful) run {
                            addProducts(response.body()!!)
                            responsesReceived++
                            checkTransition()
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<Product>>, t: Throwable) {
                        Log.d("CEL-RESPONSE", "Failure: ${t.message}")
                    }
                })
            }

            if (checkBoxCel.isChecked) {
                storesRequested++;
                val request = ServiceBuilder.buildService(WebScrappersService::class.java)
                val call = request.getProducts("cel", getFormattedProductName())

                call.enqueue(object : Callback<ArrayList<Product>>{
                    override fun onResponse(
                        call: Call<ArrayList<Product>>,
                        response: Response<ArrayList<Product>>
                    ) {
                        if (response.isSuccessful) run {
                            addProducts(response.body()!!)
                            responsesReceived++
                            checkTransition()
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<Product>>, t: Throwable) {
                        Log.d("CEL-RESPONSE", "Failure: ${t.message}")
                    }
                })
            }
        }
    }

    private fun addProducts(products: ArrayList<Product>) {
        this.products.addAll(products)
    }

    private fun checkTransition() {
        if (storesRequested == responsesReceived) {
            Log.d("First product", products[0].title!!)
            val searchListFragment = SearchListFragment.newInstance(products)
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_container, searchListFragment)
            transaction?.commit()
        }
    }

    private fun getFormattedProductName() : String {
        val text : String = searchEditText?.text.toString()
        var formattedString = ""
        val tokenizer = StringTokenizer(text, " ")

        while (tokenizer.hasMoreTokens()) {
            formattedString += tokenizer.nextToken() + "+"
        }

        return formattedString
    }
}

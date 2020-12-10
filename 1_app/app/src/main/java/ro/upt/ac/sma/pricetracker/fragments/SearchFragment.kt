package ro.upt.ac.sma.pricetracker.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import retrofit2.Call
import ro.upt.ac.sma.pricetracker.R
import ro.upt.ac.sma.pricetracker.requests.ServiceBuilder
import ro.upt.ac.sma.pricetracker.requests.WebScrappersService
import java.util.*
import retrofit2.Callback
import retrofit2.Response
import ro.upt.ac.sma.pricetracker.requests.model.ProductResults

class SearchFragment : Fragment() {

    private var searchBtn: Button?          = null
    private var checkBoxEmag: CheckBox?     = null
    private var checkBoxCel: CheckBox?      = null
    private var searchEditText : EditText?  = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myView : View = inflater.inflate(R.layout.fragment_search, container, false)

        searchBtn       = myView.findViewById(R.id.btn_search)
        checkBoxEmag    = myView.findViewById(R.id.checkbox_search_emag)
        checkBoxCel     = myView.findViewById(R.id.checkbox_search_cel)
        searchEditText  = myView.findViewById(R.id.et_search_product_name)

        return myView
    }

    override fun onResume() {
        super.onResume()

        searchBtn?.setOnClickListener {

            if (checkBoxEmag?.isChecked == true) {

                ServiceBuilder.setStoreName("emag")
                val request = ServiceBuilder.buildService(WebScrappersService::class.java)
                val call = request.getProducts(getFormattedProductName())

                call.enqueue(object : Callback<ProductResults>{
                    @SuppressLint("ShowToast")
                    override fun onResponse(
                        call: Call<ProductResults>,
                        response: Response<ProductResults>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(activity, response.body()!!.products.toString(), Toast.LENGTH_LONG)
                        }
                    }

                    @SuppressLint("ShowToast")
                    override fun onFailure(call: Call<ProductResults>, t: Throwable) {
                        Toast.makeText(activity, "${t.message}", Toast.LENGTH_SHORT)
                    }
                })
            }

            if (checkBoxCel?.isChecked == true) {
                ServiceBuilder.setStoreName("cel")
                val request = ServiceBuilder.buildService(WebScrappersService::class.java)
                val call = request.getProducts(getFormattedProductName())

                call.enqueue(object : Callback<ProductResults>{
                    @SuppressLint("ShowToast")
                    override fun onResponse(
                        call: Call<ProductResults>,
                        response: Response<ProductResults>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(activity, response.body()!!.products.toString(), Toast.LENGTH_LONG)
                        }
                    }

                    @SuppressLint("ShowToast")
                    override fun onFailure(call: Call<ProductResults>, t: Throwable) {
                        Toast.makeText(activity, "${t.message}", Toast.LENGTH_SHORT)
                    }
                })
            }
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
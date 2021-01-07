package ro.upt.ac.sma.pricetracker.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ro.upt.ac.sma.pricetracker.ProductActivity
import ro.upt.ac.sma.pricetracker.R
import ro.upt.ac.sma.pricetracker.requests.model.Product
import ro.upt.ac.sma.pricetracker.utils.OnBackPressedHelper
import ro.upt.ac.sma.pricetracker.utils.ProductRecyclerViewAdapter

class SearchListFragment : Fragment(), OnBackPressedHelper{

    private lateinit var listAdapter: ProductRecyclerViewAdapter

    private lateinit var recyclerViewList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_search_list, container, false)
        val productList = arguments?.getParcelableArrayList<Product>(PRODUCT_LIST)
        listAdapter = ProductRecyclerViewAdapter(productList!!.toMutableList()) {
            goToProductActivity(it)
        }
        recyclerViewList = view.findViewById(R.id.recycler_view_search)
        recyclerViewList.adapter = listAdapter
        recyclerViewList.layoutManager = LinearLayoutManager(activity)

        return view
    }

    private fun goToProductActivity(prod: Product) {
        val intent = Intent(activity, ProductActivity::class.java)
        intent.putExtra("product", prod)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
    }

    companion object {
        const val PRODUCT_LIST = "Product list"

        fun newInstance(listOfProducts: ArrayList<Product>) : SearchListFragment {
            val args = Bundle()
            args.putParcelableArrayList(PRODUCT_LIST, listOfProducts)
            val returnFragment = SearchListFragment()
            returnFragment.arguments = args
            return returnFragment
        }
    }

    override fun onBackPressed(): Boolean {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame_container, SearchFragment())
        transaction?.commit()

        return true
    }
}
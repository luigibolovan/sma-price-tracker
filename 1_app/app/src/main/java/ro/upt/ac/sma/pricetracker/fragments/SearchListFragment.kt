package ro.upt.ac.sma.pricetracker.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ro.upt.ac.sma.pricetracker.R
import ro.upt.ac.sma.pricetracker.requests.model.Product
import ro.upt.ac.sma.pricetracker.utils.ProductRecyclerViewAdapter

class SearchListFragment : Fragment(){

    private lateinit var listAdapter: ProductRecyclerViewAdapter

    private lateinit var recyclerViewList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_search_list, container, false)
        val productList = arguments?.getParcelableArrayList<Product>(PRODUCT_LIST)
        Log.d("PRODUCT LIST SEARCH", productList!![3].title!!)
        listAdapter = ProductRecyclerViewAdapter(productList!!.toMutableList())
        recyclerViewList = view.findViewById(R.id.recycler_view_search)
        recyclerViewList.adapter = listAdapter
        recyclerViewList.layoutManager = LinearLayoutManager(activity)

        return view
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
}
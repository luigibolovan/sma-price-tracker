package ro.upt.ac.sma.pricetracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import ro.upt.ac.sma.pricetracker.R

class SearchFragment : Fragment() {

    private var searchBtn: Button? = null
    private var checkBoxEmag: CheckBox? = null
    private var checkBoxCel: CheckBox? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myView : View = inflater.inflate(R.layout.fragment_search, container, false)

        searchBtn = myView.findViewById(R.id.btn_search)
        checkBoxEmag = myView.findViewById(R.id.checkbox_search_emag)
        checkBoxCel = myView.findViewById(R.id.checkbox_search_cel)

        return myView
    }

    override fun onResume() {
        super.onResume()

        searchBtn?.setOnClickListener {
            if (checkBoxEmag?.isChecked == true) {
                // request emag
            }

            if (checkBoxCel?.isChecked == true) {
                //request cel
            }
        }
    }
}
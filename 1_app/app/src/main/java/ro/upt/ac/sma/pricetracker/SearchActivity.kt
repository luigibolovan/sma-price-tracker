package ro.upt.ac.sma.pricetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ro.upt.ac.sma.pricetracker.fragments.AccountFragment
import ro.upt.ac.sma.pricetracker.fragments.FavoritesFragment
import ro.upt.ac.sma.pricetracker.fragments.SearchFragment

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val navBar = findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        navBar.selectedItemId = R.id.item_search_btn

        supportActionBar!!.displayOptions = DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.action_bar_default)
    }

    override fun onResume() {
        super.onResume()

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        val searchFragment = SearchFragment()
        val accountFragment = AccountFragment()
        val favoritesFragment = FavoritesFragment()

        switchToFragment(searchFragment)

        //setup listeners on nav bar button
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.item_search_btn -> switchToFragment(searchFragment)
                R.id.item_profile_btn -> switchToFragment(accountFragment)
                R.id.item_favorites_btn -> switchToFragment(favoritesFragment)
            }
            true
        }
    }

    private fun switchToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.frame_container, fragment)
            commit()
        }
    }
}
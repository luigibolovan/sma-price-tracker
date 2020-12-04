package ro.upt.ac.sma.pricetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import java.util.*

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_loading)

        val searchActivityIntent = Intent(this, SearchActivity::class.java)

        val mTimer = object : CountDownTimer(2000, 1000) {
            override fun onTick(p0: Long) {
                //nada
            }

            override fun onFinish() {
                startActivity(searchActivityIntent)
            }
        }

        mTimer.start()
    }
}
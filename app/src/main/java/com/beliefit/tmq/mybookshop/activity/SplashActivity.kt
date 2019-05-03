package com.beliefit.tmq.mybookshop.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.beliefit.tmq.mybookshop.R
import com.beliefit.tmq.mybookshop.util.SharedPrefUtil

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        doInitialLoading()
    }


    private fun doInitialLoading() {
        Handler().postDelayed(Runnable {
            checkUserLoggedIn()
        }, 3000)
    }

    private fun checkUserLoggedIn() {
        var myIntent: Intent? = null
        myIntent = if (SharedPrefUtil.getUserHasLoggedInPref(this)) {
            Intent(this, HomeActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }
        myIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(myIntent)
        finish()
    }
}

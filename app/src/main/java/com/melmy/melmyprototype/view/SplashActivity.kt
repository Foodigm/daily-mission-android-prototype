package com.melmy.melmyprototype.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.melmy.melmyprototype.R
import com.melmy.melmyprototype.home.HomeActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_splash)
        startActivity(HomeActivity.createIntent(this))
        finish()
    }

}


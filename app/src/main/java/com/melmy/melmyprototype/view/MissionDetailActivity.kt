package com.melmy.melmyprototype.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.melmy.melmyprototype.R

class MissionDetailActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, MissionDetailActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mission_detail)
    }
}

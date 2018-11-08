package com.melmy.melmyprototype.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.melmy.melmyprototype.R
import com.melmy.melmyprototype.databinding.ActivityMissionAddBinding

class MissionAddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMissionAddBinding = DataBindingUtil.setContentView(this, R.layout.activity_mission_add)
        initView(binding)
    }

    fun initView(binding: ActivityMissionAddBinding) {
        with(binding) {
            backImageView.setOnClickListener {
                finish()
            }
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, MissionAddActivity::class.java)
            return intent
        }
    }

}

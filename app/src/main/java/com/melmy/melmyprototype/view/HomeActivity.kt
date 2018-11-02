package com.melmy.melmyprototype.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.melmy.melmyprototype.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, HomeActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initDrawer()
    }

    private fun initDrawer(){
        home_navigation_view.setNavigationItemSelectedListener { menuItem ->

            when(menuItem.itemId) {
                R.id.mission_list ->
                    startActivity(MissionListActivity.createIntent(this))
                R.id.mission_history ->
                    startActivity(HistoryActivity.createIntent(this))
            }
            home_drawer_layout.closeDrawers()

            true
        }
    }

}

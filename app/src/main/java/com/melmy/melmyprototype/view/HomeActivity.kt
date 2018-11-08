package com.melmy.melmyprototype.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        initToolbar()
        initDrawer()
        initView()
    }

    private fun initView() {
        recycler_view.adapter = DailyMissionsAdapter()
        recycler_view.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        menu_image_view.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }

    }

    private fun initDrawer() {
        home_navigation_view.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.mission_list ->
                    startActivity(MissionListActivity.createIntent(this))
                R.id.mission_history ->
                    startActivity(HistoryActivity.createIntent(this))
            }
            drawer_layout.closeDrawers()

            true
        }
    }

}

class DailyMissionsAdapter : RecyclerView.Adapter<DailyMissionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyMissionViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: DailyMissionViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

class DailyMissionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind() {

    }
}

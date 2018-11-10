package com.melmy.melmyprototype.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.melmy.melmyprototype.R
import com.melmy.melmyprototype.data.DailyMission
import com.melmy.melmyprototype.data.Mission
import com.melmy.melmyprototype.data.MissionType
import com.melmy.melmyprototype.databinding.ActivityHomeBinding
import com.melmy.melmyprototype.databinding.ListItemDailyMissionsBinding
import com.melmy.melmyprototype.utils.DayOfWeekSet
import com.melmy.melmyprototype.utils.InjectorUtil
import com.melmy.melmyprototype.viewmodel.HomeViewModel
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: DailyMissionsAdapter

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, HomeActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        initToolbar(binding)
        initDrawer(binding)
        initView(binding)
        subscribeUi(binding)

//        dbDebug()
    }

    private fun dbDebug() {
        val mission = Mission(
                title = "mission1",
                days = DayOfWeekSet().apply {
                    monday = true
                    wednesday = true
                    friday = true
                },
                goalCountTotal = 10,
                goalCountDaily = 3,
                type = MissionType.COUNT
        )
        val dailyMission = DailyMission(
                missionId = 1
        )
//        viewModel.missionRepository.createMission(mission)
//        viewModel.missionRepository.createDailyMission(dailyMission)
    }

    private fun subscribeUi(binding: ActivityHomeBinding) {
        val today = Calendar.getInstance()
        viewModel.dailyMissions.observe(this, Observer {
            binding.emptyDailyMissionsTextView.visibility =
                    if (it.isNotEmpty()) {
                        View.GONE
                    } else {
                        View.VISIBLE
                    }
//            Log.d("sgc109", "subscibeUI! size : " + it.size)
//            for(mission in it) {
//                Log.d("sgc109", mission.toString())
//            }
        })
    }

    private fun initView(binding: ActivityHomeBinding) {
        val factory = InjectorUtil.provideHomeViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        adapter = DailyMissionsAdapter()

        with(binding) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(
                    this@HomeActivity,
                    2,
                    RecyclerView.VERTICAL,
                    false)
        }
    }

    private fun initToolbar(binding: ActivityHomeBinding) {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.menuImageView.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

    }

    private fun initDrawer(binding: ActivityHomeBinding) {
        binding.homeNavigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.mission_list ->
                    startActivity(MissionListActivity.createIntent(this))
                R.id.mission_history ->
                    startActivity(HistoryActivity.createIntent(this))
            }
            binding.drawerLayout.closeDrawers()

            true
        }
    }

}

class DailyMissionsAdapter : ListAdapter<DailyMission, DailyMissionViewHolder>(DailyMissionDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyMissionViewHolder {
        val binding = ListItemDailyMissionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyMissionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyMissionViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            missionTitleTextView.text
            missionCompletePercentTextView.text
        }
    }

}

class DailyMissionDiffCallback : DiffUtil.ItemCallback<DailyMission>() {
    override fun areItemsTheSame(oldItem: DailyMission, newItem: DailyMission): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DailyMission, newItem: DailyMission): Boolean {
        val copied = oldItem.copy(id = newItem.id)
        return copied == newItem
    }

}

class DailyMissionViewHolder(val binding: ListItemDailyMissionsBinding) : RecyclerView.ViewHolder(binding.root)

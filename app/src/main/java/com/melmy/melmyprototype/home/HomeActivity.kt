package com.melmy.melmyprototype.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.melmy.melmyprototype.R
import com.melmy.melmyprototype.data.Mission
import com.melmy.melmyprototype.databinding.ActivityHomeBinding
import com.melmy.melmyprototype.databinding.ListItemDailyMissionsBinding
import com.melmy.melmyprototype.missionlist.MissionListActivity
import com.melmy.melmyprototype.missionlistweek.MissionListWeekActivity
import com.melmy.melmyprototype.utils.InjectorUtil
import com.melmy.melmyprototype.view.HistoryActivity

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

        setUpToolbar(binding)
        setUpDrawer(binding)
        setUpViews(binding)
        subscribeUi(binding)
    }

    override fun onResume() {
        super.onResume()
        viewModel.start()
    }

    private fun subscribeUi(binding: ActivityHomeBinding) {
    }

    private fun setUpViews(binding: ActivityHomeBinding) {
        val factory = InjectorUtil.provideHomeViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        adapter = DailyMissionsAdapter(viewModel)
        with(binding) {
            homeRecyclerView.adapter = adapter
            homeRecyclerView.layoutManager = GridLayoutManager(
                    this@HomeActivity,
                    2,
                    RecyclerView.VERTICAL,
                    false)
            viewModel = this@HomeActivity.viewModel
            executePendingBindings()
        }
    }

    private fun setUpToolbar(binding: ActivityHomeBinding) {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.menuImageView.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

    }

    private fun setUpDrawer(binding: ActivityHomeBinding) {
        binding.homeNavigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.mission_list ->
                    startActivity(MissionListActivity.createIntent(this))
                R.id.mission_list_by_day_of_week ->
                    startActivity(MissionListWeekActivity.createIntent(this))
                R.id.mission_history ->
                    startActivity(HistoryActivity.createIntent(this))
            }
            binding.drawerLayout.closeDrawers()

            true
        }
    }
}

class DailyMissionsAdapter(val viewModel: HomeViewModel) : ListAdapter<Mission, DailyMissionViewHolder>(MissionDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyMissionViewHolder {
        val binding = ListItemDailyMissionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyMissionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyMissionViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            missionTitleTextView.text = item.title
            val percent = item.getDailyAchievePercent()
            missionCompletePercentTextView.text = percent.toString()
            progressBar.percent = percent.toFloat()
            progressBar.setOnClickListener {
                if (item.isCompletedToday()) return@setOnClickListener
                item.accCountDaily++
                viewModel.updateDailyMission(item)
                notifyDataSetChanged()
            }
        }
    }

}

class MissionDiffCallback : DiffUtil.ItemCallback<Mission>() {
    override fun areItemsTheSame(oldItem: Mission, newItem: Mission): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Mission, newItem: Mission): Boolean {
        val copied = oldItem.copy(id = newItem.id)
        return copied == newItem
    }

}

class DailyMissionViewHolder(val binding: ListItemDailyMissionsBinding) : RecyclerView.ViewHolder(binding.root)

package com.melmy.melmyprototype.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.melmy.melmyprototype.R
import com.melmy.melmyprototype.databinding.ActivityMissionListBinding
import com.melmy.melmyprototype.databinding.ListItemAllMissionsBinding
import com.melmy.melmyprototype.model.Mission
import com.melmy.melmyprototype.utils.InjectorUtil
import com.melmy.melmyprototype.utils.getAchievePercent
import com.melmy.melmyprototype.viewmodel.MissionListViewModel

class MissionListActivity : AppCompatActivity() {

    private lateinit var viewModel: MissionListViewModel
    private lateinit var adapter: ListAdapter<Mission, MissionListViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMissionListBinding = DataBindingUtil.setContentView(this, R.layout.activity_mission_list)

        initToolbar(binding)
        initView(binding)
        subscribeUi(binding)
    }

    private fun initView(binding: ActivityMissionListBinding) {
        adapter = MissionListAdapter()

        with(binding) {
            recyclerView.adapter = adapter
        }
    }

    private fun subscribeUi(binding: ActivityMissionListBinding) {
        val factory = InjectorUtil.provideMissionListViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(MissionListViewModel::class.java)

        viewModel.missions.observe(this, Observer {
            adapter.submitList(it)
            binding.emptyMissionListTextView.visibility =
                    if (it.isNotEmpty()) {
                        View.GONE
                    } else {
                        View.VISIBLE
                    }
        })
    }

    private fun initToolbar(binding: ActivityMissionListBinding) {
        setSupportActionBar(binding.toolbar)
        binding.backImageView.setOnClickListener {
            finish()
        }
        binding.addMissionImageView.setOnClickListener {
            val intent = MissionAddActivity.createIntent(this)
            startActivity(intent)
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, MissionListActivity::class.java)
            return intent
        }
    }
}

class MissionListAdapter : ListAdapter<Mission, MissionListViewHolder>(MissionDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionListViewHolder {
        val binding = ListItemAllMissionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MissionListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MissionListViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            missionItemTitle.text = item.title
            missionItemProgressBar.progress = item.getAchievePercent()
        }
    }
}

class MissionListViewHolder(val binding: ListItemAllMissionsBinding) : RecyclerView.ViewHolder(binding.root)

class MissionDiffCallback : DiffUtil.ItemCallback<Mission>() {
    override fun areItemsTheSame(oldItem: Mission, newItem: Mission): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Mission, newItem: Mission): Boolean {
        val copied = oldItem.copy(id = newItem.id)
        return copied == newItem
    }
}


package com.melmy.melmyprototype.missionlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.melmy.melmyprototype.R
import com.melmy.melmyprototype.data.Mission
import com.melmy.melmyprototype.databinding.ActivityMissionListBinding
import com.melmy.melmyprototype.databinding.ListItemAllMissionsBinding
import com.melmy.melmyprototype.missionaddedit.MissionAddEditActivity
import com.melmy.melmyprototype.missiondetail.MissionDetailActivity
import com.melmy.melmyprototype.utils.InjectorUtil
import java.util.*
import java.util.Calendar.*

class MissionListActivity : AppCompatActivity() {

    private lateinit var viewModel: MissionListViewModel
    private lateinit var adapter: ListAdapter<Mission, MissionListViewHolder>
    private lateinit var binding: ActivityMissionListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mission_list)

        setUpToolbar()
        setUpViews()
        setUpViewModel()
        subscribeUi()
    }

    private fun setUpViewModel() {
        val factory = InjectorUtil.provideMissionListViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(MissionListViewModel::class.java)
    }

    private fun setUpViews() {
        adapter = MissionListAdapter(object : MissionItemCallback {
            override fun deleteMission(mission: Mission) {
                viewModel.deleteMission(mission)
            }

            override fun editMission(missionId: Long) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun openMissionDetail(missionId: Long) {
                val intent = MissionDetailActivity.newInstance(this@MissionListActivity, missionId)
                startActivity(intent)
            }

        })

        with(binding) {
            missionListRecyclerView.adapter = adapter
            missionListRecyclerView.layoutManager = LinearLayoutManager(this@MissionListActivity, RecyclerView.VERTICAL, false)
        }
    }

    private fun subscribeUi() {
        viewModel.missions.observe(this, Observer {
            adapter.submitList(it)
            binding.missionListRecyclerView.visibility =
                    if (it.isNotEmpty()) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
            binding.emptyMissionListTextView.visibility =
                    if (it.isNotEmpty()) {
                        View.GONE
                    } else {
                        View.VISIBLE
                    }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_mission_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.isChecked = true
        when (item?.itemId) {
            R.id.item_add -> {
                val intent = MissionAddEditActivity.createIntent(this)
                startActivity(intent)
            }
            R.id.item_filter_all -> {
                binding.emptyMissionListTextView.text = getString(R.string.empty_mission_list)
                viewModel.changeFilter(MissionFilterType.ALL_MISSIONS)
            }
            R.id.item_filter_active -> {
                binding.emptyMissionListTextView.text = getString(R.string.empty_active_mission_list)
                viewModel.changeFilter(MissionFilterType.ACTIVE_MISSIONS)
            }
            R.id.item_filter_completed -> {
                binding.emptyMissionListTextView.text = getString(R.string.empty_completed_mission_list)
                viewModel.changeFilter(MissionFilterType.COMPLETED_MISSIONS)
            }
            R.id.item_sort_by_rate -> {

            }
            R.id.item_sort_by_name -> {

            }
            R.id.item_sort_by_created_date -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, MissionListActivity::class.java)
            return intent
        }
    }
}

class MissionListAdapter(
        private val missionItemCallback: MissionItemCallback
) : ListAdapter<Mission, MissionListViewHolder>(MissionDiffCallback()) {
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionListViewHolder {
        val binding = ListItemAllMissionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return MissionListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MissionListViewHolder, position: Int) {
        val item = getItem(position)

        //TODO 리팩토링
        with(holder.binding) {
            //디버그 용도
            val percent = item.getTotalAchievePercent()
            missionItemPercentage.text = context.getString(R.string.daily_complete_percent, percent)
            missionItemProgressBar.progress = percent

            //missionItemPercentage.text = context.getString(R.string.daily_complete_percent, item.getTotalAchievePercent())
            //missionItemProgressBar.progress = item.getTotalAchievePercent()

            missionItemTitle.text = item.title
            val calendar = item.createdDate
            missionItemStartDate.text = context.getString(R.string.format_start_date, calendar.get(YEAR).toString(), calendar.get(MONTH).toString(), calendar.get(DAY_OF_MONTH).toString());
            container.setOnClickListener {
                missionItemCallback.openMissionDetail(item.id)
            }
            container.setOnLongClickListener { it ->
                val popup = PopupMenu(context, holder.itemView)
                popup.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.item_remove -> {
                            val builder = AlertDialog.Builder(context)
                            builder.setTitle(R.string.mission_remove_dialog_title)
                                    .setPositiveButton(R.string.yes) { _, _ ->
                                        missionItemCallback.deleteMission(item)
                                    }
                                    .setNegativeButton(R.string.no) { _, _ ->
                                        // do nothing..
                                    }
                                    .show()

                        }
                    }
                    true
                }
                popup.menuInflater.inflate(R.menu.menu_mission_list_pop_up, popup.menu)
                popup.show()
                true
            }
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


package com.melmy.melmyprototype.home

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
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
import com.melmy.melmyprototype.data.MissionType
import com.melmy.melmyprototype.databinding.ActivityHomeBinding
import com.melmy.melmyprototype.databinding.ListItemDailyMissionsBinding
import com.melmy.melmyprototype.missionhistory.HistoryActivity
import com.melmy.melmyprototype.missionlist.MissionListActivity
import com.melmy.melmyprototype.missionlistweek.MissionListWeekActivity
import com.melmy.melmyprototype.utils.InjectorUtil
import com.melmy.melmyprototype.utils.makeInquireReport
import com.melmy.melmyprototype.utils.secondsToStringFormat
import com.melmy.melmyprototype.version.VersionActivity
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
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

        setUpToolbar(binding)
        setUpDrawer(binding)
        setUpViews(binding)
        subscribeUi(binding)

    }

    private fun subscribeUi(binding: ActivityHomeBinding) {
    }

    private fun setUpViews(binding: ActivityHomeBinding) {
        val factory = InjectorUtil.provideHomeViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
                .apply { start() }
        adapter = DailyMissionsAdapter(windowManager, viewModel, binding.confettiView, binding.missionCompletedTextView)
        with(binding) {
            confettiView.setOnClickListener {
                missionCompletedTextView.visibility = View.GONE
                confettiView.visibility = View.GONE
                confettiView.reset()
            }
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
                R.id.menu_review -> {
                    val appPackageName = packageName
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
                    } catch (anfe: android.content.ActivityNotFoundException) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
                    }
                }
                R.id.menu_report -> {
                    val uri = Uri.parse(makeInquireReport(this))
                    val it = Intent(Intent.ACTION_SENDTO, uri)
                    startActivity(it)
                }
                R.id.menu_version -> {
                    startActivity(VersionActivity.createIntent(this))
                }
                //TODO : 오픈소스 라이선스, 개인정보 취급방침 등 작성
                R.id.menu_term_service ->
                    Toast.makeText(this@HomeActivity, getString(R.string.comming_soon), Toast.LENGTH_LONG).show()
                R.id.menu_term_private ->
                    Toast.makeText(this@HomeActivity, getString(R.string.comming_soon), Toast.LENGTH_LONG).show()
                R.id.menu_open_source ->
                    Toast.makeText(this@HomeActivity, getString(R.string.comming_soon), Toast.LENGTH_LONG).show()

            }
            binding.drawerLayout.closeDrawers()

            true
        }
    }
}

class DailyMissionsAdapter(
        private val windowManager: WindowManager,
        val viewModel: HomeViewModel,
        private val confettiView: KonfettiView,
        private val missionCompletedTextView: TextView
) : ListAdapter<Mission, DailyMissionViewHolder>(MissionDiffCallback()) {
    var runningPos = -1 // 미션이 삭제돼서 position 이 이전과 달라질 수도 있음.
    var runningTask: Runnable? = null
    val handler = Handler()

//    fun stopTimer(){
//        runningTask?.let { handler.removeCallbacks(it) }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyMissionViewHolder {
        val binding = ListItemDailyMissionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyMissionViewHolder(binding)
    }


    override fun onBindViewHolder(holder: DailyMissionViewHolder, position: Int) {
        val item = getItem(position)
        // Data Binding 으로 수정
        with(holder.binding) {
            missionTitleTextView.text = item.title
            val percent = item.getDailyAchievePercent()
            missionCompletePercentTextView.text = percent.toString()
            progressBar.percent = percent.toFloat()

            progressBar.setOnClickListener {
                Log.d("sgc109", "onClick Progressbar!")
                if (item.isCompletedToday()) return@setOnClickListener

                when (item.type) {

                    MissionType.COUNT -> {
                        item.accCountsDaily++
                        if (item.isTotallyCompleted()) {
                            item.completedDate = Calendar.getInstance()
                            viewModel.updateDailyMission(item)
                        } else if (item.isCompletedToday()) {
                            missionCompletedTextView.visibility = View.VISIBLE
                            val display = DisplayMetrics()
                            windowManager.defaultDisplay.getMetrics(display)
                            holder.binding.root.context
                            confettiView.visibility = View.VISIBLE
                            confettiView.build()
                                    .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                                    .setDirection(0.0, 359.0)
                                    .setSpeed(1f, 5f)
//                                    .setFadeOutEnabled(true)
                                    .setTimeToLive(2000L)
                                    .addShapes(Shape.RECT, Shape.CIRCLE)
                                    .addSizes(Size(12))
                                    .setPosition(-50f, display.widthPixels + 50f, -50f, display.heightPixels + 50f)
//                                    .setPosition(-50f, confettiView.width + 50f, -50f, -50f)
                                    .streamFor(300, 5000L)
                        }
                        viewModel.updateDailyMission(item)
                    }

                    else -> {
                        runningTask?.let { handler.removeCallbacks(it) }

                        if (runningPos == position) {
                            runningPos = -1
                            runningTask = null
                        } else {
                            runningPos = position
                            runningTask = Runnable {
                                item.accSecondsDaily++
                                viewModel.updateDailyMission(item)
                                timerTextView.text = secondsToStringFormat(item.accSecondsDaily)
                                progressBar.percent = item.getDailyAchievePercent().toFloat()
                                if (item.isCompletedToday()) {
                                    runningPos = -1
                                    runningTask = null
                                    notifyDataSetChanged()
                                    return@Runnable
                                }
                                handler.postDelayed(runningTask, 1000)
                            }
                        }
                    }
                }
                notifyDataSetChanged()
            }

            if (item.isCompletedToday()) {
                percentMarkTextView.visibility = View.GONE
                missionCompletePercentTextView.visibility = View.GONE
                missionCompleteImageView.visibility = View.VISIBLE
                timerTextView.visibility = View.GONE
            } else {
                if (position == runningPos) {
                    timerTextView.text = secondsToStringFormat(item.accSecondsDaily)
                    handler.postDelayed(runningTask, 1000)
                    missionCompletePercentTextView.visibility = View.GONE
                    percentMarkTextView.visibility = View.GONE
                    timerTextView.visibility = View.VISIBLE
                    missionCompleteImageView.visibility = View.GONE
                } else {
                    timerTextView.visibility = View.GONE
                    percentMarkTextView.visibility = View.VISIBLE
                    missionCompletePercentTextView.visibility = View.VISIBLE
                    missionCompleteImageView.visibility = View.GONE
                }
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

class DailyMissionViewHolder(val binding: ListItemDailyMissionsBinding) : RecyclerView.ViewHolder(binding.root) {
    var isRunning = false
}

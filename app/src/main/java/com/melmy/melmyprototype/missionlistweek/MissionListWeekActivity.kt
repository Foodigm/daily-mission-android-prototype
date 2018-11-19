package com.melmy.melmyprototype.missionlistweek

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.melmy.melmyprototype.R
import com.melmy.melmyprototype.databinding.ActivityMissionListWeekBinding
import com.melmy.melmyprototype.utils.InjectorUtil
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class MissionListWeekActivity : AppCompatActivity() {

    lateinit var viewModel: MissionListWeekViewModel
    lateinit var binding: ActivityMissionListWeekBinding
    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mission_list_week)

        setUpToolbar()
        setUpViews()
    }

    override fun onResume() {
        super.onResume()
        viewModel.start()
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
            setDisplayShowTitleEnabled(false) // TODO 왜 다른 액티비티들은 이걸 안해줘도 title 이 안보이는데 이건 안해주면 Theme 을 NoActionBar 로 해도 타이틀이 보이는지 의문..
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setUpViews() {
        val factory = InjectorUtil.provideMissionListWeekViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(MissionListWeekViewModel::class.java)
        with(binding) {
            recyclerView.adapter = this@MissionListWeekActivity.adapter
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, RecyclerView.VERTICAL, false)
            viewModel = this@MissionListWeekActivity.viewModel

            executePendingBindings()
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, MissionListWeekActivity::class.java)
            return intent
        }
    }
}


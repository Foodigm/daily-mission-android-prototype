package com.melmy.melmyprototype.missiondetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.melmy.melmyprototype.R
import com.melmy.melmyprototype.databinding.ActivityMissionDetailBinding
import com.melmy.melmyprototype.utils.InjectorUtil

class MissionDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityMissionDetailBinding
    lateinit var viewModel: MissionDetailViewModel
    var missionId = 1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mission_detail)

        setUpToolbar()
        loadExtras(savedInstanceState)
        setUpViewModel()
        setUpViews()
    }

    private fun setUpViewModel() {
        val factory = InjectorUtil.provideMissionDetailViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(MissionDetailViewModel::class.java)
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setUpViews() {
        val mission = viewModel.getMission(missionId)
        with(binding) {
            /* TODO */
        }
    }


    private fun loadExtras(savedInstanceState: Bundle?) {
        val saved = savedInstanceState ?: intent.extras

        missionId = saved.getLong(EXTRA_MISSION_ID, 1L)
    }

    companion object {
        const val EXTRA_MISSION_ID = "MISSION_ID"

        fun newInstance(context: Context, missionId: Long): Intent {
            val intent = Intent(context, MissionDetailActivity::class.java)
            intent.putExtra(EXTRA_MISSION_ID, missionId)
            return intent
        }
    }

}

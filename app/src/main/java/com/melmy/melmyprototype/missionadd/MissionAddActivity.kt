package com.melmy.melmyprototype.missionadd

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.melmy.melmyprototype.R
import com.melmy.melmyprototype.data.Mission
import com.melmy.melmyprototype.data.MissionType
import com.melmy.melmyprototype.databinding.ActivityMissionAddBinding
import com.melmy.melmyprototype.utils.DayOfWeekSet
import com.melmy.melmyprototype.utils.InjectorUtil

class MissionAddActivity : AppCompatActivity() {
    val dayOfWeekSet = DayOfWeekSet()
    lateinit var viewModel: MissionAddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMissionAddBinding = DataBindingUtil.setContentView(this, R.layout.activity_mission_add)

        setUpViews(binding)
        setUpViewModel()
        debug(binding)
    }

    private fun setUpViewModel() {
        val factory = InjectorUtil.provideMissionAddViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(MissionAddViewModel::class.java)
    }

    fun setUpViews(binding: ActivityMissionAddBinding) {
        with(binding) {
            countMissionVisibility = View.VISIBLE
            timeMissionVisibility = View.GONE

            backImageView.setOnClickListener {
                finish()
            }

            submitTextView.setOnClickListener {
                submitMission(this)
            }

            missionTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when (position) {
                        SPINNER_OPTION_COUNT_MISSION -> {
                            countMissionVisibility = View.VISIBLE
                            timeMissionVisibility = View.GONE
                        }
                        SPINNER_OPTION_TIME_MISSION -> {
                            countMissionVisibility = View.GONE
                            timeMissionVisibility = View.VISIBLE
                        }
                    }
                    executePendingBindings()
                }
            }
            executePendingBindings()
        }
    }

    @SuppressLint("SetTextI18n")
    fun debug(binding: ActivityMissionAddBinding) {
        binding.missionTitleEditText.setText("테스트 미션")
        binding.totalGoalCountEditText.setText("10")
        binding.dailyGoalCountEditText.setText("3")
    }

    fun submitMission(binding: ActivityMissionAddBinding) {
        val title = binding.missionTitleEditText.text.toString()
        val newMission = when (binding.missionTypeSpinner.selectedItemPosition) {
            SPINNER_OPTION_COUNT_MISSION -> {
                Mission(
                        title = title,
                        days = dayOfWeekSet,
                        type = MissionType.COUNT,
                        goalCountTotal = binding.totalGoalCountEditText.text.toString().toInt(),
                        goalCountDaily = binding.dailyGoalCountEditText.text.toString().toInt()
                )
            }
            else -> {
                val totalGoalHour = binding.totalGoalHourEditText.text.toString().toInt()
                val dailyGoalHour = binding.dailyGoalHourEditText.text.toString().toInt()
                // TODO minute 도 더해서 넣어줘야 함

                Mission(
                        title = title,
                        days = dayOfWeekSet,
                        type = MissionType.TIME,
                        goalMinuteTotal = totalGoalHour * 60,
                        goalMinuteDaily = dailyGoalHour * 60
                )
            }
        }
        viewModel.missionRepository.createMission(newMission)
        finish()
    }

    companion object {
        const val SPINNER_OPTION_COUNT_MISSION = 0
        const val SPINNER_OPTION_TIME_MISSION = 1
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, MissionAddActivity::class.java)
            return intent
        }
    }
}

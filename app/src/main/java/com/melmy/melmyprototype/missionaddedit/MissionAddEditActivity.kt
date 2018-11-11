package com.melmy.melmyprototype.missionaddedit

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.melmy.melmyprototype.R
import com.melmy.melmyprototype.data.Mission
import com.melmy.melmyprototype.data.MissionType
import com.melmy.melmyprototype.databinding.ActivityMissionAddBinding
import com.melmy.melmyprototype.utils.DayOfWeekSet
import com.melmy.melmyprototype.utils.InjectorUtil

class MissionAddEditActivity : AppCompatActivity() {
    private val dayOfWeekSet = DayOfWeekSet()
    lateinit var viewModel: MissionAddEditViewModel
    lateinit var binding: ActivityMissionAddBinding
    var initTextColor = Color.BLACK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mission_add)

        setUpViews()
        setUpViewModel()
        debug()
    }

    private fun setUpViewModel() {
        val factory = InjectorUtil.provideMissionAddViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(MissionAddEditViewModel::class.java)
    }

    fun setUpViews() {
        with(binding) {
            countMissionVisibility = View.VISIBLE
            timeMissionVisibility = View.GONE

            backImageView.setOnClickListener {
                finish()
            }

            submitTextView.setOnClickListener {
                submitMission()
            }

            setUpDayPicker()

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

    private fun setUpDayPicker() {
        with(binding) {
            dayPickerItemMonday.setOnClickListener {
                dayOfWeekSet.toggleMonday()
                toggleDayPickerBgColor(it as TextView)
            }
            dayPickerItemTuesday.setOnClickListener {
                dayOfWeekSet.toggleTuesday()
                toggleDayPickerBgColor(it as TextView)
            }
            dayPickerItemWednesday.setOnClickListener {
                dayOfWeekSet.toggleWednesday()
                toggleDayPickerBgColor(it as TextView)
            }
            dayPickerItemThursday.setOnClickListener {
                dayOfWeekSet.toggleThursday()
                toggleDayPickerBgColor(it as TextView)
            }
            dayPickerItemFriday.setOnClickListener {
                dayOfWeekSet.toggleFriday()
                toggleDayPickerBgColor(it as TextView)
            }
            dayPickerItemSaturday.setOnClickListener {
                dayOfWeekSet.toggleSaturday()
                toggleDayPickerBgColor(it as TextView)
            }
            dayPickerItemSunday.setOnClickListener {
                dayOfWeekSet.toggleSunday()
                toggleDayPickerBgColor(it as TextView)
            }
        }
    }

    private fun toggleDayPickerBgColor(textView: TextView) {
        val color = (textView.background as? ColorDrawable)?.color ?: Color.TRANSPARENT
        val colorFilled = ResourcesCompat.getColor(resources, R.color.colorPrimary, null)
        val colorEmpty  = 0xffffffff.toInt()
        when(color) {
            colorFilled -> {
                textView.setBackgroundColor(colorEmpty)
                textView.setTextColor(initTextColor)
            }
            else -> {
                initTextColor = textView.currentTextColor
                textView.setBackgroundColor(colorFilled)
                textView.setTextColor(Color.WHITE)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun debug() {
        binding.missionTitleEditText.setText("테스트 미션")
        binding.totalGoalCountEditText.setText("10")
        binding.dailyGoalCountEditText.setText("3")
    }

    fun submitMission() {
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
            val intent = Intent(context, MissionAddEditActivity::class.java)
            return intent
        }
    }
}

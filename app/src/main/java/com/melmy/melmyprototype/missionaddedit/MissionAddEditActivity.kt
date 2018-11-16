package com.melmy.melmyprototype.missionaddedit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.melmy.melmyprototype.R
import com.melmy.melmyprototype.data.Mission
import com.melmy.melmyprototype.data.MissionType
import com.melmy.melmyprototype.databinding.ActivityMissionAddBinding
import com.melmy.melmyprototype.utils.InjectorUtil

//TODO : 하드코딩 제거 - 레이아웃 파일 포함
class MissionAddEditActivity : AppCompatActivity() {
    lateinit var viewModel: MissionAddEditViewModel
    lateinit var binding: ActivityMissionAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mission_add)

        setUpViews()
        setUpViewModel()
        //debug()
    }

    private fun setUpViewModel() {
        val factory = InjectorUtil.provideMissionAddViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(MissionAddEditViewModel::class.java)
    }


    private fun setUpViews() {
        with(binding) {
            countMissionVisibility = View.VISIBLE
            timeMissionVisibility = View.GONE

            backImageView.setOnClickListener {
                finish()
            }

            submitTextView.setOnClickListener {
                submitMission()
            }

            missionTypeCount.setOnClickListener {
                timeMissionVisibility = View.GONE
                countMissionVisibility = View.VISIBLE

                missionTypeTime.isChecked = false
                missionTypeCount.isChecked = true

                missionTypeCount.setTextColor(ContextCompat.getColor(this@MissionAddEditActivity, R.color.colorWhite))
                missionTypeTime.setTextColor(ContextCompat.getColor(this@MissionAddEditActivity, R.color.colorGray4))
            }

            missionTypeTime.setOnClickListener {
                countMissionVisibility = View.GONE
                timeMissionVisibility = View.VISIBLE

                missionTypeTime.isChecked = true
                missionTypeCount.isChecked = false

                missionTypeCount.setTextColor(ContextCompat.getColor(this@MissionAddEditActivity, R.color.colorGray4))
                missionTypeTime.setTextColor(ContextCompat.getColor(this@MissionAddEditActivity, R.color.colorWhite))
            }

            missionTypeCountTotalMinus.setOnClickListener {
                try {
                    val original = Integer.parseInt(missionTypeCountTotalAmount.text.toString())
                    val new = if (original <= 0) 0 else original - 1
                    missionTypeCountTotalAmount.setText(new.toString())
                } catch (e: NumberFormatException) {
                    Toast.makeText(this@MissionAddEditActivity, "잘못된 숫자입니다.", Toast.LENGTH_SHORT).show()
                }
            }

            missionTypeCountTotalPlus.setOnClickListener {
                try {
                    val original = Integer.parseInt(missionTypeCountTotalAmount.text.toString())
                    val new = if (original >= 99999) 99999 else original + 1
                    missionTypeCountTotalAmount.setText(new.toString())
                } catch (e: NumberFormatException) {
                    Toast.makeText(this@MissionAddEditActivity, "잘못된 숫자입니다.", Toast.LENGTH_SHORT).show()
                }
            }

            missionTypeCountDailyMinus.setOnClickListener {
                try {
                    val original = Integer.parseInt(missionTypeCountDailyAmount.text.toString())
                    val new = if (original <= 0) 0 else original - 1
                    missionTypeCountDailyAmount.setText(new.toString())
                } catch (e: NumberFormatException) {
                    Toast.makeText(this@MissionAddEditActivity, "잘못된 숫자입니다.", Toast.LENGTH_SHORT).show()
                }
            }

            missionTypeCountDailyPlus.setOnClickListener {
                try {
                    val original = Integer.parseInt(missionTypeCountDailyAmount.text.toString())
                    val new = if (original >= 99999) 99999 else original + 1
                    missionTypeCountDailyAmount.setText(new.toString())
                } catch (e: NumberFormatException) {
                    Toast.makeText(this@MissionAddEditActivity, "잘못된 숫자입니다.", Toast.LENGTH_SHORT).show()
                }
            }
            executePendingBindings()
        }
    }

    /*
    @SuppressLint("SetTextI18n")
    fun debug() {
        binding.missionTitleEditText.setText("테스트 미션")
        binding.totalGoalCountEditText.setText("10")
        binding.dailyGoalCountEditText.setText("3")
    }
    */

    //TODO : INVALID CHECK - 제목 글자수, 비어있는지, 데일리가 토탈을 넘지는 않는지 등
    private fun submitMission() {
        val title = binding.missionTitleEditText.text.toString()
        val dayOfWeekSet = binding.missionDayPicker.dayOfWeekSet
        val newMission = when (binding.countMissionVisibility) {
            View.VISIBLE -> {
                Mission(
                        title = title,
                        days = dayOfWeekSet,
                        type = MissionType.COUNT,
                        goalCountTotal = binding.missionTypeCountTotalAmount.text.toString().toInt(),
                        goalCountDaily = binding.missionTypeCountDailyAmount.text.toString().toInt()
                )
            }
            else -> {
                //TODO 시간 모드 구현
                val totalGoalHour = 60 /* DEBUG */
                val dailyGoalHour = 20 /* DEBUG */

                //val totalGoalHour = binding.totalGoalHourEditText.text.toString().toInt()
                //val dailyGoalHour = binding.dailyGoalHourEditText.text.toString().toInt()
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

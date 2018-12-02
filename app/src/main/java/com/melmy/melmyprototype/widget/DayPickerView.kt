package com.melmy.melmyprototype.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckedTextView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.melmy.melmyprototype.R
import com.melmy.melmyprototype.databinding.ViewDayPickerBinding
import com.melmy.melmyprototype.utils.DayOfWeekSet

class DayPickerView : LinearLayout, View.OnClickListener {
    val dayOfWeekSet = DayOfWeekSet()
    lateinit var binding: ViewDayPickerBinding
    lateinit var mContext: Context

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        mContext = context

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.view_day_picker, this, true)

        binding.dayPickerItemMonday.setOnClickListener(this)
        binding.dayPickerItemTuesday.setOnClickListener(this)
        binding.dayPickerItemWednesday.setOnClickListener(this)
        binding.dayPickerItemThursday.setOnClickListener(this)
        binding.dayPickerItemFriday.setOnClickListener(this)
        binding.dayPickerItemSaturday.setOnClickListener(this)
        binding.dayPickerItemSunday.setOnClickListener(this)
    }

    fun initWithDayOfWeekSet(days: DayOfWeekSet) {
        toggleColor(binding.dayPickerItemMonday, days.monday)
        toggleColor(binding.dayPickerItemTuesday, days.tuesday)
        toggleColor(binding.dayPickerItemWednesday, days.wednesday)
        toggleColor(binding.dayPickerItemThursday, days.thursday)
        toggleColor(binding.dayPickerItemFriday, days.friday)
        toggleColor(binding.dayPickerItemSaturday, days.saturday)
        toggleColor(binding.dayPickerItemSunday, days.sunday)
        if (days.monday) dayOfWeekSet.toggleMonday()
        if (days.tuesday) dayOfWeekSet.toggleTuesday()
        if (days.wednesday) dayOfWeekSet.toggleWednesday()
        if (days.thursday) dayOfWeekSet.toggleThursday()
        if (days.friday) dayOfWeekSet.toggleFriday()
        if (days.saturday) dayOfWeekSet.toggleSaturday()
        if (days.sunday) dayOfWeekSet.toggleSunday()
    }

    override fun onClick(v: View) {
        //Checked TextView 일때만 실행
        if (v !is CheckedTextView) return

        when (v.getId()) {
            R.id.day_picker_item_monday -> {
                dayOfWeekSet.toggleMonday()
                toggleColor(v, dayOfWeekSet.monday)
            }
            R.id.day_picker_item_tuesday -> {
                dayOfWeekSet.toggleTuesday()
                toggleColor(v, dayOfWeekSet.tuesday)
            }
            R.id.day_picker_item_wednesday -> {
                dayOfWeekSet.toggleWednesday()
                toggleColor(v, dayOfWeekSet.wednesday)
            }
            R.id.day_picker_item_thursday -> {
                dayOfWeekSet.toggleThursday()
                toggleColor(v, dayOfWeekSet.thursday)
            }
            R.id.day_picker_item_friday -> {
                dayOfWeekSet.toggleFriday()
                toggleColor(v, dayOfWeekSet.friday)
            }
            R.id.day_picker_item_saturday -> {
                dayOfWeekSet.toggleSaturday()
                toggleColor(v, dayOfWeekSet.saturday)
            }
            R.id.day_picker_item_sunday -> {
                dayOfWeekSet.toggleSunday()
                toggleColor(v, dayOfWeekSet.sunday)
            }
        }
    }

    private fun toggleColor(view: CheckedTextView, toggled: Boolean) {
        view.isChecked = toggled
        val textColor = if (toggled) {
            ContextCompat.getColor(mContext, R.color.colorPrimaryDark)
        } else {
            if (view.id == R.id.day_picker_item_sunday)
                ContextCompat.getColor(mContext, R.color.colorRed)
            else if (view.id == R.id.day_picker_item_saturday)
                ContextCompat.getColor(mContext, R.color.colorBlueGray)
            else
                ContextCompat.getColor(mContext, R.color.colorGray3)
        }
        view.setTextColor(textColor)
    }
}

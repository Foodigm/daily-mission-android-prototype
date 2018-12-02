package com.melmy.melmyprototype.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TableLayout
import androidx.databinding.DataBindingUtil
import com.melmy.melmyprototype.R
import com.melmy.melmyprototype.databinding.ViewGoalHistoryBinding

class GoalHistoryView : TableLayout {

    lateinit var mContext: Context
    lateinit var binding: ViewGoalHistoryBinding

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        mContext = context

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.view_goal_history, this, true)
    }
}
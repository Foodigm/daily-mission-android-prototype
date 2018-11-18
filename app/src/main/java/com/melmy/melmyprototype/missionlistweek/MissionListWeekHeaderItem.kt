package com.melmy.melmyprototype.missionlistweek

import com.melmy.melmyprototype.R
import com.melmy.melmyprototype.databinding.ListHeaderItemMissionWeekBinding
import com.xwray.groupie.databinding.BindableItem

class MissionListWeekHeaderItem : BindableItem<ListHeaderItemMissionWeekBinding>() {
    override fun bind(viewBinding: ListHeaderItemMissionWeekBinding, position: Int) {
        val daysArray = viewBinding.root.context.resources.getStringArray(R.array.days_of_week_full)
        viewBinding.listHeaderItemMissionWeekTextView.text = daysArray.get(position).toString()
    }

    override fun getLayout(): Int {
        return R.layout.list_header_item_mission_week
    }
}

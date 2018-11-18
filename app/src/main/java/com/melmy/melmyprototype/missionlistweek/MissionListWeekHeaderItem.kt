package com.melmy.melmyprototype.missionlistweek

import com.melmy.melmyprototype.R
import com.melmy.melmyprototype.databinding.ListHeaderItemMissionWeekBinding
import com.xwray.groupie.databinding.BindableItem

class MissionListWeekHeaderItem(val dayOfWeekName: String) : BindableItem<ListHeaderItemMissionWeekBinding>() {
    override fun bind(viewBinding: ListHeaderItemMissionWeekBinding, position: Int) {
        viewBinding.listHeaderItemMissionWeekTextView.text = dayOfWeekName
    }

    override fun getLayout(): Int {
        return R.layout.list_header_item_mission_week
    }
}

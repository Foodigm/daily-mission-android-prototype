package com.melmy.melmyprototype.missionlistweek

import com.melmy.melmyprototype.R
import com.melmy.melmyprototype.data.Mission
import com.xwray.groupie.databinding.BindableItem
import com.melmy.melmyprototype.databinding.ListItemMissionWeekBinding

class MissionItem(val mission: Mission) : BindableItem<ListItemMissionWeekBinding>() {
    override fun getLayout(): Int {
        return R.layout.list_item_mission_week
    }

    override fun bind(viewBinding: ListItemMissionWeekBinding, position: Int) {
        viewBinding.textView.text = mission.title
        viewBinding.progressBar.progress = mission.getAchievePercent()
    }
}

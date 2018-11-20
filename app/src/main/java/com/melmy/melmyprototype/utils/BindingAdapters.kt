package com.melmy.melmyprototype.utils

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.melmy.melmyprototype.R
import com.melmy.melmyprototype.data.Mission
import com.melmy.melmyprototype.missionlist.MissionListViewHolder
import com.melmy.melmyprototype.missionlistweek.MissionItem
import com.melmy.melmyprototype.missionlistweek.MissionListWeekHeaderItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import java.util.*


@BindingAdapter("items")
fun bindHomeRecyclerViewItems(recyclerView: RecyclerView, missions: ObservableField<List<Mission>>) {
    missions.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            (recyclerView.adapter as ListAdapter<Mission, MissionListViewHolder>).submitList(missions.get())
        }
    })
}

@BindingAdapter("items_week")
fun bindMissionListWeekReclcerViewItems(recyclerView: RecyclerView, missions: ObservableField<List<Mission>>) {
    missions.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            Log.d("sgc109", "onPropertyChanged!")

            val missions2D = Array(7) { ArrayList<Mission>() }
            missions.get()?.let {
                for (mission in it) {
                    if (mission.days.monday) missions2D[0].add(mission)
                    if (mission.days.tuesday) missions2D[1].add(mission)
                    if (mission.days.wednesday) missions2D[2].add(mission)
                    if (mission.days.thursday) missions2D[3].add(mission)
                    if (mission.days.friday) missions2D[4].add(mission)
                    if (mission.days.saturday) missions2D[5].add(mission)
                    if (mission.days.sunday) missions2D[6].add(mission)
                }
            }

            val adapter = (recyclerView.adapter as GroupAdapter<ViewHolder>)
            val sections = Array(7) { Section() }
            val daysArray = recyclerView.context.resources.getStringArray(R.array.days_of_week_full)
            for (i in 0..6) {
                sections[i].setHeader(MissionListWeekHeaderItem(daysArray[i]))
                adapter.add(sections[i])
                for (mission in missions2D[i]) {
                    adapter.add(MissionItem(mission))
                }
            }
        }
    })
}

@BindingAdapter("setCalendar")
fun bindCalendar(view: TextView, calendar: Calendar) {
    view.text = view.context.getString(R.string.format_start_date, calendar.get(Calendar.YEAR).toString(), calendar.get(Calendar.MONTH).toString(), calendar.get(Calendar.DAY_OF_MONTH).toString())
}

@BindingAdapter("app:setVisibility")
fun setEmptyTextViewVisibility(view: View, missions: ObservableField<List<Mission>>) {
    missions.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            missions.get()?.let {
                view.visibility = when (it.size) {
                    0 -> View.VISIBLE
                    else -> View.GONE
                }
            }
        }

    })
}

@BindingAdapter("app:drawerMissions")
fun bindDrawerMissions(navigationView: NavigationView, missions: ObservableField<List<Mission>>) {
    missions.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            missions.get()?.let { missions ->
                val view = navigationView.getHeaderView(0)
                val textView = view.findViewById(R.id.percent_text_view) as TextView
                var accUpper = 0
                var accLower = 0
                for (mission in missions) {
                    accUpper += mission.accCountsDaily
                    accLower += mission.goalCountsDaily
                }
                textView.text = ((accUpper.toFloat() / accLower.toFloat()) * 100).toInt().toString()
            }
        }

    })
}
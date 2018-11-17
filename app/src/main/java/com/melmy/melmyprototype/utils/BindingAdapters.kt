package com.melmy.melmyprototype.utils

import androidx.databinding.BindingAdapter
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.melmy.melmyprototype.data.Mission
import com.melmy.melmyprototype.missionlist.MissionListViewHolder


@BindingAdapter("items")
fun bindHomeRecyclerViewItems(recyclerView: RecyclerView, missions: ObservableField<List<Mission>>) {
    missions.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            (recyclerView.adapter as ListAdapter<Mission, MissionListViewHolder>).submitList(missions.get())
        }

    })
}

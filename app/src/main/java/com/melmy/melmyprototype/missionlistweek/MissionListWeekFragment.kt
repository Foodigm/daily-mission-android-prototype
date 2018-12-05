package com.melmy.melmyprototype.missionlistweek

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.melmy.melmyprototype.R
import com.melmy.melmyprototype.utils.DayOfWeekSet

//TODO : 탭 레이아웃 포함해서 구현 : 전체보기, 월요일, 화요일, 수요일 ... 이런식으로
class MissionListWeekFragment : Fragment() {
    //private lateinit var dayOfWeek: DayOfWeekSet //TODO : 요일별로 분류할 방법 생각...

    //private var listener: OnListFragmentInteractionListener? = null

    //override fun onCreate(savedInstanceState: Bundle?) {
    //    super.onCreate(savedInstanceState)

    //    arguments?.let {
    //        columnCount = it.getInt(ARG_COLUMN_COUNT)
    //    }
    //}

    //override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    //    val view = inflater.inflate(R.layout.fragment_mission_list_week, container, false)

    //    if (view is RecyclerView) {
    //        with(view) {
    //            layoutManager = when {
    //                columnCount <= 1 -> LinearLayoutManager(context)
    //                else -> GridLayoutManager(context, columnCount)
    //            }
    //            /*
    //            adapter = MyItemRecyclerViewAdapter(DummyContent.ITEMS, listener)
    //            */
    //        }
    //    }
    //    return view
    //}

    //override fun onAttach(context: Context) {
    //    super.onAttach(context)
    //    if (context is OnListFragmentInteractionListener) {
    //        listener = context
    //    } else {
    //        throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
    //    }
    //}

    //override fun onDetach() {
    //    super.onDetach()
    //    listener = null
    //}

    ///**
    // * This interface must be implemented by activities that contain this
    // * fragment to allow an interaction in this fragment to be communicated
    // * to the activity and potentially other fragments contained in that
    // * activity.
    // *
    // *
    // * See the Android Training lesson
    // * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
    // * for more information.
    // */
    //interface OnListFragmentInteractionListener {
    //    // TODO: Update argument type and name
    //    fun onListFragmentInteraction(item: DummyItem?)
    //}


    companion object {
        const val ARG_DAY_OF_WEEK = "day"

        @JvmStatic
        fun newInstance(columnCount: Int) =
                MissionListWeekFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_DAY_OF_WEEK, columnCount)
                    }
                }
    }
}

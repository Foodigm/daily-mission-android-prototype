package com.melmy.melmyprototype.missionlistweek

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.melmy.melmyprototype.R
import com.melmy.melmyprototype.databinding.FragmentMissionListWeekBinding
import com.melmy.melmyprototype.utils.InjectorUtil
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

//TODO : 탭 레이아웃 포함해서 구현 : 전체보기, 월요일, 화요일, 수요일 ... 이런식으로
class MissionListWeekFragment : Fragment() {
    lateinit var viewModel: MissionListWeekViewModel
    lateinit var binding: FragmentMissionListWeekBinding
    val adapter = GroupAdapter<ViewHolder>()

    private var tabIndex = 0

    override fun onResume() {
        super.onResume()
        viewModel.start(tabIndex)
    }

    private fun setUpViews(context: Context) {
        val factory = InjectorUtil.provideMissionListWeekViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(MissionListWeekViewModel::class.java)
        binding.viewmodel = viewModel
        with(binding) {
            recyclerView.adapter = this@MissionListWeekFragment.adapter
            recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(recyclerView.context, androidx.recyclerview.widget.RecyclerView.VERTICAL, false)

            executePendingBindings()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            tabIndex = it.getInt(ARG_TAB_INDEX)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mission_list_week, container, false)

        val view = binding.root

        context?.let { setUpViews(it) }

        return view
    }

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
        const val ARG_TAB_INDEX = "tab_index"

        @JvmStatic
        fun newInstance(index: Int) =
                MissionListWeekFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_TAB_INDEX, index)
                    }
                }
    }
}

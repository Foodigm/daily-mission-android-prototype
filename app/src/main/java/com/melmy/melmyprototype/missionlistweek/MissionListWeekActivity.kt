package com.melmy.melmyprototype.missionlistweek

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.melmy.melmyprototype.R
import com.melmy.melmyprototype.databinding.ActivityMissionListWeekBinding

class MissionListWeekActivity : AppCompatActivity() {

    lateinit var binding: ActivityMissionListWeekBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mission_list_week)

        setUpToolbar()
        setUpViewPager()
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setUpViewPager() {
        with(binding) {
            missionListWeekViewPager.adapter = SectionsPagerAdapter(supportFragmentManager, resources.getStringArray(R.array.tab_indicator_week))
            missionListWeekTabLayout.setupWithViewPager(missionListWeekViewPager)
            executePendingBindings()
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, MissionListWeekActivity::class.java)
            return intent
        }
    }

    class SectionsPagerAdapter(fm: FragmentManager, private val tabNameArray: Array<String>) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return MissionListWeekFragment.newInstance(position)
        }

        override fun getCount(): Int {
            return tabNameArray.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabNameArray[position]
        }
    }
}


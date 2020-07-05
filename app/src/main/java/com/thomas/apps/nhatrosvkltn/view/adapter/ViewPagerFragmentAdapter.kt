package com.thomas.apps.nhatrosvkltn.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.thomas.apps.nhatrosvkltn.model.FilterModel
import com.thomas.apps.nhatrosvkltn.view.screens.home.HomeFragment
import com.thomas.apps.nhatrosvkltn.view.screens.search.SearchFragment
import java.util.*


class ViewPagerFragmentAdapter(
    fragmentActivity: FragmentActivity,
    private val fragments: ArrayList<Fragment>
) :
    FragmentStateAdapter(fragmentActivity), HomeFragment.OnFilterListener {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    override fun onFilter(filterModel: FilterModel) {
        (fragments[1] as SearchFragment).onFilter(filterModel)
    }
}
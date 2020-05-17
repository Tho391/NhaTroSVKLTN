package com.thomas.apps.nhatrosvkltn.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.thomas.apps.nhatrosvkltn.databinding.ActivityMainBinding
import com.thomas.apps.nhatrosvkltn.utils.DepthPageTransformer
import com.thomas.apps.nhatrosvkltn.utils.hideSoftKeyboard
import com.thomas.apps.nhatrosvkltn.view.adapter.ViewPagerFragmentAdapter
import com.thomas.apps.nhatrosvkltn.view.ui.filters.FiltersFragment
import com.thomas.apps.nhatrosvkltn.view.ui.home.HomeFragment
import com.thomas.apps.nhatrosvkltn.view.ui.home.HomeViewModel
import com.thomas.apps.nhatrosvkltn.view.ui.profile.ProfileFragment
import com.thomas.apps.nhatrosvkltn.view.ui.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        init()

    }

    private fun init() {
        viewModel.loadApartments()

        binding.navigationConstraint.setNavigationChangeListener { _, position ->
            binding.viewPager.setCurrentItem(position, true)
        }

        val fragmentList = arrayListOf(
            HomeFragment.newInstance(),
            SearchFragment.newInstance(),
            ProfileFragment.newInstance()
        )
        binding.viewPager.setPageTransformer(DepthPageTransformer())
        binding.viewPager.adapter = ViewPagerFragmentAdapter(this, fragmentList)
        binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                binding.navigationConstraint.setCurrentActiveItem(position)
                hideSoftKeyboard()
            }
        })
    }
}

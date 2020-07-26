package com.thomas.apps.nhatrosvkltn.view.screens

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.gms.ads.MobileAds
import com.thomas.apps.nhatrosvkltn.databinding.ActivityMainBinding
import com.thomas.apps.nhatrosvkltn.model.FilterModel
import com.thomas.apps.nhatrosvkltn.utils.DepthPageTransformer
import com.thomas.apps.nhatrosvkltn.utils.hideSoftKeyboard
import com.thomas.apps.nhatrosvkltn.view.adapter.ViewPagerFragmentAdapter
import com.thomas.apps.nhatrosvkltn.view.screens.home.HomeFragment
import com.thomas.apps.nhatrosvkltn.view.screens.profile.ProfileFragment
import com.thomas.apps.nhatrosvkltn.view.screens.search.SearchFragment


class MainActivity : AppCompatActivity(), HomeFragment.OnFilterListener {

    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerFragmentAdapter: ViewPagerFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initAds()

        setupView()

    }

    private fun initAds() {
        MobileAds.initialize(this) {
            Log.i(TAG, it.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    private fun setupView() {

        val fragmentList = arrayListOf(
            HomeFragment(),
            SearchFragment(),
            ProfileFragment()
        )
        with(binding) {
            navigationConstraint.setNavigationChangeListener { _, position ->
                viewPager.setCurrentItem(position, true)
            }
            viewPagerFragmentAdapter = ViewPagerFragmentAdapter(this@MainActivity, fragmentList)
            viewPager.setPageTransformer(DepthPageTransformer())
            viewPager.adapter = viewPagerFragmentAdapter
            viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    navigationConstraint.setCurrentActiveItem(position)
                    hideSoftKeyboard()
                }
            })
        }

        viewModel.loadApartments()
    }

    override fun onBackPressed() {
        when (binding.viewPager.currentItem) {
            1, 2 -> binding.viewPager.currentItem = 0
            else -> super.onBackPressed()
        }
    }

    override fun onFilter(filterModel: FilterModel) {
        viewPagerFragmentAdapter.onFilter(filterModel)
    }

}

private const val TAG = "MainAct Tag"
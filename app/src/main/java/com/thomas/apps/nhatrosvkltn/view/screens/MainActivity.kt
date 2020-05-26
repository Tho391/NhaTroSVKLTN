package com.thomas.apps.nhatrosvkltn.view.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.gson.JsonElement
import com.thomas.apps.nhatrosvkltn.api.ApiResponse
import com.thomas.apps.nhatrosvkltn.api.Status
import com.thomas.apps.nhatrosvkltn.databinding.ActivityMainBinding
import com.thomas.apps.nhatrosvkltn.utils.DepthPageTransformer
import com.thomas.apps.nhatrosvkltn.utils.hideSoftKeyboard
import com.thomas.apps.nhatrosvkltn.view.adapter.ViewPagerFragmentAdapter
import com.thomas.apps.nhatrosvkltn.view.screens.home.HomeFragment
import com.thomas.apps.nhatrosvkltn.view.screens.profile.ProfileFragment
import com.thomas.apps.nhatrosvkltn.view.screens.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
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

        val fragmentList = arrayListOf(
            HomeFragment.newInstance(),
            SearchFragment.newInstance(),
            ProfileFragment.newInstance()
        )
        with(binding) {
            navigationConstraint.setNavigationChangeListener { _, position ->
                viewPager.setCurrentItem(position, true)
            }

            viewPager.setPageTransformer(DepthPageTransformer())
            viewPager.adapter = ViewPagerFragmentAdapter(this@MainActivity, fragmentList)
            viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    navigationConstraint.setCurrentActiveItem(position)
                    hideSoftKeyboard()
                }
            })
        }

        viewModel.loadApartments()
    }

    /*
* method to handle response
* */
    private fun consumeResponse(apiResponse: ApiResponse) {
        when (apiResponse.status) {
            Status.LOADING -> progressBar.visibility = View.VISIBLE
            Status.SUCCESS -> {
                progressBar.visibility = View.GONE
                apiResponse.data?.let { renderSuccessResponse(it) }
            }
            Status.ERROR -> {
                progressBar.visibility = View.GONE
                Toast.makeText(this, "error string", Toast.LENGTH_SHORT).show()
            }
            else -> {
            }
        }
    }

    /*
* method to handle success response
* */
    private fun renderSuccessResponse(response: JsonElement) {
        if (!response.isJsonNull) {
            Log.d("response=", response.toString())
        } else {
            Toast.makeText(this, "error string", Toast.LENGTH_SHORT).show()
        }
    }
}

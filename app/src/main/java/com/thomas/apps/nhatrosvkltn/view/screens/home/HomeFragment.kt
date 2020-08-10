package com.thomas.apps.nhatrosvkltn.view.screens.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import coil.api.load
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.FragmentHomeBinding
import com.thomas.apps.nhatrosvkltn.model.FilterModel
import com.thomas.apps.nhatrosvkltn.utils.getUser
import com.thomas.apps.nhatrosvkltn.utils.launchActivity
import com.thomas.apps.nhatrosvkltn.view.adapter.ApartmentAdapter
import com.thomas.apps.nhatrosvkltn.view.screens.MainActivity
import com.thomas.apps.nhatrosvkltn.view.screens.MainViewModel
import com.thomas.apps.nhatrosvkltn.view.screens.addapartment.AddApartmentActivity
import com.thomas.apps.nhatrosvkltn.view.screens.login.LoginActivity
import com.thomas.apps.nhatrosvkltn.view.screens.map.MapActivity


class HomeFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPager: ViewPager2

    private val recyclerAdapter by lazy { ApartmentAdapter() }

    private lateinit var onFilterListener: OnFilterListener

    interface OnFilterListener {
        fun onFilter(filterModel: FilterModel)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            onFilterListener = context as OnFilterListener
        } catch (e: Exception) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        viewPager = (activity as MainActivity).findViewById(R.id.viewPager)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        init()
        return binding.root
    }

    private fun init() {
        with(binding) {
            imageButtonMap.setOnClickListener { requireContext().launchActivity<MapActivity> { } }
            imageButtonSearch.setOnClickListener { viewPager.setCurrentItem(1, true) }
            imageDistrict1.setOnClickListener {
                //viewModel.search("Quận 10")
                viewPager.setCurrentItem(1, true)
                onFilterListener.onFilter(
                    FilterModel(
                        districtId = 10
                    )
                )
            }
            imageDistrict2.setOnClickListener {
                viewPager.setCurrentItem(1, true)
                onFilterListener.onFilter(
                    FilterModel(
                        districtId = 3
                    )
                )

            }
            imageDistrict3.setOnClickListener {
                viewPager.setCurrentItem(1, true)
                onFilterListener.onFilter(
                    FilterModel(
                        districtId = 13
                    )
                )
            }
            imageDistrict4.setOnClickListener {
                viewPager.setCurrentItem(1, true)
                onFilterListener.onFilter(
                    FilterModel(
                        districtId = 1
                    )
                )
            }
            imageDistrict5.setOnClickListener {
                viewPager.setCurrentItem(1, true)
                onFilterListener.onFilter(
                    FilterModel(
                        districtId = 17
                    )
                )
            }
            imageDistrict6.setOnClickListener {
                viewPager.setCurrentItem(1, true)
                onFilterListener.onFilter(
                    FilterModel(
                        districtId = 7
                    )
                )
            }

            imageDistrict1.load(R.mipmap.quan10) {
                size(100, 100)
            }
            imageDistrict2.load(R.mipmap.quan3) {
                size(100, 100)
            }
            imageDistrict3.load(R.mipmap.thuduc) {
                size(100, 100)
            }
            imageDistrict4.load(R.mipmap.quan1) {
                size(100, 100)
            }
            imageDistrict5.load(R.mipmap.binhthanh) {
                size(100, 100)
            }
            imageDistrict6.load(R.mipmap.quan7) {
                size(100, 100)
            }

            swipeRefreshLayout.setOnRefreshListener {
                viewModel.loadApartments()
            }
            swipeRefreshLayout.post {
                viewModel.loadApartments()
            }

            recyclerViewApartments.adapter = recyclerAdapter
            recyclerViewApartments.layoutManager = object : LinearLayoutManager(requireContext()) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }

            imageButtonAddApartment.setOnClickListener {
                val user = getUser(requireContext())
                if (user != null) {
//                    context?.launchActivity<AddApartmentActivity> { }
//                    val intent = Intent(context,AddApartmentActivity::class.java)
//                    startActivity(intent)
                    Handler().post {
                        context?.launchActivity<AddApartmentActivity> { }
                    }
                } else {
//                    context?.launchActivity<LoginActivity> { }

                    val intent = Intent(context, LoginActivity::class.java)
                    startActivity(intent)
                }
            }

        }

        viewModel.apartments.observe(viewLifecycleOwner, Observer { apartments ->
            recyclerAdapter.submitList(apartments)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.swipeRefreshLayout.isRefreshing = it
        })


    }
}

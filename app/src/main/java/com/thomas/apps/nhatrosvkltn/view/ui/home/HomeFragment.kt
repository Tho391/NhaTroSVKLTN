package com.thomas.apps.nhatrosvkltn.view.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.FragmentHomeBinding
import com.thomas.apps.nhatrosvkltn.utils.launchActivity
import com.thomas.apps.nhatrosvkltn.view.adapter.RecyclerAdapter
import com.thomas.apps.nhatrosvkltn.view.ui.MainActivity
import com.thomas.apps.nhatrosvkltn.view.ui.map.MapActivity
import kotlinx.android.synthetic.main.activity_main.view.*


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPager: ViewPager2

    private lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        viewPager = (activity as MainActivity).findViewById(R.id.viewPager)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        //viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        // TODO: Use the ViewModel

        binding.imageButtonMap.setOnClickListener { requireContext().launchActivity<MapActivity> {  } }
        binding.imageButtonSearch.setOnClickListener { viewPager.setCurrentItem(1,true) }
        binding.imageButtonAddApartment.setOnClickListener { viewPager.setCurrentItem(2,true) }
        binding.imageDistrict1.setOnClickListener { onDistrict1() }
        binding.imageDistrict2.setOnClickListener { onDistrict2() }
        binding.imageDistrict3.setOnClickListener { onDistrict3() }
        binding.imageDistrict4.setOnClickListener { onDistrict4() }
        binding.imageDistrict5.setOnClickListener { onDistrict5() }
        binding.imageDistrict6.setOnClickListener { onDistrict6() }

        updateUI()
    }

    private fun updateUI() {
        //TODO("Not yet implemented")
    }

    private fun onDistrict1() {
        viewModel.onDistrict1()
    }

    private fun onDistrict2() {
        //TODO("Not yet implemented")
    }

    private fun onDistrict3() {
        //TODO("Not yet implemented")
    }

    private fun onDistrict4() {
        //TODO("Not yet implemented")
    }

    private fun onDistrict5() {
        //TODO("Not yet implemented")
    }

    private fun onDistrict6() {
        //TODO("Not yet implemented")
    }

    private fun onAddApartment() {
        //TODO("Not yet implemented")
    }

    private fun onSearch() {
        //TODO("Not yet implemented")
    }

    private fun onMap() {
        //TODO("Not yet implemented")
    }

}

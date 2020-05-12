package com.thomas.apps.nhatrosvkltn.view.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.FragmentHomeBinding
import com.thomas.apps.nhatrosvkltn.model.Apartment
import com.thomas.apps.nhatrosvkltn.model.Image
import com.thomas.apps.nhatrosvkltn.model.User
import com.thomas.apps.nhatrosvkltn.utils.launchActivity
import com.thomas.apps.nhatrosvkltn.view.adapter.ApartmentAdapter
import com.thomas.apps.nhatrosvkltn.view.ui.MainActivity
import com.thomas.apps.nhatrosvkltn.view.ui.map.MapActivity


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPager: ViewPager2

    private val recyclerAdapter by lazy { ApartmentAdapter() }

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

        binding.imageButtonMap.setOnClickListener { requireContext().launchActivity<MapActivity> { } }
        binding.imageButtonSearch.setOnClickListener { viewPager.setCurrentItem(1, true) }
        binding.imageButtonAddApartment.setOnClickListener { viewPager.setCurrentItem(2, true) }
        binding.imageDistrict1.setOnClickListener { onDistrict1() }
        binding.imageDistrict2.setOnClickListener { onDistrict2() }
        binding.imageDistrict3.setOnClickListener { onDistrict3() }
        binding.imageDistrict4.setOnClickListener { onDistrict4() }
        binding.imageDistrict5.setOnClickListener { onDistrict5() }
        binding.imageDistrict6.setOnClickListener { onDistrict6() }

        binding.recyclerViewApartments.adapter = recyclerAdapter
        binding.recyclerViewApartments.layoutManager = LinearLayoutManager(requireContext())

        recyclerAdapter.submitList(createApartments())

        updateUI()
    }

    private fun createApartments(): List<Apartment> {
        val image1 = Image(1, "http://dummyimage.com/103x196.jpg/ff4444/ffffff")
        val image2 = Image(2, "http://dummyimage.com/204x236.bmp/ff4444/ffffff")
        val image3 = Image(3, "http://dummyimage.com/179x106.bmp/5fa2dd/ffffff")
        val image4 = Image(4, "http://dummyimage.com/238x149.jpg/dddddd/000000")
        val image5 = Image(5, "http://dummyimage.com/219x188.jpg/ff4444/ffffff")
        val images = listOf<Image>(image1, image2, image3, image4, image5)

        val user1 = User(1, "John", "https://robohash.org/etfugabeatae.jpg?size=50x50&set=set1")
        val user2 =
            User(2, "Christos", "https://robohash.org/excepturiquieligendi.jpg?size=50x50&set=set1")
        val user3 =
            User(3, "Gerhard", "https://robohash.org/nostrumquisquamautem.bmp?size=50x50&set=set1")
        val user4 =
            User(4, "Thaine", "https://robohash.org/culpaitaquevoluptatem.bmp?size=50x50&set=set1")
        val user5 =
            User(5, "Derek", "https://robohash.org/ipsammodilaudantium.bmp?size=50x50&set=set1")

        val a1 = Apartment(1, "tittle1", "address1", "district1", 1.0, 1.1, 1.1, "mo ta 1", "owner1", "0345653577", "1/1/2020", 3000000, 5000, 15000, 20, wifi = true, time = false, key = true, parking = false, air = true, heater = false, images = images, user = user1)
        val a2 = Apartment(2, "tittle2", "address2", "district2", 2.0, 100.1, 1.1, "mo ta 2", "owner2", "0345653577", "1/1/2020", 3000000, 5000, 15000, 20, wifi = false, time = true, key = true, parking = true, air = false, heater = true, images = images, user = user2)
        val a3 = Apartment(3, "tittle3", "address3", "district3", 3.0, 20.1, 1.1, "mo ta 3", "owner3", "0345653577", "1/1/2020", 3000000, 5000, 15000, 20, wifi = true, time = true, key = true, parking = false, air = true, heater = false, images = images, user = user3)
        val a4 = Apartment(4, "tittle4", "address4", "district4", 4.0, 41.1, 1.1, "mo ta 4", "owner4", "0345653577", "1/1/2020", 3000000, 5000, 15000, 20, wifi = true, time = false, key = true, parking = false, air = true, heater = true, images = images, user = user4)
        val a5 = Apartment(5, "tittle5", "address5", "district5", 5.0, 71.1, 1.1, "mo ta 5", "owner5", "0345653577", "1/1/2020", 3000000, 5000, 15000, 20, wifi = false, time = true, key = true, parking = true, air = false, heater = false, images = images, user = user5)

        return listOf(a1,a2,a3,a4,a5)
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

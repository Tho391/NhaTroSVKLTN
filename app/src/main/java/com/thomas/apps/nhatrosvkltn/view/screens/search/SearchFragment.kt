package com.thomas.apps.nhatrosvkltn.view.screens.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.thomas.apps.nhatrosvkltn.databinding.FragmentSearchBinding
import com.thomas.apps.nhatrosvkltn.model.Apartment
import com.thomas.apps.nhatrosvkltn.model.Image
import com.thomas.apps.nhatrosvkltn.model.User
import com.thomas.apps.nhatrosvkltn.utils.TOAST
import com.thomas.apps.nhatrosvkltn.utils.hideSoftKeyboard
import com.thomas.apps.nhatrosvkltn.view.adapter.ApartmentAdapter
import com.thomas.apps.nhatrosvkltn.view.screens.MainViewModel
import com.thomas.apps.nhatrosvkltn.view.screens.filters.FiltersFragment


class SearchFragment : Fragment() {

    private fun createApartments(): List<Apartment> {
        val image1 = Image(1, "http://dummyimage.com/103x196.jpg/ff4444/ffffff")
        val image2 = Image(2, "http://dummyimage.com/204x236.bmp/ff4444/ffffff")
        val image3 = Image(3, "http://dummyimage.com/179x106.bmp/5fa2dd/ffffff")
        val image4 = Image(4, "http://dummyimage.com/238x149.jpg/dddddd/000000")
        val image5 = Image(5, "http://dummyimage.com/219x188.jpg/ff4444/ffffff")
        val image6 = Image(6, "http://dummyimage.com/185x173.jpg/5fa2dd/ffffff")
        val image7 = Image(7, "http://dummyimage.com/240x165.png/cc0000/ffffff")
        val image8 = Image(8, "http://dummyimage.com/152x104.png/5fa2dd/ffffff")
        val image9 = Image(9, "http://dummyimage.com/117x189.jpg/ff4444/ffffff")
        val image10 = Image(10, "http://dummyimage.com/169x119.bmp/dddddd/000000")
        val images1 = listOf<Image>(image1, image2, image3, image4, image5)
        val images2 = listOf<Image>(image2, image3, image4, image5, image6)
        val images3 = listOf<Image>(image3, image4, image5, image6, image7)
        val images4 = listOf<Image>(image4, image5, image6, image7, image8)
        val images5 = listOf<Image>(image5, image6, image7, image8, image9)

        val user1 = User(1, "John", "https://robohash.org/etfugabeatae.jpg?size=50x50&set=set1")
        val user2 =
            User(2, "Christos", "https://robohash.org/excepturiquieligendi.jpg?size=50x50&set=set1")
        val user3 =
            User(3, "Gerhard", "https://robohash.org/nostrumquisquamautem.bmp?size=50x50&set=set1")
        val user4 =
            User(4, "Thaine", "https://robohash.org/culpaitaquevoluptatem.bmp?size=50x50&set=set1")
        val user5 =
            User(5, "Derek", "https://robohash.org/ipsammodilaudantium.bmp?size=50x50&set=set1")

        val a1 = Apartment(
            1,
            "tittle1",
            "address1",
            "district1",
            1F,
            1.1,
            1.1,
            "mo ta 1",
            "owner1",
            "0345653577",
            "1/1/2020",
            3000000,
            5000,
            15000,
            20,
            wifi = true,
            time = false,
            key = true,
            parking = false,
            air = true,
            heater = false,
            images = images1,
            user = user1
        )
        val a2 = Apartment(
            2,
            "tittle2",
            "address2",
            "district2",
            2F,
            100.1,
            1.1,
            "mo ta 2",
            "owner2",
            "0345653577",
            "1/1/2020",
            3000000,
            5000,
            15000,
            20,
            wifi = false,
            time = true,
            key = true,
            parking = true,
            air = false,
            heater = true,
            images = images2,
            user = user2
        )
        val a3 = Apartment(
            3,
            "tittle3",
            "address3",
            "district3",
            3F,
            20.1,
            1.1,
            "mo ta 3",
            "owner3",
            "0345653577",
            "1/1/2020",
            3000000,
            5000,
            15000,
            20,
            wifi = true,
            time = true,
            key = true,
            parking = false,
            air = true,
            heater = false,
            images = images3,
            user = user3
        )
        val a4 = Apartment(
            4,
            "tittle4",
            "address4",
            "district4",
            4F,
            41.1,
            1.1,
            "mo ta 4",
            "owner4",
            "0345653577",
            "1/1/2020",
            3000000,
            5000,
            15000,
            20,
            wifi = true,
            time = false,
            key = true,
            parking = false,
            air = true,
            heater = true,
            images = images4,
            user = user4
        )
        val a5 = Apartment(
            5,
            "tittle5",
            "address5",
            "district5",
            5F,
            71.1,
            1.1,
            "mo ta 5",
            "owner5",
            "0345653577",
            "1/1/2020",
            3000000,
            5000,
            15000,
            20,
            wifi = false,
            time = true,
            key = true,
            parking = true,
            air = false,
            heater = false,
            images = images5,
            user = user5
        )

        return listOf(a1, a2, a3, a4, a5)
    }

    companion object {
        fun newInstance() = SearchFragment()
    }

    //private lateinit var viewModel: SharedViewModel
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentSearchBinding

    private val recyclerAdapter by lazy { ApartmentAdapter() }
    private val filtersFragment by lazy { FiltersFragment() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_search, container, false)
        binding = FragmentSearchBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        //viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel = activity?.let { ViewModelProvider(it).get(MainViewModel::class.java) }!!

        // TODO: Use the ViewModel

        init()

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        with(binding){
            recyclerView.adapter = recyclerAdapter
            recyclerView.layoutManager = object : LinearLayoutManager(requireContext()) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
            editTextSearch.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    event != null &&
                    event.action == KeyEvent.ACTION_DOWN &&
                    event.keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (event == null || !event.isShiftPressed) {
                        // the user is done typing.
                        TOAST("Searching...")
                        hideSoftKeyboard()
                        true // consume.
                    }
                }
                false // pass on to other listeners.
            }
            editTextSearch.setOnTouchListener { _, event ->
                val DRAWABLE_LEFT = 0
                val DRAWABLE_TOP = 1
                val DRAWABLE_RIGHT = 2
                val DRAWABLE_BOTTOM = 3

                if (event!!.rawX >=
                    (editTextSearch.right - editTextSearch.compoundDrawables[DRAWABLE_RIGHT].bounds.width())
                ) {
                    hideSoftKeyboard()
                    TOAST("Filter")
                    //filtersFragment.show(childFragmentManager, "dialog filter")
                }
                false
            }
        }


        viewModel.apartments.observe(viewLifecycleOwner, Observer { apartments ->
            recyclerAdapter.submitList(apartments)
        })

    }

}

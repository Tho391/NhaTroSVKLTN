package com.thomas.apps.nhatrosvkltn.view.screens.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.thomas.apps.nhatrosvkltn.databinding.FragmentSearchBinding
import com.thomas.apps.nhatrosvkltn.model.FilterModel
import com.thomas.apps.nhatrosvkltn.utils.TOAST
import com.thomas.apps.nhatrosvkltn.utils.hideSoftKeyboard
import com.thomas.apps.nhatrosvkltn.view.adapter.ApartmentAdapter
import com.thomas.apps.nhatrosvkltn.view.screens.MainViewModel
import com.thomas.apps.nhatrosvkltn.view.screens.filters.FiltersFragment
import com.thomas.apps.nhatrosvkltn.view.screens.home.HomeFragment
import kotlinx.coroutines.*


class SearchFragment : Fragment(), FiltersFragment.OnDismissListener,
    HomeFragment.OnFilterListener {

    //private lateinit var viewModel: SharedViewModel
    private lateinit var mainViewModel: MainViewModel
    private var viewModel: SearchViewModel? = null
    private lateinit var binding: FragmentSearchBinding
    private var filterModel = FilterModel()
    private val recyclerAdapter by lazy { ApartmentAdapter() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (viewModel == null)
            viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_search, container, false)
        binding = FragmentSearchBinding.inflate(layoutInflater)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        init()
        return binding.root
    }

    private fun init() {
        val filtersFragment = FiltersFragment(
            requireContext(),
            android.R.style.Theme_Material_Light_DialogWhenLarge_NoActionBar
        )
        filtersFragment.onDismissListener = this

        with(binding) {
            recyclerView.adapter = recyclerAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    TOAST("Searching...")
                    hideSoftKeyboard()
                    // search here

                    //recyclerAdapter.filter.filter(editTextSearch.text.toString())
                    //todo call api search
                    if (query.isNullOrEmpty())
                        viewModel!!.setApartments(mainViewModel.apartments.value)
                    else
                        viewModel!!.search(query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })

            imageButtonFilter.setOnClickListener {
                hideSoftKeyboard()
                TOAST("Filter")
                filtersFragment.show()
                imageButtonFilter.isEnabled = false
            }

            swipeRefreshLayout.setOnRefreshListener {
                viewModel?.search(binding.searchView.query.toString())
//                if (filterModel.isEmpty()) {
//                    viewModel?.search(binding.searchView.query.toString())
//                } else {
//                    viewModel?.filter(filterModel)
//                }
            }
        }

        viewModel!!.apartments.observe(viewLifecycleOwner, Observer { apartments ->
            recyclerAdapter.submitList(apartments)
        })
        viewModel!!.isLoading.observe(viewLifecycleOwner, Observer {
            binding.swipeRefreshLayout.isRefreshing = it
        })
    }

    override fun onApplyButtonClick(filter: FilterModel) {
        super.onApplyButtonClick(filter)
        //todo áp lọc
        binding.imageButtonFilter.isEnabled = true

        filterModel = filter

        viewModel!!.filter(filter)
        binding.searchView.setQuery(filter.address, false)
    }

    override fun onResetFilterButtonClick() {
        super.onResetFilterButtonClick()
        binding.imageButtonFilter.isEnabled = true

        filterModel = FilterModel()
        viewModel!!.search(binding.searchView.query.toString())
    }

    override fun onCancelButtonClick() {
        super.onCancelButtonClick()
        binding.imageButtonFilter.isEnabled = true
    }

    override fun onFilter(filterModel: FilterModel) {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            delay(500)
            viewModel!!.filter(filterModel)

        }
    }
}

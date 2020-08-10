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
    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding
    private var filterModel = FilterModel()
    private val recyclerAdapter by lazy { ApartmentAdapter() }

    override fun onAttach(context: Context) {
        super.onAttach(context)

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
        with(binding) {
            recyclerView.adapter = recyclerAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    TOAST("Searching...")
                    hideSoftKeyboard()
                    // search here

                    if (query.isNullOrEmpty())
                        viewModel.setApartments(mainViewModel.apartments.value)
                    else
                        viewModel.search(query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })

            imageButtonFilter.setOnClickListener {
                hideSoftKeyboard()
                //TOAST("Filter")
                val filtersFragment = FiltersFragment(
                    requireContext(),
                    android.R.style.Theme_Material_Light_DialogWhenLarge_NoActionBar
                )
                filtersFragment.onDismissListener = this@SearchFragment
                filtersFragment.show()
            }

            swipeRefreshLayout.setOnRefreshListener {
                viewModel.search(binding.searchView.query.toString())
            }
        }

        viewModel.apartments.observe(viewLifecycleOwner, Observer { apartments ->
            recyclerAdapter.submitList(apartments)
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.swipeRefreshLayout.isRefreshing = it
        })
    }

    override fun onApplyButtonClick(filter: FilterModel) {
        super.onApplyButtonClick(filter)

        filterModel = filter

        viewModel.filter(filter)
        binding.searchView.setQuery(filter.address, false)
    }

    override fun onResetFilterButtonClick() {
        super.onResetFilterButtonClick()

        filterModel = FilterModel()
        viewModel.search(binding.searchView.query.toString())
    }

    override fun onFilter(filterModel: FilterModel) {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            delay(500)
            viewModel.filter(filterModel)

        }
    }
}

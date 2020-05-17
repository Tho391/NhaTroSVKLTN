package com.thomas.apps.nhatrosvkltn.view.ui.filters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.FragmentFiltersBinding


class FiltersFragment: Fragment() {

    companion object {
        fun newInstance() =
            FiltersFragment()
    }

    private lateinit var binding: FragmentFiltersBinding
    private lateinit var viewModel: FiltersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFiltersBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(FiltersViewModel::class.java)
        viewModel = ViewModelProvider(this).get(FiltersViewModel::class.java)
        // TODO: Use the ViewModel
        init()
    }

    private fun init() {
        TODO("Not yet implemented")
    }

}

package com.thomas.apps.nhatrosvkltn.view.screens.listimage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thomas.apps.nhatrosvkltn.databinding.ActivityListImagesBinding
import com.thomas.apps.nhatrosvkltn.model.Image
import com.thomas.apps.nhatrosvkltn.view.adapter.ImageAdapter

class ListImagesActivity : AppCompatActivity() {

    private lateinit var viewModel: ListImageViewModel
    private lateinit var binding: ActivityListImagesBinding
    private val imageAdapter by lazy { ImageAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ListImageViewModel::class.java)

        init()
    }

    private fun init() {
        setSupportActionBar(binding.toolBar.toolBar)

        binding.recyclerView.adapter = imageAdapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val apartmentId = intent.getIntExtra("apartmentId", 0)
        if (apartmentId != 0) {
            //viewModel.getImages(apartmentId)
            binding.swipeRefreshLayout.post { viewModel.getImages(apartmentId) }
        }

        viewModel.images.observe(this, Observer { imageAdapter.submitList(it) })

        binding.swipeRefreshLayout.setOnRefreshListener {
            if (apartmentId != 0)
                viewModel.getImages(apartmentId)
        }
        viewModel.isLoading.observe(this, Observer {
            binding.swipeRefreshLayout.isRefreshing = it
        })

    }

    private fun updateUI(listImage: List<Image>) {
        imageAdapter.submitList(listImage)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

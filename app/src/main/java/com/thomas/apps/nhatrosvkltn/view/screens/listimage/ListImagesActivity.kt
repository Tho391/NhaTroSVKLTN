package com.thomas.apps.nhatrosvkltn.view.screens.listimage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.thomas.apps.nhatrosvkltn.databinding.ActivityListImagesBinding

class ListImagesActivity : AppCompatActivity() {

    private lateinit var viewModel: ListImageViewModel
    private lateinit var binding: ActivityListImagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ListImageViewModel::class.java)

        init()
    }

    private fun init() {
        setSupportActionBar(binding.toolBar.toolBar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

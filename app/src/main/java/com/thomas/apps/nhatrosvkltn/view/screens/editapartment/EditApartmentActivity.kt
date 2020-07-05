package com.thomas.apps.nhatrosvkltn.view.screens.editapartment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.ActivityEditApartmentBinding

class EditApartmentActivity : AppCompatActivity() {

    private lateinit var viewModel: EditApartmentViewModel
    private lateinit var binding: ActivityEditApartmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditApartmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(EditApartmentViewModel::class.java)

        init()
    }

    private fun init() {
        setSupportActionBar(binding.toolBar.toolBar)

        val districts: List<String> = resources.getStringArray(R.array.districts).toList()
        binding.cardViewInfo.spinnerDistrict.setItems(districts)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

package com.thomas.apps.nhatrosvkltn.view.ui.manageapartments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.ActivityManageApartmentsBinding

class ManageApartmentsActivity : AppCompatActivity() {

    private lateinit var viewModel: ManageApartmentsViewModel
    private lateinit var binding: ActivityManageApartmentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageApartmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ManageApartmentsViewModel::class.java)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

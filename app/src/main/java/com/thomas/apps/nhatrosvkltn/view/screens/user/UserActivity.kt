package com.thomas.apps.nhatrosvkltn.view.screens.user

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.ActivityUserBinding
import com.thomas.apps.nhatrosvkltn.model.User
import com.thomas.apps.nhatrosvkltn.model.servermodel.Register
import com.thomas.apps.nhatrosvkltn.utils.TOAST
import com.thomas.apps.nhatrosvkltn.utils.getUser
import java.text.SimpleDateFormat
import java.util.*

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var districts: List<String>
    private var currentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        currentUser = getUser(this)
        if (currentUser == null) {
            TOAST(resources.getString(R.string.dang_nhap_lai))
            finish()
        }

        init()
    }

    private fun init() {
        setSupportActionBar(binding.toolBar.toolBar)
        districts = resources.getStringArray(R.array.districts).toList().drop(1)
        binding.spinnerDistrict.setItems(districts)

        binding.buttonPickDate.setOnClickListener {
            showDatePicker()
        }
        binding.buttonConfirm.setOnClickListener {
            val register = getData()

            viewModel.editUser(register)
        }

        with(binding) {
            editTextLastName.setText(currentUser?.lastName, TextView.BufferType.EDITABLE)
            editTextName.setText(currentUser?.firstName, TextView.BufferType.EDITABLE)
            textViewDate.text = currentUser?.dateOfBirth
            editTextAddress.setText(currentUser?.address, TextView.BufferType.EDITABLE)
            spinnerDistrict.selectedIndex = districts.indexOf(currentUser?.district)
            editTextPhone.setText(currentUser?.phoneNumber, TextView.BufferType.EDITABLE)

        }
    }

    private fun getData(): Register {
        return Register(
            binding.editTextName.editableText.toString(),
            binding.editTextLastName.editableText.toString(),
            binding.textViewDate.text.toString(),
            binding.editTextAddress.editableText.toString(),
            binding.spinnerDistrict.selectedIndex,
            1,
            binding.editTextPhone.editableText.toString()
        )
    }

    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker().build()
        datePicker.show(supportFragmentManager, "datePicker")
        datePicker.addOnPositiveButtonClickListener {
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            binding.textViewDate.text = sdf.format(Date(it))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
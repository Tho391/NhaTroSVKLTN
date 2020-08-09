package com.thomas.apps.nhatrosvkltn.view.screens.user

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.ActivityUserBinding
import com.thomas.apps.nhatrosvkltn.model.User
import com.thomas.apps.nhatrosvkltn.model.servermodel.Register
import com.thomas.apps.nhatrosvkltn.utils.TOAST
import com.thomas.apps.nhatrosvkltn.utils.getUser
import com.thomas.apps.nhatrosvkltn.utils.saveUser
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
            currentUser?.let { user ->
                user.id?.let {
                    viewModel.editUser(user.getToken(), it, register)

                }
            }
        }

        with(binding) {
            currentUser?.let { user ->
                editTextLastName.setText(user.lastName, TextView.BufferType.EDITABLE)
                editTextName.setText(user.firstName, TextView.BufferType.EDITABLE)
//                textViewDate.text = user.dateOfBirth

                textViewDate.text = user.dateOfBirth
                editTextAddress.setText(user.address, TextView.BufferType.EDITABLE)

                user.districtId?.let {
                    if (it > 0)
                        spinnerDistrict.selectedIndex = it - 1
                }
                editTextPhone.setText(user.phoneNumber, TextView.BufferType.EDITABLE)
            }
        }

        viewModel.editSuccess.observe(this, Observer {
            TOAST("Sửa thông tin thành công")

            currentUser?.let { user ->
                user.firstName = binding.editTextName.editableText.toString()
                user.lastName = binding.editTextLastName.editableText.toString()
                user.dateOfBirth = binding.textViewDate.text.toString()
                user.address = binding.editTextAddress.editableText.toString()
                user.districtId = binding.spinnerDistrict.selectedIndex + 1
                user.phoneNumber = binding.editTextPhone.editableText.toString()

                saveUser(this, user)
                finish()
            }


        })
        viewModel.isLoading.observe(this, Observer {

        })
    }

    private fun getData(): Register {
        return Register(
            binding.editTextName.editableText.toString(),
            binding.editTextLastName.editableText.toString(),
            binding.textViewDate.text.toString(),
            binding.editTextAddress.editableText.toString(),
            binding.spinnerDistrict.selectedIndex + 1,
            1,
            binding.editTextPhone.editableText.toString()
        )
    }

    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker().build()
        datePicker.show(supportFragmentManager, "datePicker")
        datePicker.addOnPositiveButtonClickListener {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            binding.textViewDate.text = sdf.format(Date(it))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
package com.thomas.apps.nhatrosvkltn.view.screens.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.android.material.datepicker.MaterialDatePicker
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.ActivityRegisterBinding
import com.thomas.apps.nhatrosvkltn.model.servermodel.Register
import com.thomas.apps.nhatrosvkltn.utils.TOAST
import com.thomas.apps.nhatrosvkltn.utils.getFileName
import com.thomas.apps.nhatrosvkltn.utils.stream2file
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private val CODE_PICK_IMAGE: Int = 1000
    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var districts: List<String>
    private var avatarFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        init()


    }

    private fun init() {
        setSupportActionBar(binding.toolBar.toolBar)
        districts = resources.getStringArray(R.array.districts).toList().drop(1)
        binding.spinnerDistrict.setItems(districts)
        binding.buttonRegister.setOnClickListener {
            //dang ky thanh cong, quay ve man hinh dang nhap
            val register = getInfo()
            if (register != null) {
                //viewModel.register(register, avatarFile)

                //change to up image to imgbb
                viewModel.register2(register, avatarFile, resources.getString(R.string.imgbb_key))
            }
        }
        binding.imageViewAvatar.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"), CODE_PICK_IMAGE
            )
        }
        binding.buttonPickDate.setOnClickListener {
            showDatePicker()
        }
        viewModel.isPosting.observe(this, Observer {
            binding.buttonRegister.isEnabled = !it
        })
        viewModel.message.observe(this, Observer { TOAST(it) })
        viewModel.finish.observe(this, Observer { if (it) finish() })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CODE_PICK_IMAGE -> {
                    if (data != null) {
                        if (data.data != null) {
                            try {

                                val inputStream = contentResolver.openInputStream(data.data!!)
                                val fileName = getFileName(this, data.data!!)
                                if (fileName != null) {
                                    val prefix = fileName.split(".").first()
                                    val suffix = "." + fileName.split(".")[1]

                                    val file = stream2file(inputStream, prefix, suffix)
                                    if (file != null) {
                                        avatarFile = file
                                        binding.imageViewAvatar.load(data.data)
                                    }
                                }

                            } catch (e: Exception) {
                                Log.e(TAG, e.message)
                                TOAST("Lỗi, thử lại sau")
                            }
                        }


                    }
                }
            }
        }
    }

    private fun getInfo(): Register? {
        val district = binding.spinnerDistrict.selectedIndex + 1
        return with(binding) {
            Register(
                editTextName.text.toString(),
                editTextLastName.text.toString(),
                if (textViewDate.text.toString().isNotEmpty()
                ) textViewDate.text.toString() else "",
                editTextAddress.text.toString(),
                district,
                1,
                editTextPhone.text.toString(),
                editTextEmail.text.toString(),
                editTextPass.text.toString()
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker().build()
        datePicker.show(supportFragmentManager, "datePicker")
        datePicker.addOnPositiveButtonClickListener {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            binding.textViewDate.text = sdf.format(Date(it))
        }
    }
}

private const val TAG = "Register Activity Tag"
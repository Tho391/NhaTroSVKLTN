package com.thomas.apps.nhatrosvkltn.view.screens.changepass

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.thomas.apps.nhatrosvkltn.databinding.ActivityChangePassBinding
import com.thomas.apps.nhatrosvkltn.utils.TOAST
import com.thomas.apps.nhatrosvkltn.utils.getUser

class ChangePassActivity : AppCompatActivity() {

    private lateinit var viewModel: ChangePassViewModel
    private lateinit var binding: ActivityChangePassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ChangePassViewModel::class.java)

        init()


    }

    private fun init() {

        setSupportActionBar(binding.toolBar.toolBar)

        with(binding) {
            progressBar.hide()


            buttonChangePass.setOnClickListener {
//            launchActivity<LoginActivity> { }
//            finish()
                val currentUser = getUser(this@ChangePassActivity)
                if (currentUser != null && currentUser.hasToken() && !currentUser.email.isNullOrEmpty())
                    if (validatePass()) {

                        val oldPass = editTextPass.editableText.toString()
                        val newPass = editTextNewPass.editableText.toString()

                        viewModel.changePass(
                            currentUser.getToken(),
                            currentUser.id!!,
                            currentUser.email!!,
                            oldPass,
                            newPass
                        )
                    }

            }
            viewModel.isLoading.observe(
                this@ChangePassActivity,
                Observer { if (it) progressBar.show() else progressBar.hide() })

            viewModel.message.observe(this@ChangePassActivity, Observer {
                TOAST(it)
                finish()
            })
        }

    }

    private fun validatePass(): Boolean {
        val newPass = binding.editTextNewPass.editableText.toString()
        val confirm = binding.editTextNewPassConfirm.editableText.toString()
        return if (newPass == confirm)
            true
        else {
            binding.editTextNewPassConfirm.error = "Mật khẩu không khớp"
            false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

package com.thomas.apps.nhatrosvkltn.view.screens.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.thomas.apps.nhatrosvkltn.databinding.ActivityLoginBinding
import com.thomas.apps.nhatrosvkltn.utils.launchActivity
import com.thomas.apps.nhatrosvkltn.view.screens.MainActivity
import com.thomas.apps.nhatrosvkltn.view.screens.forgetpass.ForgetPassActivity
import com.thomas.apps.nhatrosvkltn.view.screens.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        init()



    }

    private fun init() {

        setSupportActionBar(binding.toolBar.toolBar)

        with(binding) {
            buttonLogin.setOnClickListener {
                launchActivity<MainActivity> { }
                finish()
            }
            buttonLoginGoogle.setOnClickListener {
                launchActivity<MainActivity> { }
                finish()
            }
            textForgetPass.setOnClickListener {
                launchActivity<ForgetPassActivity> { }
            }
            textRegister.setOnClickListener {
                launchActivity<RegisterActivity> { }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

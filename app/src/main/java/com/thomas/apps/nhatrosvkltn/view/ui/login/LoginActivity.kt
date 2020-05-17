package com.thomas.apps.nhatrosvkltn.view.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.ActivityLoginBinding
import com.thomas.apps.nhatrosvkltn.databinding.ActivityMainBinding
import com.thomas.apps.nhatrosvkltn.utils.launchActivity
import com.thomas.apps.nhatrosvkltn.view.ui.MainActivity
import com.thomas.apps.nhatrosvkltn.view.ui.forgetpass.ForgetPassActivity
import com.thomas.apps.nhatrosvkltn.view.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)


        with(binding){
            buttonLogin.setOnClickListener {
                launchActivity<MainActivity> {  }
                finish()
            }
            buttonLoginGoogle.setOnClickListener{
                launchActivity<MainActivity> {  }
                finish()
            }
            textForgetPass.setOnClickListener {
                launchActivity<ForgetPassActivity> {  }
            }
            textRegister.setOnClickListener {
                launchActivity<RegisterActivity> {  }
            }
        }

    }
}

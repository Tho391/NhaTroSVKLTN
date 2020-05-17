package com.thomas.apps.nhatrosvkltn.view.ui.newpass

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.thomas.apps.nhatrosvkltn.databinding.ActivityNewPassBinding
import com.thomas.apps.nhatrosvkltn.utils.launchActivity
import com.thomas.apps.nhatrosvkltn.view.ui.login.LoginActivity

class NewPassActivity : AppCompatActivity() {

    private lateinit var viewModel: NewPassViewModel
    private lateinit var binding: ActivityNewPassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(NewPassViewModel::class.java)

        init()


    }

    private fun init() {
        setSupportActionBar(binding.toolBar.toolBar)
        binding.buttonChangePass.setOnClickListener {
            launchActivity<LoginActivity> { }
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

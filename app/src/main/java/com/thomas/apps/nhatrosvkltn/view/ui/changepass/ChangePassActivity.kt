package com.thomas.apps.nhatrosvkltn.view.ui.changepass

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.thomas.apps.nhatrosvkltn.databinding.ActivityChangePassBinding
import com.thomas.apps.nhatrosvkltn.utils.launchActivity
import com.thomas.apps.nhatrosvkltn.view.ui.login.LoginActivity

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

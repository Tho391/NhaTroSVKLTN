package com.thomas.apps.nhatrosvkltn.view.ui.forgetpass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.ActivityForgetPassBinding
import com.thomas.apps.nhatrosvkltn.utils.launchActivity
import com.thomas.apps.nhatrosvkltn.view.ui.changepass.ChangePassActivity
import com.thomas.apps.nhatrosvkltn.view.ui.newpass.NewPassActivity
import kotlinx.android.synthetic.main.activity_change_pass.*

class ForgetPassActivity : AppCompatActivity() {

    private lateinit var viewModel: ForgetPassViewModel
    private lateinit var binding: ActivityForgetPassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ForgetPassViewModel::class.java)

        init()

    }

    private fun init() {
        setSupportActionBar(binding.toolBar.toolBar)

        binding.buttonConfirm.setOnClickListener {
            launchActivity<NewPassActivity> {  }
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

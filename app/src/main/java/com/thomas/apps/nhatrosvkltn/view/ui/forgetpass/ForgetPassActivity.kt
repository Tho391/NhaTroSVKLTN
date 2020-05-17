package com.thomas.apps.nhatrosvkltn.view.ui.forgetpass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.ActivityForgetPassBinding
import com.thomas.apps.nhatrosvkltn.utils.launchActivity
import com.thomas.apps.nhatrosvkltn.view.ui.changepass.ChangePassActivity
import com.thomas.apps.nhatrosvkltn.view.ui.newpass.NewPassActivity

class ForgetPassActivity : AppCompatActivity() {

    private lateinit var viewModel: ForgetPassViewModel
    private lateinit var binding: ActivityForgetPassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ForgetPassViewModel::class.java)


        binding.buttonConfirm.setOnClickListener {
            launchActivity<NewPassActivity> {  }
            finish()
        }
    }
}

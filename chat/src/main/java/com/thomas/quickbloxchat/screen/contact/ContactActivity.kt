package com.thomas.quickbloxchat.screen.contact

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.thomas.quickbloxchat.databinding.ActivityContactBinding

class ContactActivity : AppCompatActivity() {
    private lateinit var viewModel: ContactViewModel
    private lateinit var binding: ActivityContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                ContactViewModel::class.java
            )

    }


}
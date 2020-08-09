package com.thomas.quickbloxchat.screen.call

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.thomas.quickbloxchat.databinding.ActivityCallBinding
import java.util.*

class CallActivity : AppCompatActivity() {
    private var occupantIds: ArrayList<Int>? = null
    private lateinit var viewModel: CallViewModel
    private lateinit var binding: ActivityCallBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                CallViewModel::class.java
            )

        init()
    }

    private fun init() {
        with(binding) {
            setSupportActionBar(toolbar)
            if (supportActionBar != null) {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar!!.setDisplayShowHomeEnabled(true)
//                supportActionBar!!.title = resources.getString(R.string.chat)
            }

//            fabEndCall.setOnClickListener {
//                endCall()
//            }
        }

        //get occupantIds
        occupantIds = intent.getIntegerArrayListExtra("occupantIds")
        if (!occupantIds.isNullOrEmpty()) {

        }
    }

    private fun endCall() {
    }
}
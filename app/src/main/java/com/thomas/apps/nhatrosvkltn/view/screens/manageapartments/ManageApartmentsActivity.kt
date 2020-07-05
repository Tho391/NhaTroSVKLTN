package com.thomas.apps.nhatrosvkltn.view.screens.manageapartments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.thomas.apps.nhatrosvkltn.databinding.ActivityManageApartmentsBinding
import com.thomas.apps.nhatrosvkltn.model.User
import com.thomas.apps.nhatrosvkltn.utils.getUser
import com.thomas.apps.nhatrosvkltn.view.adapter.EditApartmentAdapter

class ManageApartmentsActivity : AppCompatActivity() {

    private lateinit var viewModel: ManageApartmentsViewModel
    private lateinit var binding: ActivityManageApartmentsBinding
    private var currentUser: User? = null
    private var adapter = EditApartmentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageApartmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ManageApartmentsViewModel::class.java)

        init()
    }

    private fun init() {
        setSupportActionBar(binding.toolBar.toolBar)

        currentUser = getUser(this)


        with(binding) {
            recyclerView.apply {
                adapter = this@ManageApartmentsActivity.adapter
                layoutManager = LinearLayoutManager(this@ManageApartmentsActivity)
            }

            swipeRefreshLayout.setOnRefreshListener {
                if (currentUser != null)
                    currentUser!!.token?.let { token ->
                        currentUser!!.id?.let { id ->
                            viewModel.getUserApartments(token, id)
                        }
                    }
            }

            viewModel.isLoading.observe(
                this@ManageApartmentsActivity,
                Observer { swipeRefreshLayout.isRefreshing = it })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

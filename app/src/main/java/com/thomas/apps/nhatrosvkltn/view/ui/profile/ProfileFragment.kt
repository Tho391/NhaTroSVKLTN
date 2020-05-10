package com.thomas.apps.nhatrosvkltn.view.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.FragmentProfileBinding
import com.thomas.apps.nhatrosvkltn.utils.TOAST
import com.thomas.apps.nhatrosvkltn.utils.launchActivity
import com.thomas.apps.nhatrosvkltn.view.ui.addapartment.AddApartmentActivity
import com.thomas.apps.nhatrosvkltn.view.ui.changepass.ChangePassActivity
import com.thomas.apps.nhatrosvkltn.view.ui.login.LoginActivity
import com.thomas.apps.nhatrosvkltn.view.ui.manageapartments.ManageApartmentsActivity


class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() =
            ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_profile, container, false)
        binding = FragmentProfileBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel

        binding.textViewLogin.setOnClickListener { requireContext().launchActivity<LoginActivity> {  } }
        binding.textViewAdd.setOnClickListener { requireContext().launchActivity<AddApartmentActivity> {  } }
        binding.textViewManage.setOnClickListener { requireContext().launchActivity<ManageApartmentsActivity> {  } }
        binding.textViewChangePass.setOnClickListener { requireContext().launchActivity<ChangePassActivity> {  } }
        binding.textViewLogout.setOnClickListener { TOAST("Log out") }
    }

}

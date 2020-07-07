package com.thomas.apps.nhatrosvkltn.view.screens.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.FragmentProfileBinding
import com.thomas.apps.nhatrosvkltn.utils.Constant.Companion.SHARE_PREFERENCES_KEY
import com.thomas.apps.nhatrosvkltn.utils.TOAST
import com.thomas.apps.nhatrosvkltn.utils.clear
import com.thomas.apps.nhatrosvkltn.utils.getUser
import com.thomas.apps.nhatrosvkltn.utils.launchActivity
import com.thomas.apps.nhatrosvkltn.view.screens.addapartment.AddApartmentActivity
import com.thomas.apps.nhatrosvkltn.view.screens.changepass.ChangePassActivity
import com.thomas.apps.nhatrosvkltn.view.screens.login.LoginActivity
import com.thomas.apps.nhatrosvkltn.view.screens.manageapartments.ManageApartmentsActivity


class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding
    private var isLogin = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_profile, container, false)
        binding = FragmentProfileBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        init()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        if (!isLogin) {
            val user = getUser(requireContext())
            with(binding) {
                if (user != null) {
                    isLogin = true
                    imageViewAvatar.load(user.avatar)
                    textViewLogin.text = user.getName()
                    textViewLogin.isEnabled = false
                }
            }
        }
    }

    private fun init() {
        val user = getUser(requireContext())
        with(binding) {
            if (user != null) {
                isLogin = true
                imageViewAvatar.load(user.avatar)
                textViewLogin.text = user.getName()
                textViewLogin.isEnabled = false
            }

            textViewLogin.setOnClickListener { requireContext().launchActivity<LoginActivity> { } }
            textViewAdd.setOnClickListener {
                if (isLogin)
                    requireContext().launchActivity<AddApartmentActivity> { }
                else TOAST("Đăng nhập để thực hiện chức năng này")
            }
            textViewManage.setOnClickListener {
                if (isLogin)
                    requireContext().launchActivity<ManageApartmentsActivity> { }
                else TOAST("Đăng nhập để thực hiện chức năng này")
            }
            textViewChangePass.setOnClickListener {
                if (isLogin)
                    requireContext().launchActivity<ChangePassActivity> { }
                else TOAST("Đăng nhập để thực hiện chức năng này")
            }
            textViewLogout.setOnClickListener {
                if (isLogin)
                    logOut()
            }
        }


    }

    private fun logOut() {
        isLogin = false
        removeUser()
        binding.imageViewAvatar.setImageResource(R.drawable.person)
        binding.textViewLogin.isEnabled = true
        binding.textViewLogin.text = resources.getString(R.string.dang_nhap)
    }

    private fun removeUser() {
        val sharedPreferences =
            requireContext().getSharedPreferences(SHARE_PREFERENCES_KEY, Context.MODE_PRIVATE)
        sharedPreferences.clear()
    }

}

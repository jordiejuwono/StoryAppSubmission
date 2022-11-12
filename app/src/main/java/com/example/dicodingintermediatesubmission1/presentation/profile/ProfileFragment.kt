package com.example.dicodingintermediatesubmission1.presentation.profile

import android.content.Intent
import com.example.dicodingintermediatesubmission1.base.arch.BaseFragment
import com.example.dicodingintermediatesubmission1.databinding.FragmentProfileBinding
import com.example.dicodingintermediatesubmission1.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(FragmentProfileBinding::inflate) {
    override fun initView() {
        initData()
        setProfileName()
        logOutUser()
    }

    private fun initData() {
        getViewModel().getUserData()
    }

    private fun setProfileName() {
        getViewBinding().tvName.text = getViewModel().getUserData()?.loginResult?.name
    }

    private fun logOutUser() {
        getViewBinding().btnLogout.setOnClickListener {
            getViewModel().logOutUser()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

}
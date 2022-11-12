package com.example.dicodingintermediatesubmission1.presentation.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.dicodingintermediatesubmission1.R
import com.example.dicodingintermediatesubmission1.base.arch.BaseActivity
import com.example.dicodingintermediatesubmission1.base.model.Resource
import com.example.dicodingintermediatesubmission1.databinding.ActivityRegisterBinding
import com.example.dicodingintermediatesubmission1.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity :
    BaseActivity<ActivityRegisterBinding, RegisterViewModel>(ActivityRegisterBinding::inflate) {
    override fun initView() {
        supportActionBar?.hide()
        setupAnimation()
        initData()
        setOnClickListener()
    }

    private fun initData() {
        getViewBinding().btnRegister.setOnClickListener {
            if ((getViewBinding().etPassword.text?.length ?: 0) < 6) {
                getViewBinding().etPassword.error = getString(R.string.error_password)
                getViewBinding().etPassword.requestFocus()
            } else {
                getViewModel().registerUser(
                    name = getViewBinding().etNama.text.toString(),
                    email = getViewBinding().etEmail.text.toString(),
                    password = getViewBinding().etPassword.text.toString()
                )
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        getViewBinding().pbLoadingRegister.isVisible = isLoading
    }

    private fun setOnClickListener() {
        getViewBinding().tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun setupAnimation() {
        val imageLogo =
            ObjectAnimator.ofFloat(getViewBinding().ivDicodingLogo, View.ALPHA, 1f).setDuration(350)
        val textRegister =
            ObjectAnimator.ofFloat(getViewBinding().tvRegister, View.ALPHA, 1f).setDuration(350)
        val etNama =
            ObjectAnimator.ofFloat(getViewBinding().etNama, View.ALPHA, 1f).setDuration(350)
        val etEmail =
            ObjectAnimator.ofFloat(getViewBinding().etEmail, View.ALPHA, 1f).setDuration(350)
        val etPassword =
            ObjectAnimator.ofFloat(getViewBinding().etPassword, View.ALPHA, 1f).setDuration(350)
        val btnRegister =
            ObjectAnimator.ofFloat(getViewBinding().btnRegister, View.ALPHA, 1f).setDuration(350)
        val llLogin =
            ObjectAnimator.ofFloat(getViewBinding().llLogin, View.ALPHA, 1f).setDuration(350)

        val togetherOne = AnimatorSet().apply {
            playTogether(imageLogo, textRegister)
        }
        val togetherTwo = AnimatorSet().apply {
            playTogether(etNama,etEmail,etPassword)
        }
        val togetherThree = AnimatorSet().apply {
            playTogether(btnRegister, llLogin)
        }
        AnimatorSet().apply {
            playSequentially(togetherOne, togetherTwo, togetherThree)
            start()
        }
    }

    override fun observeData() {
        super.observeData()
        getViewModel().registerResult.observe(this) { result ->
            when (result) {
                is Resource.Loading -> {
                    getViewBinding().etPassword.error = null
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    Toast.makeText(this, result.data?.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(this, result.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
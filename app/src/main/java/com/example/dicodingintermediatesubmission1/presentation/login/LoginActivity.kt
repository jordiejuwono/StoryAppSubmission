package com.example.dicodingintermediatesubmission1.presentation.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.dicodingintermediatesubmission1.R
import com.example.dicodingintermediatesubmission1.base.arch.BaseActivity
import com.example.dicodingintermediatesubmission1.base.model.Resource
import com.example.dicodingintermediatesubmission1.databinding.ActivityLoginBinding
import com.example.dicodingintermediatesubmission1.presentation.main.MainActivity
import com.example.dicodingintermediatesubmission1.presentation.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(ActivityLoginBinding::inflate) {
    override fun initView() {
        supportActionBar?.hide()
        setupAnimation()
        intentToRegister()
        initData()
    }

    private fun setupAnimation() {
        val imageLogo = ObjectAnimator.ofFloat(getViewBinding().ivLogoDicodingCircle, View.ALPHA, 1f).setDuration(350)
        val textLogin = ObjectAnimator.ofFloat(getViewBinding().tvLoginTitle, View.ALPHA, 1f).setDuration(350)
        val etEmail = ObjectAnimator.ofFloat(getViewBinding().etEmail, View.ALPHA, 1f).setDuration(350)
        val etPassword = ObjectAnimator.ofFloat(getViewBinding().etPassword, View.ALPHA, 1f).setDuration(350)
        val btnLogin = ObjectAnimator.ofFloat(getViewBinding().btnLogin, View.ALPHA, 1f).setDuration(350)
        val llLogin = ObjectAnimator.ofFloat(getViewBinding().llLogin, View.ALPHA, 1f).setDuration(350)

        val together = AnimatorSet().apply {
            playTogether(imageLogo, textLogin)
        }
        val togetherTwo = AnimatorSet().apply {
            playTogether(etEmail, etPassword)
        }
        val togetherThree = AnimatorSet().apply {
            playTogether(btnLogin, llLogin)
        }
        AnimatorSet().apply {
            playSequentially(together, togetherTwo, togetherThree)
            start()
        }
    }

    private fun initData() {
        getViewBinding().btnLogin.setOnClickListener {
            if ((getViewBinding().etPassword.text?.length ?: 0) < 6) {
                getViewBinding().etPassword.error = getString(R.string.error_password)
                getViewBinding().etPassword.requestFocus()
            } else {
                getViewModel().loginUser(
                    email = getViewBinding().etEmail.text.toString(),
                    password = getViewBinding().etPassword.text.toString(),
                )
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        getViewBinding().pbLoadingLogin.isVisible = isLoading
    }

    private fun intentToHomePage() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun intentToRegister() {
        getViewBinding().tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    override fun observeData() {
        super.observeData()
        getViewModel().loginResult.observe(this) {
            when(it) {
                is Resource.Loading -> {
                    getViewBinding().etPassword.error = null
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    intentToHomePage()
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
        }
    }


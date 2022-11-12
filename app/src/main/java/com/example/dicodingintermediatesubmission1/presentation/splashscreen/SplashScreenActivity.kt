package com.example.dicodingintermediatesubmission1.presentation.splashscreen

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.example.dicodingintermediatesubmission1.base.arch.BaseActivity
import com.example.dicodingintermediatesubmission1.databinding.ActivitySplashScreenBinding
import com.example.dicodingintermediatesubmission1.presentation.login.LoginActivity
import com.example.dicodingintermediatesubmission1.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding, SplashScreenViewModel>(ActivitySplashScreenBinding::inflate) {
    override fun initView() {
        supportActionBar?.hide()
        initData()
        navigateToLoginOrHome()
    }

    private fun initData() {
        getViewModel().isUserLoggedIn()
    }

    private fun navigateToLoginOrHome() {
        lifecycleScope.launch(Dispatchers.IO) {
            delay(DELAY_TIME)
            if (getViewModel().isUserLoggedIn()) {
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } else {
                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            }
        }

    }

    companion object {
        const val DELAY_TIME = 2500L
    }
}
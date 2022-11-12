package com.example.dicodingintermediatesubmission1.base.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import java.lang.Exception
import javax.inject.Inject

abstract class BaseActivity<B: ViewBinding, VM: ViewModel>(
    val bindingFactory: (LayoutInflater) -> B
) : AppCompatActivity() {
    private lateinit var binding: B

    @Inject
    lateinit var viewModelInstance: VM

    fun getViewBinding(): B = binding
    fun getViewModel(): VM = viewModelInstance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        initView()
        observeData()
    }

    abstract fun initView()

    open fun observeData() {
        // Do nothing
    }

    open fun showError(isErrorEnabled: Boolean, exception: Exception) {
        if (isErrorEnabled) {
            Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
        }
    }

}
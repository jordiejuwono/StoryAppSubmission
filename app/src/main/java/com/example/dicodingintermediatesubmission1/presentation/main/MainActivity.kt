package com.example.dicodingintermediatesubmission1.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dicodingintermediatesubmission1.R
import com.example.dicodingintermediatesubmission1.databinding.ActivityMainBinding
import com.example.dicodingintermediatesubmission1.presentation.homepage.HomePageFragment
import com.example.dicodingintermediatesubmission1.presentation.mapfragment.MapFragment
import com.example.dicodingintermediatesubmission1.presentation.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val homePageFragment = HomePageFragment()
    private val profileFragment = ProfileFragment()
    private val mapFragment = MapFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fl_fragment, homePageFragment)
                .commit()
        }
        binding.bnvBottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fl_fragment, homePageFragment)
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.menu_map -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fl_fragment, mapFragment)
                        .addToBackStack(null)
                        .commit()
                    true
                }
                else -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fl_fragment, profileFragment)
                        .addToBackStack(null)
                        .commit()
                    true
                }
            }
        }
    }
}
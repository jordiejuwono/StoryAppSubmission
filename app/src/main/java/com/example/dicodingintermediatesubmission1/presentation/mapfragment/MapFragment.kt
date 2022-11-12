package com.example.dicodingintermediatesubmission1.presentation.mapfragment

import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import com.example.dicodingintermediatesubmission1.R
import com.example.dicodingintermediatesubmission1.base.arch.BaseFragment
import com.example.dicodingintermediatesubmission1.base.model.Resource
import com.example.dicodingintermediatesubmission1.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding, MapViewModel>(FragmentMapBinding::inflate), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val boundsBuilder = LatLngBounds.Builder()

    override fun initView() {
        setupMap()
        initData()
    }

    private fun setupMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setMapStyle() {
        try {
            val success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style))
            if (!success) {
                Log.e(this::class.java.simpleName, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(this::class.java.simpleName, "Can't find map style, Error: ", exception)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }

        setMapStyle()
    }

    private fun initData() {
        getViewModel().getStoriesWithLocation()
    }

    override fun observeData() {
        super.observeData()
        getViewModel().getStoriesWithLocationResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    // Do nothing
                }
                is Resource.Success -> {
                    it.data?.listStory?.forEach { story ->
                        val latLng = LatLng(story.lat ?: 0.0, story.lon ?: 0.0)
                        mMap.addMarker(MarkerOptions()
                            .position(latLng)
                            .title(story.name).snippet(story.description))
                        boundsBuilder.include(latLng)
                    }

                    // Show all LatLng inside Bounds
                    val bounds: LatLngBounds = boundsBuilder.build()
                    mMap.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(
                            bounds,
                            resources.displayMetrics.widthPixels,
                            resources.displayMetrics.heightPixels,
                            100
                        )
                    )
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Failed to get Locations", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
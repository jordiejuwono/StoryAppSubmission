package com.example.dicodingintermediatesubmission1.presentation.addstory

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import com.example.dicodingintermediatesubmission1.R
import com.example.dicodingintermediatesubmission1.base.arch.BaseActivity
import com.example.dicodingintermediatesubmission1.base.model.Resource
import com.example.dicodingintermediatesubmission1.databinding.ActivityAddStoryBinding
import com.example.dicodingintermediatesubmission1.utils.createCustomTempFile
import com.example.dicodingintermediatesubmission1.utils.reduceFileImage
import com.example.dicodingintermediatesubmission1.utils.uriToFile
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class AddStoryActivity :
    BaseActivity<ActivityAddStoryBinding, AddStoryViewModel>(ActivityAddStoryBinding::inflate) {

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    private var lat: Float? = null
    private var lon: Float? = null

    private var getFile: File? = null
    private lateinit var currentPhotoPath: String

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun initView() {
        supportActionBar?.title = getString(R.string.title_post_story)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fusedLocationClient =
            com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(this)
        getMyLastLocation()

        startGallery()
        startCamera()
        uploadImage()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    getMyLastLocation()
                }
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    getMyLastLocation()
                }
                else -> {
                    // No Access to Location
                }
            }

        }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getMyLastLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    lat = location.latitude.toFloat()
                    lon = location.longitude.toFloat()
                } else {
                    Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun askPermissionForCamera() {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    getString(R.string.text_no_camera_access),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startGallery() {
        getViewBinding().btnFromGallery.setOnClickListener {
            val intent = Intent()
            intent.action = ACTION_GET_CONTENT
            intent.type = "image/*"
            val chooser = Intent.createChooser(intent, getString(R.string.text_choose_picture))
            launcherIntentGallery.launch(chooser)
        }
    }

    private fun startCamera() {
        getViewBinding().btnFromCamera.setOnClickListener {
            if (allPermissionsGranted()) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                intent.resolveActivity(packageManager)
                createCustomTempFile(application).also { photoFile ->
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.dicodingintermediatesubmission1",
                        photoFile
                    )
                    currentPhotoPath = photoFile.absolutePath
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    launcherIntentCamera.launch(intent)
                }
            } else {
                askPermissionForCamera()
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)
            getFile = myFile
            getViewBinding().ivStoryImage.setImageURI(selectedImg)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile

            val photoResult = BitmapFactory.decodeFile(getFile?.path)
            getViewBinding().ivStoryImage.setImageBitmap(photoResult)
        }
    }

    private fun uploadImage() {
        getViewBinding().btnPostStory.setOnClickListener {
            if (getFile != null) {
                val file = reduceFileImage(getFile as File)
                getViewModel().uploadImage(
                    file,
                    getViewBinding().etDescription.text.toString(),
                    lat,
                    lon
                )
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        getViewBinding().pbLoadingPost.isVisible = isLoading
    }

    override fun observeData() {
        super.observeData()
        getViewModel().uploadImageResult.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    Toast.makeText(this, getString(R.string.text_story_success), Toast.LENGTH_SHORT)
                        .show()
                    // Set Result to OK to refresh HomePage data
                    setResult(RESULT_OK)
                    finish()
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
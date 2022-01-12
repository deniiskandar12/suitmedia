package com.mypro.suitmedia.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mypro.suitmedia.util.EXTRA_NAME
import com.mypro.suitmedia.R
import com.mypro.suitmedia.databinding.ActivityMainBinding
import com.mypro.suitmedia.util.EXTRA_USER_NAME
import com.mypro.suitmedia.util.toast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initView()
    }

    private fun initView() {
        setOverlappingStatusBar()
        with(binding) {
            imgPhoto.setOnClickListener {
                if (checkPermission())
                    openGallery()
            }

            btnCheck.setOnClickListener {
                val text = etPalindrome.text.toString()
                if (text.isNotEmpty()) {
                    val dialog =
                        MaterialAlertDialogBuilder(this@MainActivity)
                            .setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }

                    if (isPalindrome(text)) {
                        dialog.setMessage("Is palindrome")
                    } else {
                        dialog.setMessage("Not palindrome")
                    }
                    dialog.show()
                } else
                    toast("Palindrome cannot be empty")
            }

            btnNext.setOnClickListener {
                val name = etName.text.toString()
                if (name.isNotEmpty()) {
                    val intent = Intent(this@MainActivity, SecondActivity::class.java)
                    intent.putExtra(EXTRA_NAME, name)
                    startActivity(intent)
                } else
                    toast("Name cannot be empty")
            }
        }
    }

    private fun isPalindrome(text: String): Boolean {
        val trimmedText = text.replace(" ", "")
        val reversedText = trimmedText.reversed()
        return trimmedText == reversedText
    }

    private fun setOverlappingStatusBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = Color.TRANSPARENT
    }

    private fun setWindowFlag(bits: Int) {
        val win = window
        val winParams = win.attributes
        winParams.flags = winParams.flags and bits.inv()
        win.attributes = winParams
    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        galleryLauncher.launch(gallery)
    }

    private var galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                //Update Photo
                val uri = result.data?.data
                binding.imgPhoto.setImageURI(uri)
            }
        }


    private fun requestPermission() {
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            var idx = 0
            it.forEach {
                if (it.value == true)
                    idx++
            }
            if (idx == 2)
                openGallery()
        }


    private fun checkPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                ) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                ) == PackageManager.PERMISSION_GRANTED

            ) {
                return true
            } else {
                requestPermission()
                return false
            }
        } else {
            return true
        }
    }
}
package com.peteralexbizjak.c2surlauncher

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.peteralexbizjak.c2surlauncher.databinding.ActivityMainBinding
import com.peteralexbizjak.urlauncher.URLauncher

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val launcher = URLauncher(this)
    private var isPhoneCall = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // UI toggle between making a phone call and sending a text message
        binding.apply {
            activityPhoneTextCallInputBody.visibility = View.GONE
            textPhoneActionText = getString(R.string.phone)
            activityMainPhoneTextSelection.setOnCheckedChangeListener { _, checkedId ->
                if (checkedId == R.id.activityMainPhoneTextPhone) {
                    binding.activityPhoneTextCallInputBody.visibility = View.GONE
                    textPhoneActionText = getString(R.string.phone)
                    isPhoneCall = true
                } else {
                    binding.activityPhoneTextCallInputBody.visibility = View.VISIBLE
                    textPhoneActionText = getString(R.string.text)
                    isPhoneCall = false
                }
            }
        }

        // Launch URL
        binding.activityMainURLButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                try {
                    launcher.launchURL(binding.activityMainURLInput.text.toString())
                } catch (e: Exception) {
                    newSnackBar(it, "Failed to open URL")
                    Log.e(this.javaClass.simpleName, e.localizedMessage ?: "")
                }
            } else {
                newSnackBar(it, "Only available on Android 11+")
            }
        }

        // Send email
        binding.activityMainEmailButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val subject = binding.activityMainEmailInputSubject.text.toString()
                val receiver = binding.activityMainEmailInputReceiver.text.toString()
                val body = binding.activityMainEmailInputBody.text.toString()
                try {
                    launcher.sendEmail(subject, arrayOf(receiver), body)
                } catch (e: Exception) {
                    newSnackBar(it, "Failed to send email")
                    Log.e(this.javaClass.simpleName, e.localizedMessage ?: "")
                }
            } else {
                newSnackBar(it, "Only available on Android 11+")
            }
        }

        // Open phone call dialer app or SMS client
        binding.activityMainPhoneTextButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (isPhoneCall) {
                    val phoneNumber = binding.activityPhoneTextCallInputNumber.text.toString()
                    try {
                        launcher.launchPhoneDialer(phoneNumber)
                    } catch (e: Exception) {
                        newSnackBar(it, "Failed to open phone dialer app")
                        Log.e(this.javaClass.simpleName, e.localizedMessage ?: "")
                    }
                } else {
                    val phoneNumber = binding.activityPhoneTextCallInputNumber.text.toString()
                    val message = binding.activityPhoneTextCallInputBody.text.toString()
                    try {
                        launcher.sendSMSMessage(phoneNumber, message)
                    } catch (e: Exception) {
                        newSnackBar(it, "Failed to open SMS client")
                        Log.e(this.javaClass.simpleName, e.localizedMessage ?: "")
                    }
                }
            } else {
                newSnackBar(it, "Only available on Android 11+")
            }

        }
    }

    private fun newSnackBar(view: View, text: String) =
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
}
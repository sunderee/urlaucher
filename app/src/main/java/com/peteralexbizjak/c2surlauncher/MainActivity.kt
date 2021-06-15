package com.peteralexbizjak.c2surlauncher

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.peteralexbizjak.c2surlauncher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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
                } else {
                    binding.activityPhoneTextCallInputBody.visibility = View.VISIBLE
                    textPhoneActionText = getString(R.string.text)
                }
            }
        }
    }
}
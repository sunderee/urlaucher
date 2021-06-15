package com.peteralexbizjak.urlauncher

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.webkit.URLUtil
import androidx.annotation.RequiresApi
import java.util.regex.Pattern

class URLauncher(private val context: Context) {

    @RequiresApi(Build.VERSION_CODES.R)
    @Throws(ActivityNotFoundException::class, URLauncherException::class)
    fun launchURL(url: String) {
        if (url.isUrlValid()) {
            val urlLauncherIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
                addCategory(Intent.CATEGORY_BROWSABLE)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(urlLauncherIntent)
        } else {
            throw URLauncherException(
                URLauncherException.INVALID_URL,
                "Invalid URL $url"
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @Throws(ActivityNotFoundException::class, URLauncherException::class)
    fun sendEmail(subject: String, receivers: Array<String>, body: String) {
        if (receivers.all { it.isEmailValid() }) {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, receivers)
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
                addCategory(Intent.CATEGORY_BROWSABLE)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER
            }
            context.startActivity(emailIntent)
        } else {
            throw URLauncherException(
                URLauncherException.INVALID_EMAIL,
                "Invalid email(s) $receivers"
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @Throws(ActivityNotFoundException::class, URLauncherException::class)
    fun launchPhoneDialer(phoneNumber: String) {
        if (phoneNumber.isPhoneNumberValid()) {
            val phoneIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
                addCategory(Intent.CATEGORY_BROWSABLE)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER
            }
            context.startActivity(phoneIntent)
        } else {
            throw URLauncherException(
                URLauncherException.INVALID_PHONE_NUMBER,
                "Invalid phone number $phoneNumber"
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @Throws(ActivityNotFoundException::class, URLauncherException::class)
    fun sendSMSMessage(phoneNumber: String, message: String) {
        if (phoneNumber.isPhoneNumberValid()) {
            val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
                type = "text/plain"
                data = Uri.parse("smsto:$phoneNumber")
                putExtra("sms_body", message)
                addCategory(Intent.CATEGORY_BROWSABLE)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER
            }
            context.startActivity(smsIntent)
        } else {
            throw URLauncherException(
                URLauncherException.INVALID_PHONE_NUMBER,
                "Invalid phone number $phoneNumber"
            )
        }
    }

    private fun String.isUrlValid(): Boolean =
        URLUtil.isValidUrl(this)

    private fun String.isEmailValid(): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private fun String.isPhoneNumberValid(): Boolean =
        Pattern.compile("^[+]?[0-9]{10,13}\$")
            .matcher(this)
            .matches()
}
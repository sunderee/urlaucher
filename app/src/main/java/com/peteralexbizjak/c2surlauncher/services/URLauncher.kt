package com.peteralexbizjak.c2surlauncher.services

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.peteralexbizjak.c2surlauncher.utils.URLauncherException
import com.peteralexbizjak.c2surlauncher.utils.isEmailValid
import com.peteralexbizjak.c2surlauncher.utils.isPhoneNumberValid
import com.peteralexbizjak.c2surlauncher.utils.isUrlValid

internal class URLauncher(private val context: Context) {
    @RequiresApi(Build.VERSION_CODES.R)
    @Throws(ActivityNotFoundException::class, URLauncherException::class)
    fun launchURL(url: String) {
        if (url.isUrlValid()) {
            val urlLauncherIntent = Intent(ACTION_VIEW, Uri.parse(url)).apply {
                addCategory(CATEGORY_BROWSABLE)
                flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_REQUIRE_NON_BROWSER
            }
            context.startActivity(urlLauncherIntent)
        } else {
            throw URLauncherException("Invalid URL $url")
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @Throws(ActivityNotFoundException::class, URLauncherException::class)
    fun sendEmail(subject: String, receivers: Array<String>, body: String) {
        if (receivers.all { it.isEmailValid() }) {
            val emailIntent = Intent(ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(EXTRA_EMAIL, receivers)
                putExtra(EXTRA_SUBJECT, subject)
                putExtra(EXTRA_TEXT, body)
                addCategory(CATEGORY_BROWSABLE)
                flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_REQUIRE_NON_BROWSER
            }
            context.startActivity(emailIntent)
        } else {
            throw URLauncherException("Invalid email(s) $receivers")
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @Throws(ActivityNotFoundException::class, URLauncherException::class)
    fun launchPhoneDialer(phoneNumber: String) {
        if (phoneNumber.isPhoneNumberValid()) {
            val phoneIntent = Intent(ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
                addCategory(CATEGORY_BROWSABLE)
                flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_REQUIRE_NON_BROWSER
            }
            context.startActivity(phoneIntent)
        } else {
            throw URLauncherException("Invalid phone number $phoneNumber")
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @Throws(ActivityNotFoundException::class, URLauncherException::class)
    fun sendSMSMessage(phoneNumber: String, message: String) {
        if (phoneNumber.isPhoneNumberValid()) {
            val smsIntent = Intent(ACTION_SENDTO).apply {
                type = "text/plain"
                data = Uri.parse("smsto:")
                putExtra("sms_body", message)
                addCategory(CATEGORY_BROWSABLE)
                flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_REQUIRE_NON_BROWSER
            }
            context.startActivity(smsIntent)
        } else {
            throw URLauncherException("Invalid phone number $phoneNumber")
        }
    }
}
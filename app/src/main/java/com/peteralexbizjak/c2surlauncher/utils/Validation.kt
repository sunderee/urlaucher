package com.peteralexbizjak.c2surlauncher.utils

import android.webkit.URLUtil
import java.util.regex.Pattern

fun String.isUrlValid(): Boolean = URLUtil.isValidUrl(this)

fun String.isEmailValid(): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isPhoneNumberValid(): Boolean = Pattern.compile("^[+]?[0-9]{10,13}\$")
    .matcher(this)
    .matches()
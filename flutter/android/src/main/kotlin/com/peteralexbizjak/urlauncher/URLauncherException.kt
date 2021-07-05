package com.peteralexbizjak.urlauncher

class URLauncherException(
    code: Int,
    errorMessage: String
) : Exception("$code: $errorMessage") {
    companion object {
        const val INVALID_URL = 0
        const val INVALID_EMAIL = 1
        const val INVALID_PHONE_NUMBER = 2
    }
}
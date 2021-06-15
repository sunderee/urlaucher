package com.peteralexbizjak.urlauncher

/**
 * Custom exception used to denote URLauncher failed to perform an action.
 */
class URLauncherException(code: Int, errorMessage: String) : Exception("$code: $errorMessage") {
    companion object {
        /**
         * This error code specifies the URL was invalid, for example, not preceding host name with
         * `http://` or `https://`.
         */
        const val INVALID_URL = 0

        /**
         * This error code specifies the email address is invalid. Check follows the Android's
         * internal `android.util.Patterns.EMAIL_ADDRESS` regular expression.
         */
        const val INVALID_EMAIL = 1

        /**
         * This error code specifies the phone number is invalid.
         */
        const val INVALID_PHONE_NUMBER = 2
    }
}
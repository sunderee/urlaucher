package com.peteralexbizjak.urlauncher

import android.content.ActivityNotFoundException
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

class MethodCallHandlerImplementation(
    private val urLauncher: URLauncher
) : MethodChannel.MethodCallHandler {
    private var methodChannel: MethodChannel? = null

    @Throws(ClassCastException::class)
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            LAUNCH_URL -> {
                val arguments = call.arguments as List<*>
                try {
                    if (arguments.isEmpty()) {
                        result.error(
                            ERROR_CODE_ARGUMENTS,
                            "There was an issue with the provided argument",
                            null
                        )
                    }
                    (arguments.first() as String?)?.let { urLauncher.launchURL(it) }
                    result.success(true)
                } catch (exception: URLauncherException) {
                    result.error(ERROR_CODE_PLUGIN, exception.message, null)
                } catch (exception: ActivityNotFoundException) {
                    result.error(ERROR_CODE_PLUGIN, exception.message, exception.stackTrace)
                }
            }
            SEND_EMAIL -> {
                val arguments = call.arguments as List<*>
                try {
                    if (arguments.isEmpty() || arguments.size != 3) {
                        result.error(
                            ERROR_CODE_ARGUMENTS,
                            "There was an issue with the provided argument",
                            null
                        )
                    }
                    urLauncher.sendEmail(
                        arguments[0] as String? ?: "",
                        arrayOf(arguments[1] as String? ?: ""),
                        arguments[2] as String? ?: ""
                    )
                    result.success(true)
                } catch (exception: URLauncherException) {
                    result.error(ERROR_CODE_PLUGIN, exception.message, null)
                } catch (exception: ActivityNotFoundException) {
                    result.error(ERROR_CODE_PLUGIN, exception.message, exception.stackTrace)
                }
            }
            LAUNCH_PHONE_DIALER -> {
                val arguments = call.arguments as List<*>
                try {
                    if (arguments.isEmpty()) {
                        result.error(
                            ERROR_CODE_ARGUMENTS,
                            "There was an issue with the provided argument",
                            null
                        )
                    }
                    (arguments.first() as String?)?.let { urLauncher.launchPhoneDialer(it) }
                    result.success(true)
                } catch (exception: URLauncherException) {
                    result.error(ERROR_CODE_PLUGIN, exception.message, null)
                } catch (exception: ActivityNotFoundException) {
                    result.error(ERROR_CODE_PLUGIN, exception.message, exception.stackTrace)
                }
            }
            SEND_SMS_MESSAGE -> {
                val arguments = call.arguments as List<*>
                try {
                    if (arguments.isEmpty() || arguments.size != 3) {
                        result.error(
                            ERROR_CODE_ARGUMENTS,
                            "There was an issue with the provided argument",
                            null
                        )
                    }
                    urLauncher.sendSMSMessage(
                        arguments[0] as String? ?: "",
                        arguments[1] as String? ?: ""
                    )
                    result.success(true)
                } catch (exception: URLauncherException) {
                    result.error(ERROR_CODE_PLUGIN, exception.message, null)
                } catch (exception: ActivityNotFoundException) {
                    result.error(ERROR_CODE_PLUGIN, exception.message, exception.stackTrace)
                }
            }
        }
    }

    fun startListening(messenger: BinaryMessenger) {
        if (methodChannel != null) {
            stopListening()
        }
        methodChannel = MethodChannel(messenger, "urlauncher")
        methodChannel?.setMethodCallHandler(this)
    }

    fun stopListening() {
        if (methodChannel == null) {
            return
        }
        methodChannel?.setMethodCallHandler(null)
        methodChannel = null
    }

    companion object {
        private const val LAUNCH_URL = "launchURL"
        private const val SEND_EMAIL = "sendEmail"
        private const val LAUNCH_PHONE_DIALER = "launchPhoneDialer"
        private const val SEND_SMS_MESSAGE = "sendSMSMessage"

        private const val ERROR_CODE_PLUGIN = "INTERNAL_ERROR"
        private const val ERROR_CODE_ARGUMENTS = "INVALID_ARGUMENT"
    }
}
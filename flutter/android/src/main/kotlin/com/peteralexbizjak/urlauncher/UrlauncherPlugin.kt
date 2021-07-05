package com.peteralexbizjak.urlauncher

import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin

class UrlauncherPlugin : FlutterPlugin {
    private var handler: MethodCallHandlerImplementation? = null

    override fun onAttachedToEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        handler = MethodCallHandlerImplementation(URLauncher(binding.applicationContext))
        handler?.startListening(binding.binaryMessenger)
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        if (handler == null) {
            return
        }
        handler?.stopListening()
        handler = null
    }
}

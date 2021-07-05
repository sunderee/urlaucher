import 'dart:async';

import 'package:flutter/services.dart';

/// Android-only Flutter plugin for launching various intents (URL, emails, phone
/// calls/SMS messages), port of URLauncher.
class URLauncher {
  static const MethodChannel _channel = const MethodChannel('urlauncher');

  /// Launch URL from the host and (optional) endpoint. By default, we parse the
  /// URI with HTTPS scheme. If you wish to use insecure connection, don't.
  Future<bool> launchURL(
    String host, {
    String? endpoint,
  }) async {
    try {
      final url = Uri.https(host, endpoint ?? '');
      return await _channel.invokeMethod(
        'launchURL',
        [url.toString()],
      );
    } on PlatformException catch (e) {
      print(e);
      return false;
    }
  }

  /// Send email via default email client
  Future<bool> sendEmail(
    String subject,
    String receiverAddress,
    String emailBody,
  ) async {
    try {
      return await _channel.invokeMethod(
        "sendEmail",
        [subject, receiverAddress, emailBody],
      );
    } on PlatformException catch (e) {
      print(e);
      return false;
    }
  }

  /// Launch a phone dialer
  Future<bool> launchPhoneDialer(
    String phoneNumber,
  ) async {
    try {
      return await _channel.invokeMethod(
        "launchPhoneDialer",
        [phoneNumber],
      );
    } on PlatformException catch (e) {
      print(e);
      return false;
    }
  }

  /// Launch default SMS client with default text message
  Future<bool> sendSMSMessage(
    String phoneNumber,
    String smsMessageBody,
  ) async {
    try {
      return await _channel.invokeMethod(
        "sendSMSMessage",
        [phoneNumber, smsMessageBody],
      );
    } on PlatformException catch (e) {
      print(e);
      return false;
    }
  }
}

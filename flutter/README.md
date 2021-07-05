# URLauncher Flutter

Android-only Flutter plugin for launching various intents (URL, emails, phone calls/SMS messages), port of URLauncher. Functionalities

* Launching URL in a device's default browser
* Launching email client with subject/receiver/body pre-filled
* Launching phone dialer application
* Launching messaging client with phone number and body pre-filled

This library requires the minimum SDK version 23 (Android 6.0) and is available all the way up to
Android 11 (SDK version 30).

## Usage

You can check the `example/lib/main.dart` with a full example ("clone" app of the URLauncher native one).

```dart
final launcher = URLauncher();

// Launch URL in a browser
launcher.launchURL(
    'some.host.com',
    endpoint: 'slash-something',
);

// Launch email client
launcher.sendEmail(
    'Test',
    'john.doe@gmail.com',
    'Test email message body',
);

// Launch phone dialer
launcher.launchPhoneDialer('+15556500');

// Launch SMS client
launcher.sendSMSMessage(
    '+15556500',
    'Test SNS message body',
)
```

## License

Open sourced under MIT license.
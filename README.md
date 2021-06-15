# URLauncher

Simplest possible library for Android 11+ which enables

* Launching URL in a device's default browser
* Launching email client with subject/receiver/body pre-filled
* Launching phone dialer application
* Launching messaging client with phone number and body pre-filled

## Usage

Instantiate `URLauncher` class and then call one of the four provided methods

```kotlin
val launcher = URLauncher(context)

// Launch URL in a browser
val url = "https://google.com"
launcher.launchURL(url)

// Launch email client
val subject = "Test"
val receivers = arrayOf("john.doe@gmail.com", "jane.smith@gmail.com")
val body = "Test email message body"
launcher.sendEmail(subject, receivers, body)

// Launch phone dialler
val phoneNumber = "+15556500"
launcher.launchPhoneDialer(phoneNumber)

// Launch SMS client
val phoneNumber = "+15556500"
val smsBody = "Test SMS message body"
launcher.sendSMSMessage(phoneNumber, smsBody)
```

## License

Open sourced under MIT license.
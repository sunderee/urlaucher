import 'package:flutter/material.dart';
import 'package:urlauncher/urlauncher.dart';

void main() {
  runApp(App());
}

const Color COLOR_PRODUCT = const Color(0xFFA9CEF4);

enum SmsOrCall { sms, call }

class App extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(
        primaryColor: COLOR_PRODUCT,
        accentColor: COLOR_PRODUCT,
      ),
      home: HomeScreen(),
    );
  }
}

class HomeScreen extends StatefulWidget {
  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  final _urlController = TextEditingController();

  final _subjectController = TextEditingController();
  final _receiverController = TextEditingController();
  final _bodyController = TextEditingController();

  final _phoneController = TextEditingController();
  final _smsMessageController = TextEditingController();

  final _launcher = URLauncher();

  SmsOrCall _smsOrCall = SmsOrCall.call;

  @override
  void dispose() {
    _urlController.dispose();
    _subjectController.dispose();
    _receiverController.dispose();
    _bodyController.dispose();
    _phoneController.dispose();
    _smsMessageController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('URLauncher'),
        centerTitle: true,
      ),
      body: SafeArea(
        minimum: const EdgeInsets.all(16.0),
        child: ListView(
          children: [
            Text('Launch URL in device\'s default browser'),
            TextField(
              controller: _urlController,
              decoration: InputDecoration(
                hintText: 'URL',
              ),
            ),
            SizedBox(height: 8.0),
            MaterialButton(
              color: COLOR_PRODUCT,
              child: Text('LAUNCH URL'),
              onPressed: () async {
                await _launcher.launchURL(_urlController.text);
              },
            ),
            SizedBox(height: 16.0),
            Text('Send email via your default email client'),
            TextField(
              controller: _subjectController,
              decoration: InputDecoration(
                hintText: 'Subject',
              ),
            ),
            TextField(
              controller: _receiverController,
              decoration: InputDecoration(
                hintText: 'Receiver (to)',
              ),
            ),
            TextField(
              controller: _bodyController,
              decoration: InputDecoration(
                hintText: 'Body',
              ),
            ),
            SizedBox(height: 8.0),
            MaterialButton(
              color: COLOR_PRODUCT,
              child: Text('SEND EMAIL'),
              onPressed: () async {
                await _launcher.sendEmail(
                  _subjectController.text,
                  _receiverController.text,
                  _bodyController.text,
                );
              },
            ),
            SizedBox(height: 16.0),
            Text('Make a phone call or send an SMS message'),
            ListTile(
              contentPadding: EdgeInsets.zero,
              leading: Radio<SmsOrCall>(
                groupValue: _smsOrCall,
                value: SmsOrCall.call,
                onChanged: (SmsOrCall? option) {
                  if (option != null) {
                    setState(() => _smsOrCall = option);
                  }
                },
              ),
              title: Text('Make a phone call'),
            ),
            ListTile(
              contentPadding: EdgeInsets.zero,
              leading: Radio<SmsOrCall>(
                groupValue: _smsOrCall,
                value: SmsOrCall.sms,
                onChanged: (SmsOrCall? option) {
                  if (option != null) {
                    setState(() => _smsOrCall = option);
                  }
                },
              ),
              title: Text('Send a text message'),
            ),
            TextField(
              controller: _phoneController,
              decoration: InputDecoration(
                hintText: 'Phone number',
              ),
            ),
            _smsOrCall == SmsOrCall.sms
                ? TextField(
                    controller: _smsMessageController,
                    decoration: InputDecoration(
                      hintText: 'SMS message',
                    ),
                  )
                : SizedBox(),
            SizedBox(height: 8.0),
            MaterialButton(
              color: COLOR_PRODUCT,
              child: Text('FIRE IT UP!'),
              onPressed: () async {
                if (_smsOrCall == SmsOrCall.call) {
                  await _launcher.launchPhoneDialer(
                    _phoneController.text,
                  );
                } else {
                  await _launcher.sendSMSMessage(
                    _phoneController.text,
                    _smsMessageController.text,
                  );
                }
              },
            ),
          ],
        ),
      ),
    );
  }
}

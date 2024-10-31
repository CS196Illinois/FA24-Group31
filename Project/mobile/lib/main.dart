import 'package:flutter/material.dart';

import 'homepage.dart';


void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Matched.lol',
      debugShowCheckedModeBanner: false, // Disable debug banner
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.blue),
        useMaterial3: true,
      ),
      home: const SplashScreen(), // Set SplashScreen as the home page
    );
  }
}

class SplashScreen extends StatefulWidget {
  const SplashScreen({super.key});

  @override
  _SplashScreenState createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {
  @override
  void initState() {
    super.initState();
    // Navigate to HomePage after 2 seconds
    Future.delayed(const Duration(seconds: 2), () {
      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (context) => const HomePage()), // Replace with your HomePage widget
      );
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.pinkAccent, // Splash screen background color
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            // Dummy logo in the center
            Container(
              width: 100, // Adjust width as needed
              height: 100, // Adjust height as needed
              decoration: BoxDecoration(
                shape: BoxShape.circle,
                color: Colors.white, // Placeholder color for the logo
                image: DecorationImage(
                  image: AssetImage('assets/images/lol_logo.png'), // Replace with your logo path
                  fit: BoxFit.cover,
                ),
              ),
            ),
            const SizedBox(height: 20), // Space between logo and text
            Text(
              'Matched.lol',
              style: TextStyle(color: Colors.white, fontSize: 40,),
            ),
          ],
        ),
      ),
    );
  }
}

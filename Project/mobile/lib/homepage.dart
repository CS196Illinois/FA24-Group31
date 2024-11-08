import 'package:first_macos_app/aboutus.dart';
import 'package:first_macos_app/matching.dart';
import 'package:flutter/material.dart';
import 'accountinfo.dart';
import 'ourmission.dart'; // Make sure to import any necessary pages
import 'community.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        title: const Text(
          "Matched.lol",
          style: TextStyle(
            fontSize: 23,
            color: Colors.blueAccent,
          ),
        ),
        leading: Builder(
          builder: (context) => IconButton(
            icon: const Icon(Icons.menu, color: Colors.blueAccent),
            onPressed: () {
              Scaffold.of(context).openDrawer();
            },
          ),
        ),
      ),
      drawer: Container(
        width: MediaQuery.of(context).size.width * 0.69, // Set the width to 40% of the screen width
        child: Drawer(
          backgroundColor: Colors.white,
          child: ListView(
            padding: EdgeInsets.zero,
            children: [
              DrawerHeader(
                decoration: BoxDecoration(
                  color: Colors.blueAccent,
                ),
                child: Column(
                  children: [
                    const CircleAvatar(
                      radius: 40, // Adjust the size of the avatar
                      backgroundImage: AssetImage('assets/images/lol_logo.png'), // Replace with your logo asset
                      // If you want a default color for the avatar when image is not available
                      // backgroundColor: Colors.grey,
                    ),
                    const SizedBox(height: 10), // Add space between avatar and text
                    const Text(
                      'Matched.lol',
                      style: TextStyle(
                        color: Colors.white,
                        fontSize: 24,
                        fontWeight: FontWeight.bold, // Make the header bold
                      ),
                    ),
                  ],
                ),
              ),
              ListTile(
                title: const Text('Home Page'),
                onTap: () {
                  Navigator.pop(context); // Close the drawer
                  // Optionally, navigate to Home Page if needed
                },
              ),
              ListTile(
                title: const Text('Our Mission'),
                onTap: () {
                  Navigator.pop(context); // Close the drawer
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (context) => MissionPage()),
                  );
                },
              ),
              ListTile(
                title: const Text('About Us'),
                onTap: () {
                  Navigator.pop(context); // Close the drawer
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (context) => AboutUsPage()),
                  );
                },
              ),
              ListTile(
                title: const Text('Community'),
                onTap: () {
                  Navigator.pop(context); // Close the drawer
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (context) => CommunityPage()),
                  );
                },
              ),
            ],
          ),
        ),
      ),
      body: Container(
        decoration: const BoxDecoration(
          gradient: LinearGradient(
            begin: Alignment.topCenter,
            end: Alignment.bottomCenter,
            colors: [
              Color(0xFFFFA07A), // Light Salmon
              Color(0xFFFF69B4), // Hot Pink
              Color(0xFFFFB6C1), // Light Pink
            ],
          ),
        ),
        child: SingleChildScrollView(
          child: Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                SizedBox(height: MediaQuery.of(context).size.height * 0.25),
                const Text(
                  'League of Legends themed matching app.',
                  textAlign: TextAlign.center,
                  style: TextStyle(
                    fontSize: 28,
                    fontWeight: FontWeight.bold,
                    color: Colors.white,
                  ),
                ),
                SizedBox(height: 20),
                const Text(
                  'Find your perfect partner, in-game or in-real-life.',
                  textAlign: TextAlign.center,
                  style: TextStyle(
                    fontSize: 16,
                    color: Colors.white,
                  ),
                ),
                SizedBox(height: 20),
                ElevatedButton(
                  onPressed: () {
                    // Navigate to the signup page
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => AccountInfoPage()),
                    );
                  },
                  child: Text(
                    'Sign up / Login',
                    style: TextStyle(color: Colors.blueAccent),
                  ),
                ),
                SizedBox(height: 350), // Add extra space to make it scrollable
              ],
            ),
          ),
        ),
      ),
    );
  }
}

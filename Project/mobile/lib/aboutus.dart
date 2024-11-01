import 'package:flutter/material.dart';

class AboutUsPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('About Us'),
        centerTitle: true,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: SingleChildScrollView(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                'About Us',
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                ),
              ),
              SizedBox(height: 16),
              Text(
                'Our Team',
                style: TextStyle(
                  fontSize: 20,
                  fontWeight: FontWeight.bold,
                ),
              ),
              SizedBox(height: 8),
              Text(
                'Jeffrey Huang - Project Manager\n'
                    'Adhi Thirumala - Backend / DB\n'
                    'Raymond Yang - Backend / DB\n'
                    'Vinay Rajagopalan - Backend\n'
                    'Barry Liu - Frontend\n'
                    'Carlton Woo - Frontend\n'
                    'Ekansh Arora - Frontend / Mobile Dev\n'
                    'Enya Chen - Frontend\n'
                    'Maya Swaminathan - Frontend',
                style: TextStyle(
                  fontSize: 16,
                  height: 1.5,
                ),
              ),
              SizedBox(height: 16),
              Text(
                'Our Tech Stack',
                style: TextStyle(
                  fontSize: 20,
                  fontWeight: FontWeight.bold,
                ),
              ),
              SizedBox(height: 8),
              Text(
                'Frontend: React, Next.js, Tailwind, TypeScript, Flutter\n'
                    'Backend: Java, Spring Boot, SQLite3',
                style: TextStyle(
                  fontSize: 16,
                  height: 1.5,
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

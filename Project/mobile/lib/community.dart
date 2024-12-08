import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';

class CommunityPage extends StatelessWidget {
  // Dummy URLs for social media links
  final String discordUrl = 'https://discord.com/invite/dummylink';
  final String instagramUrl = 'https://instagram.com/dummylink';
  final String twitterUrl = 'https://twitter.com/dummylink';

  // Function to launch URLs
  void _launchURL(String url) async {
    if (await canLaunch(url)) {
      await launch(url);
    } else {
      throw 'Could not launch $url';
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Community'),
        centerTitle: true,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              'Join Our Community',
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.bold,
              ),
            ),
            SizedBox(height: 16),
            Text(
              'Connect with us on our social platforms to stay updated and join the conversation!',
              textAlign: TextAlign.center,
              style: TextStyle(
                fontSize: 16,
              ),
            ),
            SizedBox(height: 32),
            SocialMediaButton(
              label: 'Discord',
              icon: Icons.discord, // Requires a custom icon, so use an image or other icon as needed
              url: discordUrl,
              onTap: () => _launchURL(discordUrl),
            ),
            SizedBox(height: 16),
            SocialMediaButton(
              label: 'Instagram',
              icon: Icons.camera_alt,
              url: instagramUrl,
              onTap: () => _launchURL(instagramUrl),
            ),
            SizedBox(height: 16),
            SocialMediaButton(
              label: 'Twitter/X',
              icon: Icons.alternate_email,
              url: twitterUrl,
              onTap: () => _launchURL(twitterUrl),
            ),
          ],
        ),
      ),
    );
  }
}

class SocialMediaButton extends StatelessWidget {
  final String label;
  final IconData icon;
  final String url;
  final VoidCallback onTap;

  SocialMediaButton({
    required this.label,
    required this.icon,
    required this.url,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return ElevatedButton.icon(
      onPressed: onTap,
      icon: Icon(icon, size: 28),
      label: Text(
        label,
        style: TextStyle(fontSize: 18),
      ),
      style: ElevatedButton.styleFrom(
        padding: EdgeInsets.symmetric(horizontal: 20, vertical: 12),
      ),
    );
  }
}

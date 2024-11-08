import 'package:flutter/material.dart';

class MatchingPage extends StatefulWidget {
  @override
  _MatchingPageState createState() => _MatchingPageState();
}

class _MatchingPageState extends State<MatchingPage> {
  final List<UserProfile> profiles = [
    UserProfile(
      name: 'John Doe',
      age: 25,
      pronouns: 'he/him',
      imageUrl: 'https://via.placeholder.com/100',
      rank: 'Diamond',
      championsPlayed: 'Ezreal, Lux, Zed',
      hasLikedCurrentUser: false,  // Add this field
    ),
    UserProfile(
      name: 'Jane Smith',
      age: 28,
      pronouns: 'she/her',
      imageUrl: 'https://via.placeholder.com/100',
      rank: 'Platinum',
      championsPlayed: 'Ahri, Yasuo, Lee Sin',
      hasLikedCurrentUser: true,  // This user has already liked the current user
    ),
  ];

  Map<String, bool> expandedState = {};
  Map<String, bool> likedByCurrentUser = {};

  void _showMatchDialog() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('It\'s a Match! 🎮'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Icon(
                Icons.favorite,
                color: Colors.red,
                size: 50,
              ),
              SizedBox(height: 16),
              Text('You both liked each other!'),
            ],
          ),
          actions: [
            TextButton(
              child: Text('Start Chatting'),
              onPressed: () {
                Navigator.of(context).pop();
                // Add chat functionality here
              },
            ),
            TextButton(
              child: Text('Keep Browsing'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  void _handleSwipe(UserProfile profile, DismissDirection direction) {
    if (direction == DismissDirection.endToStart) {
      // Swiped left - Dislike
      setState(() {
        likedByCurrentUser[profile.name] = false;
        profiles.remove(profile);
      });
    } else if (direction == DismissDirection.startToEnd) {
      // Swiped right - Like
      setState(() {
        likedByCurrentUser[profile.name] = true;

        // Only show match if other user has already liked current user
        if (profile.hasLikedCurrentUser) {
          Future.delayed(Duration(milliseconds: 0), () {
            _showMatchDialog();
          });
        }
        profiles.remove(profile);
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Dating Profile'),
      ),
      body: Center(
        child: profiles.isNotEmpty
            ? Stack(
          alignment: Alignment.center,
          children: profiles.map((profile) {
            expandedState.putIfAbsent(profile.name, () => false);
            likedByCurrentUser.putIfAbsent(profile.name, () => false);

            return Dismissible(
              key: ValueKey(profile.name),
              direction: DismissDirection.horizontal,
              onDismissed: (direction) => _handleSwipe(profile, direction),
              background: Container(
                color: Colors.green,
                alignment: Alignment.centerLeft,
                child: Padding(
                  padding: const EdgeInsets.only(left: 20.0),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Icon(Icons.favorite, color: Colors.white, size: 30),
                      Text(
                        'LIKE',
                        style: TextStyle(color: Colors.white),
                      ),
                    ],
                  ),
                ),
              ),
              secondaryBackground: Container(
                color: Colors.red,
                alignment: Alignment.centerRight,
                child: Padding(
                  padding: const EdgeInsets.only(right: 20.0),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Icon(Icons.close, color: Colors.white, size: 30),
                      Text(
                        'NOPE',
                        style: TextStyle(color: Colors.white),
                      ),
                    ],
                  ),
                ),
              ),
              child: GestureDetector(
                onVerticalDragUpdate: (details) {
                  setState(() {
                    if (details.delta.dy < -10) {
                      expandedState[profile.name] = true;
                    } else if (details.delta.dy > 10) {
                      expandedState[profile.name] = false;
                    }
                  });
                },
                child: Container(
                  width: MediaQuery.of(context).size.width * 0.7,
                  child: Card(
                    elevation: 10.0,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(20.0),
                    ),
                    child: Padding(
                      padding: const EdgeInsets.all(25.0),
                      child: Column(
                        mainAxisSize: MainAxisSize.min,
                        children: [
                          CircleAvatar(
                            radius: 80.0,
                            backgroundImage: NetworkImage(profile.imageUrl),
                          ),
                          SizedBox(height: 25.0),
                          Text(
                            '${profile.name}, ${profile.age}',
                            style: TextStyle(
                              fontSize: 24.0,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                          SizedBox(height: 8.0),
                          Text(
                            profile.pronouns,
                            style: TextStyle(
                              fontSize: 18.0,
                              color: Colors.grey[600],
                            ),
                          ),
                          SizedBox(height: 8.0),
                          AnimatedContainer(
                            duration: Duration(milliseconds: 300),
                            curve: Curves.easeInOut,
                            height: expandedState[profile.name]! ? 120.0 : 0.0,
                            child: expandedState[profile.name]!
                                ? Column(
                              children: [
                                Divider(color: Colors.grey),
                                Text(
                                  'Rank: ${profile.rank}',
                                  style: TextStyle(
                                    fontSize: 16.0,
                                    color: Colors.grey[700],
                                  ),
                                ),
                                SizedBox(height: 8.0),
                                Text(
                                  'Champions Played:\n${profile.championsPlayed}',
                                  style: TextStyle(
                                    fontSize: 16.0,
                                    color: Colors.grey[700],
                                  ),
                                ),
                              ],
                            )
                                : SizedBox.shrink(),
                          ),
                        ],
                      ),
                    ),
                  ),
                ),
              ),
            );
          }).toList(),
        )
            : Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Icon(
                Icons.sentiment_satisfied,
                size: 60,
                color: Colors.grey,
              ),
              SizedBox(height: 16),
              Text(
                'No more profiles',
                style: TextStyle(
                  fontSize: 24,
                  color: Colors.grey,
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class UserProfile {
  final String name;
  final int age;
  final String pronouns;
  final String imageUrl;
  final String rank;
  final String championsPlayed;
  final bool hasLikedCurrentUser;  // Add this field

  UserProfile({
    required this.name,
    required this.age,
    required this.pronouns,
    required this.imageUrl,
    required this.rank,
    required this.championsPlayed,
    this.hasLikedCurrentUser = false,  // Default to false
  });
}
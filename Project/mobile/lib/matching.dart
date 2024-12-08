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
    ),
    UserProfile(
      name: 'Jane Smith',
      age: 28,
      pronouns: 'she/her',
      imageUrl: 'https://via.placeholder.com/100',
      rank: 'Platinum',
      championsPlayed: 'Ahri, Yasuo, Lee Sin',
    ),
    // Add more profiles here
  ];

  // Track expanded state for each profile
  Map<String, bool> expandedState = {};

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
            // Initialize expanded state if not already done
            expandedState.putIfAbsent(profile.name, () => false);

            return Dismissible(
              key: ValueKey(profile.name),
              direction: DismissDirection.horizontal,
              onDismissed: (direction) {
                setState(() {
                  // Remove the swiped profile from the list
                  profiles.remove(profile);
                });
              },
              background: Container(
                color: Colors.red,
                alignment: Alignment.centerLeft,
                child: Icon(Icons.clear, color: Colors.white),
              ),
              secondaryBackground: Container(
                color: Colors.green,
                alignment: Alignment.centerRight,
                child: Icon(Icons.check, color: Colors.white),
              ),
              child: GestureDetector(
                onVerticalDragUpdate: (details) {
                  setState(() {
                    if (details.delta.dy < -10) {
                      // Swipe up to expand
                      expandedState[profile.name] = true;
                    } else if (details.delta.dy > 10) {
                      // Swipe down to collapse
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
                          // Animated section for "Rank" and "Champions Played"
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
                                    fontSize: 16.0, color: Colors.grey[700],
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
            : Text('No more profiles'),
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

  UserProfile({
    required this.name,
    required this.age,
    required this.pronouns,
    required this.imageUrl,
    required this.rank,
    required this.championsPlayed,
  });
}

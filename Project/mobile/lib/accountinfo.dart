import 'package:flutter/material.dart';
import 'matching.dart';
import 'package:intl/intl.dart'; // Import for date formatting

class AccountInfoPage extends StatefulWidget {
  @override
  _AccountInfoPageState createState() => _AccountInfoPageState();
}

class _AccountInfoPageState extends State<AccountInfoPage> {
  final _formKey = GlobalKey<FormState>();
  final _firstNameController = TextEditingController();
  final _lastNameController = TextEditingController();
  final _riotIdController = TextEditingController();
  final _discordIdController = TextEditingController();

  // New Controllers and Variables
  final _birthdateController = TextEditingController();
  DateTime? _selectedBirthdate;

  // Regular expression to match ONLY letters (no numbers, spaces, or special characters)
  final RegExp _letterRegex = RegExp(r'^[a-zA-Z]+$');

  @override
  void dispose() {
    _firstNameController.dispose();
    _lastNameController.dispose();
    _riotIdController.dispose();
    _discordIdController.dispose();
    _birthdateController.dispose(); // Dispose new controller
    super.dispose();
  }

  String? _validateRiotId(String? value) {
    if (value == null || value.isEmpty) {
      return 'Please enter your Riot ID';
    }

    final parts = value.split('#');
    if (parts.length != 2) {
      return 'Riot ID must be in the format "gamename#tag';
    }

    final gameName = parts[0];
    final tagline = parts[1];

    // Game Name validations
    if (gameName.length < 3 || gameName.length > 16) {
      return 'Game Name must be 3-16 characters long';
    }
    if (!RegExp(r'^[a-zA-Z0-9]+$').hasMatch(gameName)) {
      return 'Game Name can only contain alphanumeric characters';
    }
    if (gameName.contains('Riot')) {
      return 'Game Name cannot contain "Riot"';
    }

    // Tagline validations
    if (tagline.length < 3 || tagline.length > 5) {
      return 'Tagline must be 3-5 characters long';
    }
    if (!RegExp(r'^[a-zA-Z0-9]+$').hasMatch(tagline)) {
      return 'Tagline can only contain alphanumeric characters';
    }

    return null; // Return null if all validations pass
  }

  // Function to calculate age based on selected birthdate
  int? _calculateAge(DateTime birthdate) {
    final today = DateTime.now();
    int age = today.year - birthdate.year;
    if (today.month < birthdate.month || (today.month == birthdate.month && today.day < birthdate.day)) {
      age--;
    }
    return age;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        title: const Text(
          "Account Info",
          style: TextStyle(
            color: Colors.blueAccent,
          ),
        ),
      ),
      body: Center(
        child: SingleChildScrollView(
          child: Container(
            width: MediaQuery.of(context).size.width * 0.9,
            height: MediaQuery.of(context).size.width * 1.5,
            constraints: BoxConstraints(maxWidth: 400),
            child: Card(
              elevation: 4,
              child: Padding(
                padding: const EdgeInsets.all(30.0),
                child: Form(
                  key: _formKey,
                  child: Column(
                    mainAxisSize: MainAxisSize.min,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        'First Name',
                        style: TextStyle(fontSize: 16, fontWeight: FontWeight.w500),
                      ),
                      SizedBox(height: 8),
                      TextFormField(
                        controller: _firstNameController,
                        decoration: InputDecoration(
                          hintText: 'Enter your first name',
                          border: OutlineInputBorder(),
                          errorMaxLines: 2,
                        ),
                        validator: (value) {
                          if (value == null || value.isEmpty) {
                            return 'Please enter your first name';
                          }
                          if (!_letterRegex.hasMatch(value)) {
                            return 'First name can only contain letters (a-z, A-Z)';
                          }
                          return null;
                        },
                        onChanged: (value) {
                          if (value.isNotEmpty && !_letterRegex.hasMatch(value)) {
                            _firstNameController.text = value.replaceAll(RegExp(r'[^a-zA-Z]'), '');
                            _firstNameController.selection = TextSelection.fromPosition(
                              TextPosition(offset: _firstNameController.text.length),
                            );
                          }
                        },
                      ),
                      SizedBox(height: 16),
                      Text(
                        'Last Name',
                        style: TextStyle(fontSize: 16, fontWeight: FontWeight.w500),
                      ),
                      SizedBox(height: 8),
                      TextFormField(
                        controller: _lastNameController,
                        decoration: InputDecoration(
                          hintText: 'Enter your last name',
                          border: OutlineInputBorder(),
                          errorMaxLines: 2,
                        ),
                        validator: (value) {
                          if (value == null || value.isEmpty) {
                            return 'Please enter your last name';
                          }
                          if (!_letterRegex.hasMatch(value)) {
                            return 'Last name can only contain letters (a-z, A-Z)';
                          }
                          return null;
                        },
                        onChanged: (value) {
                          if (value.isNotEmpty && !_letterRegex.hasMatch(value)) {
                            _lastNameController.text = value.replaceAll(RegExp(r'[^a-zA-Z]'), '');
                            _lastNameController.selection = TextSelection.fromPosition(
                              TextPosition(offset: _lastNameController.text.length),
                            );
                          }
                        },
                      ),
                      SizedBox(height: 16),
                      Text(
                        'Birthdate',
                        style: TextStyle(fontSize: 16, fontWeight: FontWeight.w500),
                      ),
                      SizedBox(height: 8),
                      TextFormField(
                        controller: _birthdateController,
                        decoration: InputDecoration(
                          hintText: 'Select your birthdate',
                          border: OutlineInputBorder(),
                          suffixIcon: IconButton(
                            icon: Icon(Icons.calendar_today),
                            onPressed: () async {
                              DateTime? pickedDate = await showDatePicker(
                                context: context,
                                initialDate: _selectedBirthdate ?? DateTime.now(),
                                firstDate: DateTime(1900),
                                lastDate: DateTime.now(),
                              );
                              if (pickedDate != null && pickedDate != _selectedBirthdate) {
                                setState(() {
                                  _selectedBirthdate = pickedDate;
                                  _birthdateController.text = DateFormat('yyyy-MM-dd').format(pickedDate); // Format the date
                                });
                              }
                            },
                          ),
                        ),
                        readOnly: true, // Make it read-only to prevent manual input
                      ),
                      SizedBox(height: 16),
                      Text(
                        'Riot ID',
                        style: TextStyle(fontSize: 16, fontWeight: FontWeight.w500),
                      ),
                      SizedBox(height: 8),
                      TextFormField(
                        controller: _riotIdController,
                        decoration: InputDecoration(
                          hintText: 'Enter your Riot ID (gamename#tag)',
                          border: OutlineInputBorder(),
                        ),
                        validator: _validateRiotId,
                      ),
                      SizedBox(height: 50),
                      Center(
                        child: ElevatedButton(
                          style: ElevatedButton.styleFrom(
                            minimumSize: Size(200, 45),
                          ),
                          onPressed: () {
                            if (_formKey.currentState!.validate()) {
                              // Show a Snackbar to indicate processing
                              ScaffoldMessenger.of(context).showSnackBar(
                                SnackBar(content: Text('Processing Data')),
                              );

                              // Optionally calculate and display the age
                              if (_selectedBirthdate != null) {
                                int? age = _calculateAge(_selectedBirthdate!);
                                ScaffoldMessenger.of(context).showSnackBar(
                                  SnackBar(content: Text('Your age is: $age')),
                                );
                              }

                              // Navigate to the next page after a short delay, if desired
                              Future.delayed(Duration(seconds: 1), () {
                                Navigator.push(
                                  context,
                                  MaterialPageRoute(builder: (context) => MatchingPage()),
                                );
                              });
                            }
                          },
                          child: Text('Submit'),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }
}

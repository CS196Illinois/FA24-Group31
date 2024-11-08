
# Web Server

## Tasks

Ranked by urgency:

- [x] Setup web server (can't do shit without the web server being up yet)
- [ ] Setup out account creation
- [x] figure out login/secret token logic

## Routes

- **/api/v1/check_in** -- DONE - adhi
	- have front end check in at an interval to make sure it is still connected to the backend
	- response: "OK" with status code 200 on success
- **/api/v1/users/create_user**
	- create new user in db
	- response: ??
- **/api/v1/users/delete_user_{by_discord_id? or by_uuid?}**
	- delete user from db
	- we can use json_remove() to remove elements from json_array: https://www.sqlite.org/json1.html#jrm

it would look something like this (edit all rows):

UPDATE users
SET one_way_matched = json_remove(one_way_matched, '$['asdlfkj-sadfjklas-asdlfkj-lsadfj']')
WHERE 1=1;

or something like this (edit rows that contain 'asdlfkj-sadfjklas-asdlfkj-lsadfj'):

UPDATE users
SET one_way_matched = json_remove(one_way_matched, '$['asdlfkj-sadfjklas-asdlfkj-lsadfj']')
WHERE EXISTS (SELECT 1 FROM json_each(one_way_matched) WHERE value = '$['asdlfkj-sadfjklas-asdlfkj-lsadfj'];

double check my sql syntax but you get the logic behind it
	- response: ??
- **/api/v1/users/fetch_by_discord_id**
	- fetch user info by discord_id
	- response: ??
- **/api/v1/users/fetch_by_uuid**
	- fetch user info by uuid
	- response: ??
- **/api/v1/auth/check_if_discord_id_exist**
    - input - the token of the discord redirect URL
    - check if discord id is already in db
    - response: {"exists": true, "token": "6qrZcUqja7812RVdnEKjpzOL4CvHBFG"} or {"exists": false, "token": "6qrZcUqja7812RVdnEKjpzOL4CvHBFG"}
        - redirect to account creation if response "exists": false
        - redirect to profile page if response "exists": true

# DB

> [!NOTE]
> DB structure will probably change soon but I will update accordingly

> [!CAUTION]
> Do not delete *.db.bak

> [!TIP]
> If you accidentally corrupt the database while testing, do `cp main.db.bak main.db'

## Users

discord_id: String (primary key)

riot_id: String

first_name: String

last_name: String

dob: Int

pronouns: String

description: String

roles: json

rank: String

one_way_matched: json_array

two_way_matched: json_array

| discord_id | riot_id | first_name | last_name | dob | pronouns | description | roles | rank | one_way_matched | two_way_matched |
|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|
| "QFg60eu" | "fkC526x1JHbM6OwKQN#wUS0x" | "Bill" | "Washington" | 936346028 | "she/them" | "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " | ["top","jungle","middle"] | "challenger" | ["HFdGu6jD","u9VU4lDMovmVwvz","wGGPS6wjWTEuBp2ew","q5gb0FAdmTBblDv"] | ["6axZbpvS4U46C","BMNa2ge","NTNJ","mUoAouaLHCLbf"] |
| "klTak" | "KhqklOumkW37d#UEkT" | "George" | "Smith" | 710284578 | "she/her" | "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " | ["top","jungle","middle","bottom","suppport"] | "grandmaster" | ["AVydpE","IGxJg","hY5t1wq","BHDy9t5N5HEjU3l","mEyfQCYeE5","b03Ru9nTDpw8X","Ky7t3ZqIVn7DsOj","vTbt3HjYd5MEe6gDo","gNUyjFmUG8qwlBffy","C2htWCUKg8HwQm"] | ["k2KxALyVW","kFAgcCkIgb19Lv","h3klsMrmGxNnybzuL0","cgBgbo","2WkX9iKeEtEiwdlo","dAJjxakt","bvHFIcYvjsvJ","2ZF8","mJZ","C9GPQCdHnD74YweL"]
"vPzGvjwjeBi78szQ" | "1W8v5h#No6j" | "George" | "Adams"| 629376025 | "she/her" | "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " | ["bottom"] | "emerald" | ["z3EbRB8TAt1CE3I6","ts6y8","ySt","bWOc1x0","5mSgx8V","RqWPG7fLmOM"] | ["MYQNd1noE","U161z0sQwqD","azwbYuFP8sH","M5V83xsrQGn","XK1MtUIWQfCEa1W","JHHx"]

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
> See Postgresql queries examples (here)[https://github.com/CS196Illinois/FA24-Group31/blob/master/Project/backend/java-server/app]

## private

CREATE and INSERT query examples can be found (here)[https://github.com/CS196Illinois/FA24-Group31/blob/master/Project/backend/java-server/app/populatePrivate.sql]

discord_id: String (primary key)

dob: Date (YYYY-MM-DD)

one_way_matched: Array\<String\>

two_way_matched: Array\<String\>

| discord_id | dob | one_way_matched | two_way_matched |
|:-:|:-:|:-:|:-:|
FDhibSs|1900-01-01|["n8UMm", "HA8mzS"]|["nu5cY4OaWdmfmziw3s", "VAnmg3joCnW0Qdr9R"]
HA8mzS|1980-05-02|["VAnmg3joCnW0Qdr9R"]|["n8UMm"]

# public

CREATE and INSERT query examples can be found (here)[https://github.com/CS196Illinois/FA24-Group31/blob/master/Project/backend/java-server/app/populatePublic.sql]

discord_id: String (primary key)

riot_id: String

first_name: String

last_name: String

pronouns: String

description: String

roles: Array\<String\>

rank: String

| discord_id | riot_id | first_name | last_name | pronouns | description | roles | rank |
|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|
ILLYpQKhuY3v|iUR6fvHAPmAqg#Tizc4"|John|Washington|any/any|Lorem ipsum dolor sit amet, consectetur adipiscing elit. |["top","jungle","middle","bottom","suppport"]|platinum|
mHZhK8TGIIfI|"d0VEfX5j#l9ade"|Bea|White|she/her|Lorem ipsum dolor sit amet, consectetur adipiscing elit. |["top","jungle","middle","bottom","suppport"]|bronze|
vi048PUuSftDY4kU|aeVJ2BzlLQlZ#S3u|Mary|White|any/any|Lorem ipsum dolor sit amet, consectetur adipiscing elit. |["top","jungle","middle"]|silver|

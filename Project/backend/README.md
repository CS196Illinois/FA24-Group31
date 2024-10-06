# DB

> [!NOTE]
> DB structure will probably change soon but I will update accordingly

> [!CAUTION]
> Do not delete *.db.bak

> [!TIP]
> If you accidentally corrupt the database while testing, do `cp main.db.bak main.db'

## Users

#### Table Structure

uuid: String (Primary Key)

riot_id: String

discord_id: String

first_name: String

last_name: String

one_way_matched: json_array

two_way_matched: json_array

| uuid | riot_id | discord_id | first_name | last_name | one_way_matched | two_way_matched |
|:-:|:-:|:-:|:-:|:-:|:-:|:-:|
| "a6db7a0-5755-45d2-a994-4d0a3614cfa3" | "0xSec#6969" | "yzk5" | "Raymond" | "Yang" | ["2ad9303f-4cea-43fe-bac4-e1fcec783a6b", "bf82051f-f586-41ea-ad55-d1968e21ba24", "f299ed0f-0c92-4ce3-bcc7-82a3f6c94924"] | ["9ddf2a72-59ee-4892-adfd-cd675a1eb4d6", "989a43f7-b6d4-41f9-8662-04b16c182c7b"]

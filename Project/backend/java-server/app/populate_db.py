# populates db with random data
# usage: python3 populate_db.py [# of rows]
# ignore the shit code
import sys
import os
import sqlite3
import random
import string

print("make sure sqlite3 package is installed")
print("usage: python3 populate_db.py -f [path to db file] -n [# of rows generate]")

# check cmd line args
if (len(sys.argv) != 5 or sys.argv[1].strip() != "-f" or not os.path.exists(sys.argv[2].strip()) or sys.argv[3].strip() != "-n" or not sys.argv[4].strip().isdigit() or int(sys.argv[4].strip()) <= 0):
    sys.exit(1)

db_path = sys.argv[2].strip()
con = sqlite3.connect(db_path)
cur = con.cursor()

for i in range(0, int(sys.argv[4].strip())):
    # generate random data
    discordID = "".join(random.choices(string.ascii_uppercase + string.ascii_lowercase + string.digits, k=random.randint(3, 18)))
    riotID = "".join(random.choices(string.ascii_uppercase + string.ascii_lowercase + string.digits, k=random.randint(3, 18))) + "#" + "".join(random.choices(string.ascii_uppercase + string.ascii_lowercase + string.digits, k=random.randint(3, 5)))
    firstName = ["Bob", "Rob", "Jane", "Mary", "Diana", "Joe", "Bill", "John", "George", "Lia", "Bea"]
    lastName = ["Doe", "Smith", "Black", "White", "Adams", "Washington", "Lincoln", "Williams"]
    dob = random.randrange(-1, 1299399910)
    pronouns = ["he/him", "she/her", "they/them", "he/them", "she/them", "any/any"]
    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
    roleOptions = ["'top'", "'jungle'", "'middle'", "'bottom'", "'suppport'"]
    n = 4
    for i in range(random.randint(0, 4)):
        roleOptions.pop(random.randint(0, n))
        n -= 1
    roleOptions = ",".join(roleOptions)
    rank = ["iron", "bronze", "silver", "gold", "platinum", "emerald", "diamond", "master", "grandmaster", "challenger"]
    one_way_matched = []
    two_way_matched = []
    for i in range(random.randint(1, 10)):
        one_way_matched.append("'" + "".join(random.choices(string.ascii_uppercase + string.ascii_lowercase + string.digits, k=random.randint(3, 18))) + "'")
        two_way_matched.append("'" + "".join(random.choices(string.ascii_uppercase + string.ascii_lowercase + string.digits, k=random.randint(3, 18))) + "'")
    one_way_matched = ",".join(one_way_matched)
    two_way_matched = ",".join(two_way_matched)

    # run query
    query = f"""
    INSERT INTO private (discord_id,  dob, one_way_matched, two_way_matched)
    VALUES(
        '{discordID}',
        '{dob}',
        json_array({one_way_matched}),
        json_array({two_way_matched})
    );
    INSERT INTO public (discord_id, first_name, last_name, pronouns, description, roles, rank)
    VALUES(
        '{discordID}',
        '{random.choice(firstName)}',
        '{random.choice(lastName)}',
        '{random.choice(pronouns)}',
        '{description}',
        json_array({roleOptions}),
        '{random.choice(rank)}',
        '{random.choice(rank)}',
        '{riotID}'
    );
    """
    print(f"\nQuery executed: \n{query}\n")
    try:
        cur.executescript(query)
        con.commit()
        print("Execution success")
    except Exception as e:
        print("Execution failed")
        print(f"Error: {e}")

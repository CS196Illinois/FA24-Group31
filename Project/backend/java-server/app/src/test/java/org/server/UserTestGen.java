package org.server;

import java.util.ArrayList;
import org.server.userops.User;

/**
 * This class contains the UserTestGen class which generates User objects for testing purposes.
 * @author adhit2
 */
public class UserTestGen {

    /**
     * This field is an {@link ArrayList} of {@link User} objects.
     */
    private ArrayList<User> users;

    /**
     * Constructs a new {@link UserTestGen} object with a new {@link ArrayList} of {@link User} objects.
     */
    public UserTestGen() {
        users.add(
            new User(
                "uuid1",
                "John",
                "Doe",
                "riot123",
                "discord123",
                new String[] { "uuid2", "uuid3" },
                new String[] { "uuid4", "uuid6" }
            )
        );
        users.add(
            new User(
                "uuid2",
                "Jane",
                "Doe",
                "riot456",
                "discord456",
                new String[] { "uuid3" },
                new String[] {}
            )
        );
        users.add(
            new User(
                "uuid3",
                "Real",
                "Person",
                "aatroxmain123",
                "ilovediscord",
                new String[] { "uuid5" },
                new String[] { "uuid4" }
            )
        );
        users.add(
            new User(
                "uuid4",
                "ilove",
                "league",
                "leagueplayer",
                "discord456",
                new String[] {},
                new String[] { "uuid3", "uuid1" }
            )
        );
        users.add(
            new User(
                "uuid5",
                "John",
                "Doe",
                "riot123",
                "discord123",
                new String[] {},
                new String[] {}
            )
        );
        users.add(
            new User(
                "uuid6",
                "Jane",
                "Doe",
                "riot456",
                "discord456",
                new String[] { "uuid5", "uuid4" },
                new String[] { "uuid1" }
            )
        );
    }

    /**
     * This method returns a {@link User} object from the {@link ArrayList} of {@link User} objects.
     * @param index the index of the {@link User} object in the {@link ArrayList} of {@link User} objects.
     * @return the {@link User} object at the specified index.
     */
    public User getUser(int index) {
        return users.get(index);
    }

    public int getNumUsers() {
        return users.size();
    }

    public boolean addUsers(User user) {
        return users.add(user);
    }
}

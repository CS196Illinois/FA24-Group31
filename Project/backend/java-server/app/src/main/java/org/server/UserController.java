package org.server;

import org.server.userops.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static final String DB_URL = "main.db";
    DBController db = new DBController(DB_URL);

    @PostMapping("/api/v1/_delete_user_")
    public void deleteUser(@PathVariable String uuid) {
        User user = db.getUser(uuid, true);
        boolean deleted = db.deleteUser(user);
        if (deleted) {
            System.out.println("Deleted user: " + uuid);
        }
    }

    @PostMapping("/api/v1/add_user")
    public void addUser(
        @PathVariable String uuid,
        @PathVariable String firstName,
        @PathVariable String lastName,
        @PathVariable String riotID,
        @PathVariable String discordID,
        @PathVariable String[] oneWayMatched,
        @PathVariable String[] twoWayMatched
    ) {
        User user = new User(
            uuid,
            firstName,
            lastName,
            riotID,
            discordID,
            oneWayMatched,
            twoWayMatched
        );
        db.addUser(user);
    }
}

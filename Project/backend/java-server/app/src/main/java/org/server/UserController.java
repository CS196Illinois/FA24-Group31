package org.server;

<<<<<<< HEAD
<<<<<<< HEAD
import org.server.userops.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static final String DB_URL = "main.db";

    DBController db = new DBController(DB_URL);

    @PostMapping("/api/v1/_delete_user_/{uuid}")
    public ResponseEntity<Object> deleteUser(@PathVariable String uuid) {
        User user = db.getUser(uuid, true);
        boolean deleted = db.deleteUser(user);
        if (deleted) {
            Object response = new Object() {
                public final String status = "{\"status\": \"deleted\"}";
            };
            return ResponseEntity.ok(response);
        } else {
            Object response = new Object() {
                public final String status = "{\"status\": \"failed\"}";
            };
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    public ResponseEntity<?> addUser(
        @RequestParam String uuid,
        @RequestParam String firstName,
        @RequestParam String lastName,
        @RequestParam String riotID,
        @RequestParam String discordID,
        @RequestParam String[] oneWayMatched,
        @RequestParam String[] twoWayMatched
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
        boolean added = db.addUser(user);
        if (added) {
            Object response = new Object() {
                public final String status = "{\"status\": \"created\"}";
            };
            return ResponseEntity.ok(response);
        } else {
            Object response = new Object() {
                public final String status = "{\"status\": \"failed\"}";
            };
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                response
            );
        }
    }
=======
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
>>>>>>> 1c908c5 (added delete function, I'm only like 75% sure it works so test it pretty pls)
=======
>>>>>>> f416794 (added add user pathway)
import org.springframework.web.bind.annotation.RestController;
import org.server.DBController;

import javax.swing.*;
import java.sql.*;

@RestController
public class UserController {
    private static final String DB_URL = "jdbc:sqlite:main.db";
<<<<<<< HEAD
<<<<<<< HEAD

=======
>>>>>>> 1c908c5 (added delete function, I'm only like 75% sure it works so test it pretty pls)
=======

>>>>>>> f416794 (added add user pathway)
    DBController db = new DBController(DB_URL);
    @PostMapping("/api/v1/_delete_user_")
    public void deleteUser(@PathVariable String uuid) {
        User user = db.getUser(uuid, true);
        boolean deleted = db.deleteUser(user);
        if (deleted) {
            System.out.println("Deleted user: " + uuid);
        }
    }
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> f416794 (added add user pathway)
    @PostMapping("/api/v1/add_user")
    public void addUser(@PathVariable String uuid, @PathVariable String firstName, @PathVariable String lastName, @PathVariable String riotID, @PathVariable String discordID, @PathVariable String[] oneWayMatched, @PathVariable String[] twoWayMatched) {
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

<<<<<<< HEAD
>>>>>>> 413c47f (removed random files and updated gitignore)
=======
>>>>>>> 1c908c5 (added delete function, I'm only like 75% sure it works so test it pretty pls)
=======
>>>>>>> f416794 (added add user pathway)
}

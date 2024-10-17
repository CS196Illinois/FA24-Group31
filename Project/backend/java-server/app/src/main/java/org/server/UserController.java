package org.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.server.DBController;

import javax.swing.*;
import java.sql.*;

@RestController
public class UserController {
    private static final String DB_URL = "jdbc:sqlite:main.db";

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
    @PostMapping("/api/v1/add_user")
    public ResponseEntity<?> addUser(@RequestParam String uuid, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String riotID, @RequestParam String discordID, @RequestParam String[] oneWayMatched, @RequestParam String[] twoWayMatched) {
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}

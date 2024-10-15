package org.server;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.server.DBController;

import javax.swing.*;
import java.sql.*;

@RestController
public class UserController {
    private static final String DB_URL = "jdbc:sqlite:main.db";
    DBController db = new DBController(DB_URL);
    @PostMapping("/api/v1/_delete_user_")
    public void deleteUser(@PathVariable String uuid) {
        User user = db.getUser(uuid, true);
        boolean deleted = db.deleteUser(user);
        if (deleted) {
            System.out.println("Deleted user: " + uuid);
        }
    }
}

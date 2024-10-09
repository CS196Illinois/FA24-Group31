package org.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;

public class DBController {

    /**
     * The path to the database.
     */
    private String dbPath;

    /**
     * Constructs a new {@link DBController} with the specified database path.
     * @param dbPath the path to the database
     */
    public DBController(String dbPath) {
        this.dbPath = dbPath;
    }

    /**
     * Gets the user with the specified parameter.
     * @param param the parameter to search for
     * @param isDiscord whether the parameter is a Discord ID or a UUID
     * @return the user with the specified parameter
     */
    public User getUser(String param, boolean isDiscord) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbPath);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery(
                "select * from users where " +
                (isDiscord ? "discord_id" : "uuid") +
                " = \"" +
                param +
                "\""
            );
            if (rs.next()) {
                return new User(
                    rs.getString("uuid"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("riot_id"),
                    rs.getString("discord_id"),
                    rs.getString("one_way_matched").split(","),
                    rs.getString("two_way_matched").split(",")
                );
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return null;
    }

    /**
     * Gets the user with the specified parameter.
     * @param param the parameter to search for
     * @param isDiscord whether the parameter is a Discord ID or a UUID
     * @return the user with the specified parameter
     */
    public void addUser(User user) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbPath);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate(
                "insert into users values (" +
                user.getIdentifier() +
                ", " +
                user.getFirstName() +
                ", " +
                user.getLastName() +
                ", " +
                user.getRiotID() +
                ", " +
                user.getDiscordID() +
                ", json_array(" +
                Arrays.toString(user.getOneWayMatched().toArray()) +
                "), json_array(" +
                Arrays.toString(user.getTwoWayMatched().toArray()) +
                "))"
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    /**
     * Tests the database connection.
     */
    public void testDB() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbPath);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * from users");
            while (rs.next()) {
                System.out.println("uuid = " + rs.getString("uuid"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
}

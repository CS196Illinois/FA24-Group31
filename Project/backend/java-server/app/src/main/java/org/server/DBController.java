package org.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBController {

    private String dbPath;

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

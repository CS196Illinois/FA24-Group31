package org.server;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;

/**
 * This class is responsible for controlling the PostgreSQL database. It can deal with user data.
 * {@code @Author} adhit2
 */
public class PostgreSQLController {
  private Properties props;
  private String url;

  public PostgreSQLController(String url, String user, String password) {
    props = new Properties();
    props.setProperty("user", user);
    props.setProperty("password", password);
    this.url = "jdbc:postgresql://" + url;
  }

  public PostgreSQLController() {
    Dotenv dotenv = Dotenv.load();
    props = new Properties();
    props.setProperty("user", dotenv.get("PGUSER"));
    props.setProperty("password", dotenv.get("PGPASSWORD"));
    this.url = "jdbc:postgresql://" + dotenv.get("PGURL");
  }

  public void createUser(String discordId, String riotId, String firstName, String lastName, String[] pronouns, String description, String[] roles, String rank, String image) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, props);
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);

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

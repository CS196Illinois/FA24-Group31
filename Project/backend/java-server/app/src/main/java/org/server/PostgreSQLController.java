package org.server;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

/**
 * This class is responsible for controlling the PostgreSQL database. It can deal with user data.
 * {@code @Author} adhit2
 */
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class PostgreSQLController {

  private Properties props;
  private String url;

  /**
   * Constructs a new {@link PostgreSQLController} with the specified url, user, and password.
   *
   * @param url the url of the PostgreSQL database
   * @param user the user of the PostgreSQL database
   * @param password the password of the PostgreSQL database
   */
  public PostgreSQLController(String url, String user, String password) {
    props = new Properties();
    props.setProperty("user", user);
    props.setProperty("password", password);
    this.url = "jdbc:postgresql://" + url;
  }

  /**
   * Constructs a new {@link PostgreSQLController} with the specified url, user, and password from
   * the ENV file.
   */
  public PostgreSQLController() {
    Dotenv dotenv = Dotenv.load();
    props = new Properties();
    props.setProperty("user", dotenv.get("PGUSER"));
    props.setProperty("password", dotenv.get("PGPASSWORD"));
    this.url = "jdbc:postgresql://" + dotenv.get("PGURL");
  }

  /**
   * Creates a new user in the database.
   *
   * @param discordId the Discord ID of the user
   * @param riotId the Riot ID of the user
   * @param firstName the first name of the user
   * @param lastName the last name of the user
   * @param pronouns the pronouns of the user
   * @param description the description of the user
   * @param roles the roles that the user plays
   */
  public void createUser(
      String discordId,
      String riotId,
      String firstName,
      String lastName,
      String[] pronouns,
      String description,
      String[] roles,
      String rank,
      String image,
      String dob) {
    Connection connection = null;
    String uuid = UUID.randomUUID().toString();
    try {
      connection = DriverManager.getConnection(url, props);
      connection.setAutoCommit(false);

      String sqlPrivate =
          "INSERT INTO private (discord_id, dob, one_way_matched, two_way_matched) VALUES (?, ?, '{}', '{}')";
      String sqlPublic =
          "INSERT INTO public (discord_id, riot_id, first_name, last_name, pronouns, description, roles, rank, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
      // String sqlAuth = "INSERT INTO auth (discord_id, session_token) VALUES (?, ?)";

      try (PreparedStatement stmtPrivate = connection.prepareStatement(sqlPrivate);
          PreparedStatement stmtPublic = connection.prepareStatement(sqlPublic);
          PreparedStatement stmtAuth = connection.prepareStatement(sqlAuth)) {

        stmtPrivate.setString(1, discordId);
        stmtPrivate.setDate(2, java.sql.Date.valueOf(dob));
        stmtPrivate.executeUpdate();

        stmtPublic.setString(1, discordId);
        stmtPublic.setString(2, riotId);
        stmtPublic.setString(3, firstName);
        stmtPublic.setString(4, lastName);
        stmtPublic.setArray(5, connection.createArrayOf("text", pronouns));
        stmtPublic.setString(6, description);
        stmtPublic.setArray(7, connection.createArrayOf("text", roles));
        stmtPublic.setString(8, rank);
        stmtPublic.setString(9, image);
        stmtPublic.executeUpdate();

        stmtAuth.setString(1, discordId);
        stmtAuth.setString(2, uuid);
        stmtAuth.executeUpdate();

        connection.commit();
      } catch (SQLException e) {
        connection.rollback();
        throw e;
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

  /** Tests the connection to the database. */
  public void testConnection() {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, props);
      System.out.println("Connection successful");
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

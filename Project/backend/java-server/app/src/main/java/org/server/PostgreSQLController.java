package org.server;

import io.github.cdimascio.dotenv.Dotenv;
import org.checkerframework.checker.units.qual.A;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * This class is responsible for controlling the PostgreSQL database. It can deal with user data.
 * {@code @Author} adhit2
 */
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class PostgreSQLController {

  private final Properties props;
  private final String url;
  private final DiscordOps discordOps = new DiscordOps();

  /**
   * Constructs a new {@link PostgreSQLController} with the specified url, user, and password from
   * the ENV file.
   */
  public PostgreSQLController() {
    Dotenv dotenv = Dotenv.load();
    props = new Properties();
    props.setProperty("user", dotenv.get("PGUSER"));
    props.setProperty("password", dotenv.get("PGPASSWORD"));
    this.url = "jdbc:postgresql://" + dotenv.get("PGURL") + "/main";
  }

  /**
   * Creates a new auth row in the database.
   *
   * @param discordID the discord ID of the user
   * @param authToken the auth token of the user
   * @param refreshToken the refresh token of the user
   * @return the session token of the user
   */
  public String createAuthRow(String discordID, String authToken, String refreshToken) {
    Connection connection = null;
    String uuid = UUID.randomUUID().toString();
    try {
      connection = DriverManager.getConnection(url, props);
      connection.setAutoCommit(false);
      String sqlAuth =
          "INSERT INTO auth "
              + "(discord_id, session_token, refresh_token, auth_token)"
              + " VALUES (?, ?, ?, ?)";
      try (PreparedStatement stmtAuth = connection.prepareStatement(sqlAuth)) {
        stmtAuth.setString(1, discordID);
        stmtAuth.setString(2, uuid);
        stmtAuth.setString(3, refreshToken);
        stmtAuth.setString(4, authToken);
        stmtAuth.executeUpdate();
        connection.commit();
        return uuid;
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
        System.err.println(e.getMessage());
      }
    }
    throw new IllegalArgumentException("User not found");
  }

  /**
   * Creates a new auth row in the database.
   *
   * @param user the user to create
   */
  public void createUser(User user) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, props);
      connection.setAutoCommit(false);

      String sqlPrivate =
          "INSERT INTO private "
              + " (discord_id, dob, one_way_matched, two_way_matched, seen)"
              + " VALUES (?, ?, '{}', '{}', '{}')";
      String sqlPublic =
          "INSERT INTO public"
              + " (discord_id, riot_id, first_name, last_name,"
              + " pronouns, description, roles, rank, image) "
              + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

      try (PreparedStatement stmtPrivate = connection.prepareStatement(sqlPrivate);
          PreparedStatement stmtPublic = connection.prepareStatement(sqlPublic)) {

        stmtPrivate.setString(1, user.getPublicUser().getDiscordId());
        stmtPrivate.setDate(2, Date.valueOf(user.getPrivateUser().getDoB()));
        stmtPrivate.executeUpdate();

        stmtPublic.setString(1, user.getPublicUser().getDiscordId());
        stmtPublic.setString(2, user.getPublicUser().getRiotId());
        stmtPublic.setString(3, user.getPublicUser().getFirstName());
        stmtPublic.setString(4, user.getPublicUser().getLastName());
        stmtPublic.setArray(
            5, connection.createArrayOf("text", user.getPublicUser().getPronouns()));
        stmtPublic.setString(6, user.getPublicUser().getDescription());
        stmtPublic.setArray(7, connection.createArrayOf("text", user.getPublicUser().getRoles()));
        stmtPublic.setString(8, user.getPublicUser().getRank());
        stmtPublic.setString(9, user.getPublicUser().getImage());
        stmtPublic.executeUpdate();

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
        System.err.println(e.getMessage());
      }
    }
  }

  /**
   * gets a user based on discord ID.
   *
   * @param discordID the discord ID of the user
   * @return the user
   */
  public User getUser(String discordID) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, props);
      connection.setAutoCommit(false);
      String sqlPrivate = "SELECT * FROM private WHERE discord_id = ?";
      String sqlPublic = "SELECT * FROM public WHERE discord_id = ?";
      try (PreparedStatement stmtPrivate = connection.prepareStatement(sqlPrivate);
          PreparedStatement stmtPublic = connection.prepareStatement(sqlPublic)) {
        stmtPrivate.setString(1, discordID);
        stmtPublic.setString(1, discordID);
        ResultSet priv = stmtPrivate.executeQuery();
        ResultSet pub = stmtPublic.executeQuery();
        if (priv.next() && pub.next()) {
          PrivateUser privateUser =
              new PrivateUser(
                  priv.getString("discord_id"),
                  priv.getDate("dob").toString(),
                  (String[]) priv.getArray("one_way_matched").getArray(),
                  (String[]) priv.getArray("two_way_matched").getArray(),
                  (String[]) priv.getArray("seen").getArray());
          PublicUser publicUser =
              new PublicUser(
                  pub.getString("discord_id"),
                  pub.getString("riot_id"),
                  pub.getString("first_name"),
                  pub.getString("last_name"),
                  (String[]) pub.getArray("pronouns").getArray(),
                  pub.getString("description"),
                  (String[]) pub.getArray("roles").getArray(),
                  pub.getString("rank"),
                  pub.getString("image"));
          return new User(privateUser, publicUser);
        }
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
        System.err.println(e.getMessage());
      }
    }
    throw new IllegalArgumentException("User not found");
  }

  public void resetSeen(String discordId) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, props);
      connection.setAutoCommit(false);
      String sqlPrivate = "UPDATE private SET seen = '{}' WHERE discord_id = ?";
      try (PreparedStatement stmtPrivate = connection.prepareStatement(sqlPrivate)) {
        stmtPrivate.setString(1, discordId);
        stmtPrivate.executeUpdate();
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
        System.err.println(e.getMessage());
      }
    }
  }

  public List<User> filterWithSQL(String sessionToken) throws SQLException {
    List<User> users = new ArrayList<>();
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, props);
      connection.setAutoCommit(false);

      // update to make sure that the user is not in seen
      String sql =
          "SELECT * FROM private JOIN public ON private.discord_id = "
              + "public.discord_id WHERE EXTRACT(YEAR FROM age(private.dob))"
              + " BETWEEN ? AND ? AND public.rank = ANY(?) AND public.roles && ? "
              + "AND private.discord_id NOT IN "
              + "(SELECT unnest(seen) FROM private WHERE discord_id = ?)";

      try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        UserPrefs prefs = getUserPreferences(sessionToken);
        stmt.setInt(1, prefs.getMinAge());
        stmt.setInt(2, prefs.getMaxAge());
        stmt.setArray(3, connection.createArrayOf("text", prefs.getRanks()));
        stmt.setArray(4, connection.createArrayOf("text", prefs.getRoles()));
        stmt.setString(5, getDiscordId(sessionToken));

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
          PrivateUser privateUser =
              new PrivateUser(
                  rs.getString("discord_id"),
                  rs.getDate("dob").toString(),
                  rs.getArray("one_way_matched") != null
                      ? (String[]) rs.getArray("one_way_matched").getArray()
                      : new String[0],
                  rs.getArray("two_way_matched") != null
                      ? (String[]) rs.getArray("two_way_matched").getArray()
                      : new String[0],
                  rs.getArray("seen") != null
                      ? (String[]) rs.getArray("seen").getArray()
                      : new String[0]);

          PublicUser publicUser =
              new PublicUser(
                  rs.getString("discord_id"),
                  rs.getString("riot_id"),
                  rs.getString("first_name"),
                  rs.getString("last_name"),
                  (String[]) rs.getArray("pronouns").getArray(),
                  rs.getString("description"),
                  (String[]) rs.getArray("roles").getArray(),
                  rs.getString("rank"),
                  rs.getString("image"));

          users.add(new User(privateUser, publicUser));
        }
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
        System.err.println(e.getMessage());
      }
    }
    return users;
  }

  public List<User> getUsers() throws SQLException {
    List<User> users = new ArrayList<>();
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, props);
      connection.setAutoCommit(false);
      String sqlPrivate = "SELECT * FROM private";
      String sqlPublic = "SELECT * FROM public";
      try (PreparedStatement stmtPrivate = connection.prepareStatement(sqlPrivate);
          PreparedStatement stmtPublic = connection.prepareStatement(sqlPublic)) {

        ResultSet priv = stmtPrivate.executeQuery();
        ResultSet pub = stmtPublic.executeQuery();

        while (priv.next() && pub.next()) {
          if (priv.getArray("one_way_matched") == null) {}
          PrivateUser privateUser =
              new PrivateUser(
                  priv.getString("discord_id"),
                  priv.getDate("dob").toString(),
                  priv.getArray("one_way_matched") != null
                      ? (String[]) priv.getArray("one_way_matched").getArray()
                      : new String[0],
                  priv.getArray("two_way_matched") != null
                      ? (String[]) priv.getArray("two_way_matched").getArray()
                      : new String[0],
                  priv.getArray("seen") != null
                      ? (String[]) priv.getArray("seen").getArray()
                      : new String[0]);

          PublicUser publicUser =
              new PublicUser(
                  pub.getString("discord_id"),
                  pub.getString("riot_id"),
                  pub.getString("first_name"),
                  pub.getString("last_name"),
                  (String[]) pub.getArray("pronouns").getArray(),
                  pub.getString("description"),
                  (String[]) pub.getArray("roles").getArray(),
                  pub.getString("rank"),
                  pub.getString("image"));

          users.add(new User(privateUser, publicUser));
        }
        if (!users.isEmpty()) {
          return users;
        }
        connection.commit();
      } catch (SQLException e) {
        System.err.println(e.getMessage());
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    } finally {
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        System.err.println(e.getMessage());
      }
    }
    throw new IllegalArgumentException("Error, users not found");
  }

  public void updateUserPreferences(UserPrefs prefs) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, props);
      connection.setAutoCommit(false);
      String sqlPrivate =
          "UPDATE user_preferences SET min_age = ?, max_age = ?, ranks = ?, roles = ? WHERE session_token = ?";
      try (PreparedStatement stmtPrivate = connection.prepareStatement(sqlPrivate)) {
        stmtPrivate.setInt(1, prefs.getMinAge());
        stmtPrivate.setInt(2, prefs.getMaxAge());
        stmtPrivate.setArray(3, connection.createArrayOf("text", prefs.getRanks()));
        stmtPrivate.setArray(4, connection.createArrayOf("text", prefs.getRoles()));
        stmtPrivate.setString(5, prefs.getSessionToken());
        int result = stmtPrivate.executeUpdate();
        if (result == 0) {
          String sqlInsert =
              "INSERT INTO user_preferences (session_token, min_age, max_age, ranks, roles) VALUES (?, ?, ?, ?, ?)";
          try (PreparedStatement stmtInsert = connection.prepareStatement(sqlInsert)) {
            stmtInsert.setString(1, prefs.getSessionToken());
            stmtInsert.setInt(2, prefs.getMinAge());
            stmtInsert.setInt(3, prefs.getMaxAge());
            stmtInsert.setArray(4, connection.createArrayOf("text", prefs.getRanks()));
            stmtInsert.setArray(5, connection.createArrayOf("text", prefs.getRoles()));
            stmtInsert.executeUpdate();
          } catch (SQLException e) {
            connection.rollback();
            throw e;
          }
        }
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
        System.err.println(e.getMessage());
      }
    }
  }

  /**
   * Gets an access token.
   *
   * @param discordID the discord ID of the user
   * @return the user's access token
   */
  public String getAccessToken(String discordID) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, props);
      connection.setAutoCommit(false);
      String sqlAuth = "SELECT auth_token FROM auth WHERE discord_id = ?";
      try (PreparedStatement stmtAuth = connection.prepareStatement(sqlAuth)) {
        stmtAuth.setString(1, discordID);
        ResultSet auth = stmtAuth.executeQuery();
        if (auth.next()) {
          return auth.getString("auth_token");
        }
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
        System.err.println(e.getMessage());
      }
    }
    throw new IllegalArgumentException("User not found");
  }

  /**
   * Updates the user in the database.
   *
   * @param discordID the discord ID of the user
   * @return the user
   */
  public String doesUserExist(String discordID) {
    // returns the session token of the user if they exist
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, props);
      connection.setAutoCommit(false);
      String sqlAuth = "SELECT session_token FROM auth WHERE discord_id = ?";
      try (PreparedStatement stmtAuth = connection.prepareStatement(sqlAuth)) {
        stmtAuth.setString(1, discordID);
        ResultSet auth = stmtAuth.executeQuery();
        if (auth.next()) {
          return auth.getString("session_token");
        }
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
        System.err.println(e.getMessage());
      }
    }
    return null;
  }

  /**
   * Updates the user in the database.
   *
   * @param discordId the discord ID of the user
   */
  public void deleteUser(String discordId) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, props);
      connection.setAutoCommit(false);

      // Remove the user from the matched arrays in the private table
      String updateMatchedArrays =
          "UPDATE private SET "
              + "one_way_matched = array_remove(one_way_matched, ?), "
              + "two_way_matched = array_remove(two_way_matched, ?) "
              + "WHERE ? = ANY(one_way_matched) OR ? = ANY(two_way_matched)";

      // Delete the user from the private, public, and auth tables
      String deletePrivate = "DELETE FROM private WHERE discord_id = ?";
      String deletePublic = "DELETE FROM public WHERE discord_id = ?";
      String deleteAuth = "DELETE FROM auth WHERE discord_id = ?";

      try (PreparedStatement stmtUpdate = connection.prepareStatement(updateMatchedArrays);
          PreparedStatement stmtDeletePrivate = connection.prepareStatement(deletePrivate);
          PreparedStatement stmtDeletePublic = connection.prepareStatement(deletePublic);
          PreparedStatement stmtDeleteAuth = connection.prepareStatement(deleteAuth)) {

        // Update matched arrays
        stmtUpdate.setString(1, discordId);
        stmtUpdate.setString(2, discordId);
        stmtUpdate.setString(3, discordId);
        stmtUpdate.setString(4, discordId);
        stmtUpdate.executeUpdate();

        // Delete user from private table
        stmtDeletePrivate.setString(1, discordId);
        stmtDeletePrivate.executeUpdate();

        // Delete user from public table
        stmtDeletePublic.setString(1, discordId);
        stmtDeletePublic.executeUpdate();

        // Delete user from auth table
        stmtDeleteAuth.setString(1, discordId);
        stmtDeleteAuth.executeUpdate();

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
        System.err.println(e.getMessage());
      }
    }
  }

  /**
   * Updates the user in the database.
   *
   * @param sessionToken the session token of the user
   * @return the discord ID of the user
   */
  public String getDiscordId(String sessionToken) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, props);
      connection.setAutoCommit(false);
      String sqlAuth = "SELECT discord_id FROM auth WHERE session_token = ?";
      try (PreparedStatement stmtAuth = connection.prepareStatement(sqlAuth)) {
        stmtAuth.setString(1, sessionToken);
        ResultSet auth = stmtAuth.executeQuery();
        if (auth.next()) {
          return auth.getString(1);
        }
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
        System.err.println(e.getMessage());
      }
    }
    throw new IllegalArgumentException("Invalid Session Token");
  }

  public UserPrefs getUserPreferences(String sessionToken) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, props);
      connection.setAutoCommit(false);
      String sqlPrivate = "SELECT * FROM user_preferences WHERE session_token = ?";
      try (PreparedStatement stmtPrivate = connection.prepareStatement(sqlPrivate)) {
        stmtPrivate.setString(1, sessionToken);
        ResultSet priv = stmtPrivate.executeQuery();
        if (priv.next()) {
          return new UserPrefs(
              getDiscordId(sessionToken),
              priv.getInt("min_age"),
              priv.getInt("max_age"),
              (String[]) priv.getArray("ranks").getArray(),
              (String[]) priv.getArray("roles").getArray(),
              priv.getString("session_token"));
        }
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
        System.err.println(e.getMessage());
      }
    }
    throw new IllegalArgumentException("User not found");
  }

  public void setRefreshToken(String discordId, String refreshToken) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, props);
      connection.setAutoCommit(false);
      String sqlAuth = "UPDATE auth SET refresh_token = ? WHERE discord_id = ?";
      try (PreparedStatement stmtAuth = connection.prepareStatement(sqlAuth)) {
        stmtAuth.setString(1, refreshToken);
        stmtAuth.setString(2, discordId);
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
        System.err.println(e.getMessage());
      }
    }
  }

  /**
   * Updates the user in the database.
   *
   * @param discordId the discord ID of the user
   * @return the user's refresh token
   */
  public String getRefreshToken(String discordId) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, props);
      connection.setAutoCommit(false);
      String sqlAuth = "SELECT refresh_token FROM auth WHERE discord_id = ?";
      try (PreparedStatement stmtAuth = connection.prepareStatement(sqlAuth)) {
        stmtAuth.setString(1, discordId);
        ResultSet auth = stmtAuth.executeQuery();
        if (auth.next()) {
          return auth.getString(1);
        }
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
        System.err.println(e.getMessage());
      }
    }
    throw new IllegalArgumentException("User not found");
  }

  /**
   * updates the auth token for given discord ID.
   *
   * @param discordId the discord ID of the user
   */
  public void changeAuthToken(String authToken, String discordId) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url);
      connection.setAutoCommit(false);
      String sqlAuth = "UPDATE auth SET auth_token = ? WHERE discord_id = ?";
      try (PreparedStatement stmtAuth = connection.prepareStatement(sqlAuth)) {
        stmtAuth.setString(1, authToken);
        stmtAuth.setString(2, discordId);
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
        System.err.println(e.getMessage());
      }
    }
  }

  public boolean hasUserBeenCreated(String discordID) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, props);
      connection.setAutoCommit(false);
      String sqlPrivate = "SELECT * FROM private WHERE discord_id = ?";
      String sqlPublic = "SELECT * FROM public WHERE discord_id = ?";
      try (PreparedStatement stmtPrivate = connection.prepareStatement(sqlPrivate);
          PreparedStatement stmtPublic = connection.prepareStatement(sqlPublic)) {
        stmtPrivate.setString(1, discordID);
        stmtPublic.setString(1, discordID);
        ResultSet priv = stmtPrivate.executeQuery();
        ResultSet pub = stmtPublic.executeQuery();
        if (priv.next() && pub.next()) {
          return true;
        }
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
        System.err.println(e.getMessage());
      }
    }
    return false;
  }

  public void addUserToSeen(String discordId, String seenUser) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, props);
      connection.setAutoCommit(false);
      String sqlPrivate = "UPDATE private SET seen = array_append(seen, ?) WHERE discord_id = ?";
      try (PreparedStatement stmtPrivate = connection.prepareStatement(sqlPrivate)) {
        stmtPrivate.setString(1, seenUser);
        stmtPrivate.setString(2, discordId);
        stmtPrivate.executeUpdate();
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
        System.err.println(e.getMessage());
      }
    }
  }

  /** Tests the connection to the database. */
  public boolean testConnection() {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, props);
      if (connection != null) {
        connection.close();
      }
      return true;
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      return false;
    } finally {
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        System.err.println(e.getMessage());
      }
    }
  }
}

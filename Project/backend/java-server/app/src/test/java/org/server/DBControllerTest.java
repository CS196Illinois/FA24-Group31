package org.server;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import org.junit.jupiter.api.*;
import org.server.userops.User;

public class DBControllerTest {

    private static final String DB_URL = "main.db";
    private DBController dbController;

    @BeforeEach
    void setUp() throws SQLException {
        dbController = new DBController(DB_URL);
    }

    @Test
    void testAddUser() {
        User user = new User(
            "uuid1",
            "John",
            "Doe",
            "riot123",
            "discord123",
            new String[] { "sdlkfj", "sldkfjsdlkfj" },
            new String[] { "sldfjsdlkfj", "slkdfjlskddd" }
        );
        dbController.addUser(user);
        dbController.testDB();
        User retrievedUser = dbController.getUser("uuid1", false);
        assertNotNull(retrievedUser);
        assertEquals("uuid1", retrievedUser.getIdentifier());
        assertEquals("John", retrievedUser.getFirstName());
        assertEquals("Doe", retrievedUser.getLastName());
        assertEquals("riot123", retrievedUser.getRiotID());
        assertEquals("discord123", retrievedUser.getDiscordID());
        assertEquals(user.getOneWayMatched(), retrievedUser.getOneWayMatched());
        assertEquals(user.getTwoWayMatched(), retrievedUser.getTwoWayMatched());
    }

    @Test
    void testGetUserByUUID() {
        User user = new User(
            "uuid2",
            "Jane",
            "Doe",
            "riot456",
            "discord456",
            new String[] { "ssldfjlskdf", "sldkfjlskfjd" },
            new String[] { "ssldfjlskdf", "sldkfjlskfjd" }
        );
        dbController.addUser(user);

        User retrievedUser = dbController.getUser("uuid2", false);
        assertNotNull(retrievedUser);
        assertEquals("uuid2", retrievedUser.getIdentifier());
        assertEquals("Jane", retrievedUser.getFirstName());
        assertEquals("Doe", retrievedUser.getLastName());
        assertEquals("riot456", retrievedUser.getRiotID());
        assertEquals("discord456", retrievedUser.getDiscordID());
        assertEquals(user.getOneWayMatched(), retrievedUser.getOneWayMatched());
        assertEquals(user.getTwoWayMatched(), retrievedUser.getTwoWayMatched());
    }

    @Test
    void testGetUserByDiscordID() {
        User user = new User(
            "uuid3",
            "Alice",
            "Smith",
            "riot789",
            "discord789",
            new String[] { "ssldfjlskdf", "sldkfjlskfjd" },
            new String[] { "ssldfjlskdf", "sldkfjlskfjd" }
        );
        dbController.addUser(user);

        User retrievedUser = dbController.getUser("discord789", true);
        assertNotNull(retrievedUser);
        assertEquals("uuid3", retrievedUser.getIdentifier());
        assertEquals("Alice", retrievedUser.getFirstName());
        assertEquals("Smith", retrievedUser.getLastName());
        assertEquals("riot789", retrievedUser.getRiotID());
        assertEquals("discord789", retrievedUser.getDiscordID());
    }

    @Test
    void testDBConnection() {
        assertDoesNotThrow(() -> dbController.testDB());
    }

    @Test
    void testUserNotFound() {
        assertThrows(NullPointerException.class, () ->
            dbController.getUser("nonexistent", false)
        );
    }

    @Test
    void testDeleteUser() {
        User user = new User(
                "uuid4",
                "V",
                "R",
                "riot1",
                "discord1",
                new String[] { "ssldfjlskdf", "sldkfjlskfjd" },
                new String[] { "ssldfjlskdf", "sldkfjlskfjd" }
        );
        dbController.addUser(user);
        dbController.deleteUser(user);
        System.out.println(user.getIdentifier());
    }

    @AfterAll
    static void tearDown() throws SQLException {
        try (
            Connection connection = DriverManager.getConnection(
                "jdbc:sqlite:" + DB_URL
            )
        ) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                "DELETE FROM users where discord_id like 'discord%'"
            );
        }
    }
}

package org.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        String[] oneWayMatched = { "user2", "user3" };
        String[] twoWayMatched = { "user4" };
        user = new User(
            "user1",
            "John",
            "Doe",
            "riot123",
            "discord123",
            oneWayMatched,
            twoWayMatched
        );
    }

    @Test
    public void testConstructor() {
        assertEquals("user1", user.getIdentifier());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("riot123", user.getRiotID());
        assertEquals("discord123", user.getDiscordID());
        assertTrue(user.getOneWayMatched().contains("user2"));
        assertTrue(user.getOneWayMatched().contains("user3"));
        assertTrue(user.getTwoWayMatched().contains("user4"));
    }

    @Test
    public void testSettersAndGetters() {
        user.setIdentifier("user5");
        assertEquals("user5", user.getIdentifier());

        user.setFirstName("Jane");
        assertEquals("Jane", user.getFirstName());

        user.setLastName("Smith");
        assertEquals("Smith", user.getLastName());

        user.setRiotID("riot456");
        assertEquals("riot456", user.getRiotID());

        user.setDiscordID("discord456");
        assertEquals("discord456", user.getDiscordID());
    }

    @Test
    public void testAddOneWayMatched() {
        assertTrue(user.addOneWayMatched("user5"));
        assertTrue(user.getOneWayMatched().contains("user5"));
        assertFalse(user.addOneWayMatched("user5")); // Adding the same user again should return false
    }

    @Test
    public void testDeleteOneWayMatched() {
        assertTrue(user.deleteOneWayMatched("user2"));
        assertFalse(user.getOneWayMatched().contains("user2"));
        assertFalse(user.deleteOneWayMatched("user2")); // Deleting a non-existent user should return false
    }

    @Test
    public void testIsOneWayMatched() {
        assertTrue(user.isOneWayMatched("user2"));
        assertFalse(user.isOneWayMatched("user5"));
    }

    @Test
    public void testMoveToTwoWayMatched() {
        assertTrue(user.moveToTwoWayMatched("user2"));
        assertFalse(user.getOneWayMatched().contains("user2"));
        assertTrue(user.getTwoWayMatched().contains("user2"));

        assertFalse(user.moveToTwoWayMatched("user5")); // Moving a non-existent user should return false
    }

    @Test
    public void testDeleteTwoWayMatched() {
        assertTrue(user.deleteTwoWayMatched("user4"));
        assertFalse(user.getTwoWayMatched().contains("user4"));
        assertFalse(user.deleteTwoWayMatched("user4")); // Deleting a non-existent user should return false
    }

    @Test
    public void testIsTwoWayMatched() {
        assertTrue(user.isTwoWayMatched("user4"));
        assertFalse(user.isTwoWayMatched("user5"));
    }

    @Test
    public void testToString() {
        String expected =
            "User{identifier='user1', firstName='John', lastName='Doe', riotID='riot123', discordID='discord123', oneWayMatched=[user2, user3], twoWayMatched=[user4]}";
        assertEquals(expected, user.toString());
    }

    @Test
    public void testNullValuesInConstructor() {
        String[] oneWayMatched = null;
        String[] twoWayMatched = null;
        User nullUser = new User(
            null,
            null,
            null,
            null,
            null,
            oneWayMatched,
            twoWayMatched
        );

        assertTrue(nullUser.getIdentifier().isEmpty());
        assertTrue(nullUser.getFirstName().isEmpty());
        assertTrue(nullUser.getLastName().isEmpty());
        assertTrue(nullUser.getRiotID().isEmpty());
        assertTrue(nullUser.getDiscordID().isEmpty());
        assertTrue(nullUser.getOneWayMatched().isEmpty());
        assertTrue(nullUser.getTwoWayMatched().isEmpty());
    }

    @Test
    public void testEmptyStringsInConstructor() {
        String[] oneWayMatched = {};
        String[] twoWayMatched = {};
        User emptyUser = new User(
            "",
            "",
            "",
            "",
            "",
            oneWayMatched,
            twoWayMatched
        );

        assertEquals("", emptyUser.getIdentifier());
        assertEquals("", emptyUser.getFirstName());
        assertEquals("", emptyUser.getLastName());
        assertEquals("", emptyUser.getRiotID());
        assertEquals("", emptyUser.getDiscordID());
        assertTrue(emptyUser.getOneWayMatched().isEmpty());
        assertTrue(emptyUser.getTwoWayMatched().isEmpty());
    }

    @Test
    public void testNullValuesInSetters() {
        user.setIdentifier(null);
        assertTrue(user.getIdentifier().isEmpty());

        user.setFirstName(null);
        assertTrue(user.getFirstName().isEmpty());

        user.setLastName(null);
        assertTrue(user.getLastName().isEmpty());

        user.setRiotID(null);
        assertTrue(user.getRiotID().isEmpty());

        user.setDiscordID(null);
        assertTrue(user.getDiscordID().isEmpty());
    }

    @Test
    public void testEmptyStringsInSetters() {
        user.setIdentifier("");
        assertEquals("", user.getIdentifier());

        user.setFirstName("");
        assertEquals("", user.getFirstName());

        user.setLastName("");
        assertEquals("", user.getLastName());

        user.setRiotID("");
        assertEquals("", user.getRiotID());

        user.setDiscordID("");
        assertEquals("", user.getDiscordID());
    }

    @Test
    public void testAddNullToOneWayMatched() {
        assertFalse(user.addOneWayMatched(null));
    }

    @Test
    public void testAddEmptyStringToOneWayMatched() {
        assertTrue(user.addOneWayMatched(""));
        assertTrue(user.getOneWayMatched().contains(""));
    }

    @Test
    public void testMoveNullToTwoWayMatched() {
        assertFalse(user.moveToTwoWayMatched(null));
    }

    @Test
    public void testMoveEmptyStringToTwoWayMatched() {
        user.addOneWayMatched("");
        assertTrue(user.moveToTwoWayMatched(""));
        assertTrue(user.getTwoWayMatched().contains(""));
    }
}

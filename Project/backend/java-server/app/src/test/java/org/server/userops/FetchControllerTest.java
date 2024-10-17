package org.server.userops;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.server.DBController;
import org.springframework.http.ResponseEntity;

public class FetchControllerTest {

    @Mock
    private DBController dbController;

    @InjectMocks
    private FetchController fetchController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchByUuid_UserExists() {}

    @Test
    public void testFetchByUuid_UserNotFound() {
        String uuid = "non-existent-uuid";
        when(dbController.getUser(uuid, false)).thenThrow(
            new NullPointerException("User not found")
        );

        ResponseEntity<JsonObject> response = fetchController.fetch_by_uuid(
            uuid
        );

        assertEquals(ResponseEntity.notFound().build(), response);
        verify(dbController, times(1)).getUser(uuid, false);
    }

    @Test
    public void testFetchByDiscordId_UserExists() {
        String discordId = "test-discord-id";
        JsonObject userJson = new JsonObject();
        userJson.addProperty("discord_id", discordId);
        userJson.addProperty("first_name", "Jane");
        userJson.addProperty("last_name", "Doe");

        User user = mock(User.class);
        when(user.toJson()).thenReturn(userJson);
        when(dbController.getUser(discordId, true)).thenReturn(user);

        ResponseEntity<JsonObject> response =
            fetchController.fetch_by_discordid(discordId);

        assertEquals(ResponseEntity.ok(userJson), response);
        verify(dbController, times(1)).getUser(discordId, true);
    }

    @Test
    public void testFetchByDiscordId_UserNotFound() {
        String discordId = "non-existent-discord-id";
        when(dbController.getUser(discordId, true)).thenThrow(
            new NullPointerException("User not found")
        );

        ResponseEntity<JsonObject> response =
            fetchController.fetch_by_discordid(discordId);

        assertEquals(ResponseEntity.notFound().build(), response);
        verify(dbController, times(1)).getUser(discordId, true);
    }
}

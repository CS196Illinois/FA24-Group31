package org.server.userops;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.server.DBController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FetchController.class)
@DisabledInAotMode
public class FetchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DBController dbController;

    @InjectMocks
    private FetchController fetchController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchByUuid_Success() throws Exception {
        JsonObject userJson = new JsonObject();
        userJson.addProperty("uuid", "12345");
        userJson.addProperty("first_name", "John");
        userJson.addProperty("last_name", "Doe");
        userJson.addProperty("riot_id", "riot123");
        userJson.addProperty("discord_id", "discord123");
        JsonArray oneWayMatched = new JsonArray();
        userJson.add("one_way_matched", oneWayMatched);
        JsonArray twoWayMatched = new JsonArray();
        userJson.add("two_way_matched", twoWayMatched);

        User user = new User(
            "12345",
            "John",
            "Doe",
            "riot123",
            "discord123",
            new String[] {},
            new String[] {}
        );
        when(
            dbController.getUser(anyString(), anyString().equals("false"))
        ).thenReturn(user);

        mockMvc
            .perform(
                get("/api/v1/fetch_by_uuid/12345").contentType(
                    MediaType.APPLICATION_JSON
                )
            )
            .andExpect(content().json(userJson.toString()));
        System.out.println(userJson.toString());
    }

    @Test
    public void testFetchByUuid_NotFound() throws Exception {
        when(
            dbController.getUser(anyString(), anyString().equals("false"))
        ).thenThrow(new NullPointerException("User not found"));

        mockMvc
            .perform(
                get("/api/v1/fetch_by_uuid/12345").contentType(
                    MediaType.APPLICATION_JSON
                )
            )
            .andExpect(status().isNotFound());
    }

    @Test
    public void testFetchByDiscordId_Success() throws Exception {
        JsonObject userJson = new JsonObject();
        userJson.addProperty("uuid", "12345");
        userJson.addProperty("first_name", "John");
        userJson.addProperty("last_name", "Doe");
        userJson.addProperty("riot_id", "riot123");
        userJson.addProperty("discord_id", "discord123");

        User user = new User(
            "12345",
            "John",
            "Doe",
            "riot123",
            "discord123",
            new String[] { "sdfhsd" },
            new String[] { "sdsdklfsd" }
        );
        when(
            dbController.getUser(anyString(), anyString().equals("true"))
        ).thenReturn(user);

        mockMvc
            .perform(
                get("/api/v1/fetch_by_discordid/discord123").contentType(
                    MediaType.APPLICATION_JSON
                )
            )
            .andExpect(status().isOk())
            .andExpect(content().json(userJson.toString()));
    }

    @Test
    public void testFetchByDiscordId_NotFound() throws Exception {
        when(
            dbController.getUser(anyString(), anyString().equals("true"))
        ).thenThrow(new NullPointerException("User not found"));

        mockMvc
            .perform(
                get("/api/v1/fetch_by_discordid/discord123").contentType(
                    MediaType.APPLICATION_JSON
                )
            )
            .andExpect(status().isNotFound());
    }
}

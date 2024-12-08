package org.server;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import com.google.gson.JsonPrimitive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/** This class represents the routes for the profile. {@code @Author} adhit2 */
@RestController
public class ProfileRoutes {

  /**
   * Gets the username of the user.
   *
   * @param sessionToken the session token
   * @return the response entity containing the username
   * @throws IOException when there is a bad thing
   * @throws ExecutionException when there is a bad thing
   * @throws InterruptedException when there is a bad thing
   */
  @PostMapping("/api/v1/username")
  public ResponseEntity<JsonObject> getUsername(@RequestBody String sessionToken)
      throws IOException, ExecutionException, InterruptedException {
    JsonObject jsonObject = JsonParser.parseString(sessionToken).getAsJsonObject();
    sessionToken = jsonObject.get("session_token").getAsString();
    PostgreSQLController pgController = new PostgreSQLController();
    DiscordOps discordOps = new DiscordOps();
    String discordUsername = discordOps.getUsername(pgController.getDiscordId(sessionToken));
    Logger.getGlobal().info("Username: " + discordUsername);
    JsonObject json = new JsonObject();
    json.addProperty("discord_username", discordUsername);
    return new ResponseEntity<>(json, HttpStatus.OK);
  }
}

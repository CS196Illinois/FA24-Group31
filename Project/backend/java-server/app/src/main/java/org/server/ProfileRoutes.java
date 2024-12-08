package org.server;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

  /**
   * Creates a user.
   *
   * @param jsonObject the JSON object
   * @return the response entity containing whether the user was created
   */
  @PostMapping("/api/v1/create_user")
  public ResponseEntity<Boolean> createUser(@RequestBody JsonObject jsonObject) {
    PostgreSQLController pgController = new PostgreSQLController();
    String sessionToken = jsonObject.get("session_token").getAsString();
    String discordId;
    try {
      discordId = pgController.getDiscordId(sessionToken);
    } catch (Exception e) {
      return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }
    if (pgController.hasUserBeenCreated(discordId)) {
      return new ResponseEntity<>(false, HttpStatus.CONFLICT);
    }
    PublicUser publicUser =
        new PublicUser(
            discordId,
            jsonObject.get("riot_id").getAsString(),
            jsonObject.get("first_name").getAsString(),
            jsonObject.get("last_name").getAsString(),
            toArray(jsonObject.get("pronouns").getAsJsonArray()),
            jsonObject.get("description").getAsString(),
            toArray(jsonObject.get("roles").getAsJsonArray()),
            jsonObject.get("rank").getAsString(),
            jsonObject.get("image").getAsString());
    PrivateUser privateUser =
        new PrivateUser(
            discordId,
            jsonObject.get("dob").getAsString(),
            new String[0],
            new String[0],
            new String[0]);
    User user = new User(privateUser, publicUser);
    pgController.createUser(user);
    return new ResponseEntity<>(true, HttpStatus.CREATED);
  }

  @GetMapping("/api/v1/me")
  public ResponseEntity<User> me(@RequestBody JsonObject sessionToken) {
    PostgreSQLController pgController = new PostgreSQLController();
    String discordId = pgController.getDiscordId(sessionToken.get("session_token").getAsString());
    User user = pgController.getUser(discordId);
    ResponseEntity<User> value = new ResponseEntity<>(user, HttpStatus.OK);
    Logger.getGlobal().info("User: " + user);
    return value;
  }

  private String[] toArray(JsonArray jsonArray) {
    String[] array = new String[jsonArray.size()];
    for (int i = 0; i < jsonArray.size(); i++) {
      array[i] = jsonArray.get(i).getAsString();
    }
    return array;
  }
}

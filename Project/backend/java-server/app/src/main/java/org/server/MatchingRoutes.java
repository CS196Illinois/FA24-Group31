package org.server;

import com.google.gson.JsonObject;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class MatchingRoutes {

  public static class UserOutput {
    private String name;
    private String image;
    private int age;
    private String[] roles;
    private String rank;
    private String bio;
    private String[] pronouns;
    private String discordId;
    private String riotId;

    private UserOutput(User user) throws IOException, ExecutionException, InterruptedException {
      name = user.getPublicUser().getFirstName() + " " + user.getPublicUser().getLastName();
      image = user.getPublicUser().getImage();
      age = user.getPrivateUser().getAge();
      roles = user.getPublicUser().getRoles();
      rank = user.getPublicUser().getRank();
      bio = user.getPublicUser().getDescription();
      pronouns = user.getPublicUser().getPronouns();
      DiscordOps discordOps = new DiscordOps();
      discordId = discordOps.getUsername(user.getPublicUser().getDiscordId());
      riotId = user.getPublicUser().getRiotId();
    }
  }

  @PostMapping(path = "/api/v1/update_prefs")
  public ResponseEntity<List<User>> matching(@RequestBody UserPrefs userPrefs) {
    PostgreSQLController pgController = new PostgreSQLController();
    String discordId = pgController.getDiscordId(userPrefs.getSessionToken());
    if (pgController.hasUserBeenCreated(discordId)) {
      try {
        pgController.updateUserPreferences(userPrefs);
      } catch (Exception e) {
        return ResponseEntity.badRequest().build();
      }
    }
    return ResponseEntity.ok().build();
  }

  @PostMapping(path = "/api/v1/next_user")
  public ResponseEntity<UserOutput> nextUser(@RequestBody JsonObject token) {
    PostgreSQLController pgController = new PostgreSQLController();
    String sessionToken = token.get("session_token").getAsString();
    String discordId = pgController.getDiscordId(sessionToken);
    if (pgController.hasUserBeenCreated(discordId)) {
      try {
        Matching matching = new Matching(sessionToken);
        matching.newFillMatchList();
        List<User> matchList = matching.getMatchList();
        if (matchList.isEmpty()) {
          pgController.resetSeen(discordId);
          matching.newFillMatchList();
          matchList = matching.getMatchList();
          if (matchList.isEmpty()) {
            return ResponseEntity.notFound().build();
          }
          return ResponseEntity.ok(new UserOutput(matchList.getFirst()));
        }
        pgController.addUserToSeen(discordId, matchList.getFirst().getPrivateUser().getDiscordId());
        return ResponseEntity.ok(new UserOutput(matchList.getFirst()));
      } catch (Exception e) {
        return ResponseEntity.badRequest().build();
      }
    }
    return ResponseEntity.notFound().build();
  }
}

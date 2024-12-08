package org.server;

import com.google.gson.JsonObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
            return ResponseEntity.ok().build();
          }
          Random rand = new Random();
          return ResponseEntity.ok(new UserOutput(matchList.get(rand.nextInt(matchList.size()))));
        }
        pgController.addUserToSeen(discordId, matchList.getFirst().getPrivateUser().getDiscordId());
        Random rand = new Random();
        return ResponseEntity.ok(new UserOutput(matchList.get(rand.nextInt(matchList.size()))));
      } catch (Exception e) {
        return ResponseEntity.badRequest().build();
      }
    }
    return ResponseEntity.notFound().build();
  }

  public class MatchResponse {
    private String name;
    private String discordId;

    public MatchResponse(String name, String discordId) {
      this.name = name;
      this.discordId = discordId;
    }

    public String getDiscordId() {
      return discordId;
    }

    public void setDiscordId(String discordId) {
      this.discordId = discordId;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  @PostMapping(path = "/api/v1/get_matches")
  public ResponseEntity<List<MatchResponse>> getMatches(@RequestBody JsonObject token) {
    PostgreSQLController pgController = new PostgreSQLController();
    String sessionToken = token.get("session_token").getAsString();
    String discordId = pgController.getDiscordId(sessionToken);
    if (pgController.hasUserBeenCreated(discordId)) {
      try {
        List<String> outputList = pgController.getTwoWayMatched(discordId);
        List<MatchResponse> outputFinal = new ArrayList<>();
        DiscordOps discordOps = new DiscordOps();
        for (String user : outputList) {
          outputFinal.add(
              new MatchResponse(
                  pgController.getUser(user).getPublicUser().getFirstName()
                      + " "
                      + pgController.getUser(user).getPublicUser().getLastName(),
                  discordOps.getUsername(user)));
        }
        return ResponseEntity.ok(outputFinal);
      } catch (Exception e) {
        return ResponseEntity.badRequest().build();
      }
    }
    return ResponseEntity.notFound().build();
  }
}

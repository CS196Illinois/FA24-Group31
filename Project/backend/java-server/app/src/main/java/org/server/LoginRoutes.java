package org.server;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/** This class represents the routes for login. {@code @Author} adhit2 */
@RestController
public class LoginRoutes {

  /** This class represents the return value for the login route. {@code @Author} adhit2 */
  public static class ReturnValue {
    private boolean success;
    private String sessionToken;

    /**
     * Constructs a new {@link ReturnValue} with the specified success and session token.
     *
     * @param success the success
     * @param sessionToken the session token
     */
    public ReturnValue(boolean success, String sessionToken) {
      this.success = success;
      this.sessionToken = sessionToken;
    }

    public boolean isSuccess() {
      return success;
    }

    public void setSuccess(boolean success) {
      this.success = success;
    }

    public String getSessionToken() {
      return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
      this.sessionToken = sessionToken;
    }
  }

  /**
   * Logs in the user.
   *
   * @param code the access token
   * @return the response entity containing whether the login was successful and the session token
   */
  @PostMapping(path = "/api/v1/login")
  public ResponseEntity<ReturnValue> login(@RequestBody String code)
      throws IOException, ExecutionException, InterruptedException {
    PostgreSQLController pgController = new PostgreSQLController();
    DiscordOps discordOps = new DiscordOps();
    JsonObject json = JsonParser.parseString(code).getAsJsonObject();
    code = json.get("code").getAsString();
    OAuth2AccessToken tr = discordOps.getTokens(code);
    String discordId = discordOps.getDiscordId(tr.getAccessToken());
    String sessionToken = pgController.doesUserExist(discordId);
    if (sessionToken != null) {
      return ResponseEntity.ok(new ReturnValue(true, sessionToken));
    } else {
      return ResponseEntity.ok(
          new ReturnValue(
              false,
              pgController.createAuthRow(discordId, tr.getAccessToken(), tr.getRefreshToken())));
    }
  }

  public static class matchUpdate {
    private String sessionToken;
    private String matchedId;

    public matchUpdate(String sessionToken, String matchedId) {
      this.sessionToken = sessionToken;
      this.matchedId = matchedId;
    }

    public String getMatchedId() {
      return matchedId;
    }

    public void setMatchedId(String matchedId) {
      this.matchedId = matchedId;
    }

    public String getSessionToken() {
      return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
      this.sessionToken = sessionToken;
    }
  }

  @PostMapping(path = "/api/v1/update_matches")
  public ResponseEntity<Boolean> updateMatches(@RequestBody matchUpdate match) {
    OneAndTwoWayMatches oneAndTwoWayMatches = new OneAndTwoWayMatches();
    PostgreSQLController pgController = new PostgreSQLController();
    boolean success =
        oneAndTwoWayMatches.updateMatches(
            pgController.getDiscordId(match.getSessionToken()),
            pgController.getDiscordIdfromRiot(match.getMatchedId()));
    return ResponseEntity.ok(success);
  }
}

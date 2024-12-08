package org.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.scribejava.apis.DiscordApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.*;
import com.github.scribejava.core.oauth.OAuth20Service;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import org.springframework.context.annotation.Configuration;

/** This class represents the Discord operations. {@code @Author} adhit2 */
@Configuration
public class DiscordOps {

  private static final PostgreSQLController pgController = new PostgreSQLController();
  private final OAuth20Service service =
      new ServiceBuilder(Dotenv.load().get("DISCORD_CLIENT_ID"))
          .apiSecret(Dotenv.load().get("DISCORD_CLIENT_SECRET"))
          .callback(Dotenv.load().get("DISCORD_REDIRECT_URI"))
          .userAgent("ScribeJava")
          .build(DiscordApi.instance());

  private static final String DISCORD_API_URL = "https://discord.com/api/v10";
  private static final String PROTECTED_RESOURCE_URL = "https://discord.com/api/users/@me";

  /** Constructs a new {@link DiscordOps}. */
  public DiscordOps() {}

  /** This class represents the Discord user. */
  public static class DiscordUser {
    private String id;
    private String username;
    private String discriminator;

    @JsonProperty("global_name")
    private String globalName;

    // Getters and setters
    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }
  }

  /**
   * Gets the username.
   *
   * @param discordId the discord ID
   * @return the username
   */
  public String getUsername(String discordId)
      throws IOException, ExecutionException, InterruptedException {
    try {
      return getString(pgController.getAccessToken(discordId), "username");

    } catch (Exception e) {
      refreshToken(pgController.getRefreshToken(discordId), discordId);
      return getString(pgController.getAccessToken(discordId), "username");
    }
  }

  /**
   * Refreshes the token.
   *
   * @param refreshToken the refresh token
   * @param discordId the discord ID
   * @throws IOException if an I/O error occurs
   * @throws ExecutionException if an execution error occurs
   * @throws InterruptedException if an interrupt error occurs
   */
  public void refreshToken(String refreshToken, String discordId)
      throws IOException, ExecutionException, InterruptedException {
    final OAuth2AccessToken accessToken = service.refreshAccessToken(refreshToken);
    pgController.setRefreshToken(discordId, accessToken.getRefreshToken());
    pgController.changeAuthToken(
        accessToken.getAccessToken(), pgController.getDiscordId(refreshToken));
  }

  /**
   * Gets the tokens.
   *
   * @param code the code
   * @return the tokens
   * @throws IOException if an I/O error occurs
   * @throws ExecutionException if an execution error occurs
   * @throws InterruptedException if an interrupt error occurs
   */
  public OAuth2AccessToken getTokens(String code)
      throws IOException, ExecutionException, InterruptedException {
    return service.getAccessToken(code);
  }

  /**
   * Gets the discord ID.
   *
   * @param accessToken the access token
   * @return the discord ID
   * @throws IOException if an I/O error occurs
   * @throws ExecutionException if an execution error occurs
   * @throws InterruptedException if an interrupt error occurs
   */
  public String getDiscordId(String accessToken)
      throws IOException, ExecutionException, InterruptedException {
    try {
      return getString(accessToken, "id");
    } catch (Exception e) {
      String discordId = pgController.getDiscordId(accessToken);
      refreshToken(pgController.getRefreshToken(discordId), discordId);
      return getString(accessToken, "id");
    }
  }

  private String getString(String accessToken, String type)
      throws InterruptedException, ExecutionException, IOException {
    final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
    service.signRequest(accessToken, request);
    Response response = service.execute(request);
    Logger.getGlobal().info(response.getBody());
    return response.getBody().split("\"" + type + "\":\"")[1].split("\"")[0];
  }
}

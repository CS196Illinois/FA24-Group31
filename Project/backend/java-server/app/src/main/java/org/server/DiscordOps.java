package org.server;

import io.github.cdimascio.dotenv.Dotenv;
import io.mokulu.discord.oauth.DiscordAPI;
import io.mokulu.discord.oauth.DiscordOAuth;
import io.mokulu.discord.oauth.model.TokensResponse;
import io.mokulu.discord.oauth.model.User;
import java.io.IOException;

/** This class represents the Discord operations. {@code @Author} adhit2 */
public class DiscordOps {
  private static final Dotenv dotenv = Dotenv.load();
  private static final String clientSecret = dotenv.get("DISCORD_CLIENT_SECRET");
  private static final String clientId = dotenv.get("DISCORD_CLIENT_ID");
  private static final String redirectUri = dotenv.get("DISCORD_REDIRECT_URI");
  private static final String[] scope = {"identify"};
  private static final PostgreSQLController pgController = new PostgreSQLController();

  private static final DiscordOAuth oauthHandler =
      new DiscordOAuth(clientId, clientSecret, redirectUri, scope);

  /**
   * Fetches the username.
   *
   * @param discordId discord ID
   * @return the username
   * @throws IOException if an error occurs
   */
  public static String getUsername(String discordId) throws IOException {
    String accessToken = pgController.getAccessToken(discordId);
    User user;
    try {
      DiscordAPI api = new DiscordAPI(accessToken);
      user = api.fetchUser();
    } catch (IOException e) {
      // Token might be expired, refresh it
      pgController.changeAuthToken(discordId);
      accessToken = pgController.getAccessToken(discordId);
      user = new DiscordAPI(accessToken).fetchUser();
    }
    return user.getUsername();
  }

  /**
   * Gets the user info.
   *
   * @param refreshToken refresh token
   * @return the user info
   * @throws IOException if an error occurs
   */
  public static String refreshToken(String refreshToken) throws IOException {
    TokensResponse tokensResponse = oauthHandler.refreshTokens(refreshToken);
    return tokensResponse.getAccessToken();
  }

  /**
   * Gets the tokens.
   *
   * @param code the code
   * @return the tokens
   * @throws IOException if an error occurs
   */
  public static TokensResponse getTokens(String code) throws IOException {
    return oauthHandler.getTokens(code);
  }

  /**
   * Gets the discord ID.
   *
   * @param accessToken the access token
   * @return the discord ID
   * @throws IOException if an error occurs
   */
  public static String getDiscordId(String accessToken) throws IOException {
    return new DiscordAPI(accessToken).fetchUser().getId();
  }
}

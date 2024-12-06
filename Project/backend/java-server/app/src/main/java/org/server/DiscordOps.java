package org.server;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/** This class represents the Discord operations. {@code @Author} adhit2 */
public class DiscordOps {
  private static final Dotenv dotenv = Dotenv.load();
  private static final String clientSecret = dotenv.get("DISCORD_CLIENT_SECRET");
  private static final String clientId = dotenv.get("DISCORD_CLIENT_ID");
  private static final String redirectUri = dotenv.get("DISCORD_REDIRECT_URI");
  private static final String[] scope = {"identify"};
  private static final PostgreSQLController pgController = new PostgreSQLController();
  private static final HttpClient httpClient = HttpClient.newHttpClient();
  private static final String DISCORD_API_BASE = "https://discord.com/api";

  /**
   * Fetches the username.
   *
   * @param discordId discord ID
   * @return the username
   * @throws IOException if an error occurs
   */
  public static String getUsername(String discordId) throws IOException {
    String accessToken = pgController.getAccessToken(discordId);
    JSONObject user;
    try {
      user = makeUserRequest(accessToken);
    } catch (IOException e) {
      // Token might be expired, refresh it
      pgController.changeAuthToken(discordId);
      accessToken = pgController.getAccessToken(discordId);
      user = makeUserRequest(accessToken);
    }
    return user.getString("username");
  }

  private static JSONObject makeUserRequest(String accessToken) throws IOException {
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(DISCORD_API_BASE + "/users/@me"))
            .header("Authorization", "Bearer " + accessToken)
            .GET()
            .build();

    try {
      HttpResponse<String> response =
          httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      return new JSONObject(response.body());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IOException("Request interrupted", e);
    }
  }

  /**
   * Gets new tokens using the refresh token.
   *
   * @param refreshToken refresh token
   * @return the new access token
   * @throws IOException if an error occurs
   */
  public static String refreshToken(String refreshToken) throws IOException {
    Map<String, String> params = new HashMap<>();
    params.put("client_id", clientId);
    params.put("client_secret", clientSecret);
    params.put("grant_type", "refresh_token");
    params.put("refresh_token", refreshToken);

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(DISCORD_API_BASE + "/oauth2/token"))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(buildFormDataFromMap(params))
            .build();

    try {
      HttpResponse<String> response =
          httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      JSONObject tokens = new JSONObject(response.body());
      return tokens.getString("access_token");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IOException("Request interrupted", e);
    }
  }

  /**
   * Gets the tokens using authorization code.
   *
   * @param code the authorization code
   * @return JSONObject containing access_token and refresh_token
   * @throws IOException if an error occurs
   */
  public static JSONObject getTokens(String code) throws IOException {
    Map<String, String> params = new HashMap<>();
    params.put("client_id", clientId);
    params.put("client_secret", clientSecret);
    params.put("grant_type", "authorization_code");
    params.put("code", code);
    params.put("redirect_uri", redirectUri);

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(DISCORD_API_BASE + "/oauth2/token"))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(buildFormDataFromMap(params))
            .build();

    try {
      HttpResponse<String> response =
          httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      return new JSONObject(response.body());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IOException("Request interrupted", e);
    }
  }

  /**
   * Gets the discord ID.
   *
   * @param accessToken the access token
   * @return the discord ID
   * @throws IOException if an error occurs
   */
  public static String getDiscordId(String accessToken) throws IOException {
    JSONObject user = makeUserRequest(accessToken);
    return user.getString("id");
  }

  private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<String, String> data) {
    StringBuilder builder = new StringBuilder();
    for (Map.Entry<String, String> entry : data.entrySet()) {
      if (!builder.isEmpty()) {
        builder.append("&");
      }
      builder.append(entry.getKey()).append("=").append(entry.getValue());
    }
    return HttpRequest.BodyPublishers.ofString(builder.toString());
  }
}

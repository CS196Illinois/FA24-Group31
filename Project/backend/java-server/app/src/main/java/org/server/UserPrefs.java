package org.server;

public class UserPrefs {
  private int minAge;
  private int maxAge;
  private String[] ranks;
  private String[] roles;
  private String sessionToken;

  public UserPrefs(
      String discordId,
      int minAge,
      int maxAge,
      String[] ranks,
      String[] roles,
      String sessionToken) {
    this.minAge = minAge;
    this.maxAge = maxAge;
    this.ranks = ranks;
    this.roles = roles;
    this.sessionToken = sessionToken;
  }

  public int getMinAge() {
    return minAge;
  }

  public void setMinAge(int minAge) {
    this.minAge = minAge;
  }

  public int getMaxAge() {
    return maxAge;
  }

  public void setMaxAge(int maxAge) {
    this.maxAge = maxAge;
  }

  public String[] getRanks() {
    return ranks;
  }

  public void setRanks(String[] ranks) {
    this.ranks = ranks;
  }

  public String[] getRoles() {
    return roles;
  }

  public void setRoles(String[] roles) {
    this.roles = roles;
  }

  public String getSessionToken() {
    return sessionToken;
  }

  public void setSessionToken(String sessionToken) {
    this.sessionToken = sessionToken;
  }
}

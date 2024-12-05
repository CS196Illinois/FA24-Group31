package org.server;

public class PublicUser {
  private String discordId;
  private String riotId;
  private String firstName;
  private String lastName;
  private String[] pronouns;
  private String description;
  private String[] roles;
  private String rank;
  private String image;

  public PublicUser(String discordId, String riotId, String firstName, String lastName, String[] pronouns, String description, String[] roles, String rank, String image) {
    this.discordId = discordId;
    this.riotId = riotId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.pronouns = pronouns;
    this.description = description;
    this.roles = roles;
    this.rank = rank;
    this.image = image;
  }

  public String getDiscordId() {
    return discordId;
  }

  public void setDiscordId(String discordId) {
    this.discordId = discordId;
  }

  public String getRiotId() {
    return riotId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String[] getPronouns() {
    return pronouns;
  }

  public void setPronouns(String[] pronouns) {
    this.pronouns = pronouns;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String[] getRoles() {
    return roles;
  }

  public void setRoles(String[] roles) {
    this.roles = roles;
  }

  public String getRank() {
    return rank;
  }

  public void setRank(String rank) {
    this.rank = rank;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}

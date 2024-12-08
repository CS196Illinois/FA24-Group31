package org.server;

/**
 * This class represents a public user.
 *
 * @author adhit2
 */
public class PublicUser {
  private String discordId;
  String riotId;
  private String firstName;
  private String lastName;
  private String[] pronouns;
  private String description;
  private String[] roles;
  private String rank;
  private String image;

  /**
   * Constructs a new {@link PublicUser} with the specified discord ID, riot ID, first name, last
   * name, pronouns, description, roles, rank, and image.
   *
   * @param discordId the discord ID
   * @param riotId the riot ID
   * @param firstName the first name
   * @param lastName the last name
   * @param pronouns the pronouns
   * @param description the description
   * @param roles the roles
   * @param rank the rank
   * @param image the image
   */
  public PublicUser(
      String discordId,
      String riotId,
      String firstName,
      String lastName,
      String[] pronouns,
      String description,
      String[] roles,
      String rank,
      String image) {
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

  /** Constructs a new {@link PublicUser}. */
  public PublicUser() {}

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

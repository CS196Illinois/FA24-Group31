package org.server;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;

/** This class represents a private user. @Author adhit2 */
public class PrivateUser {
  private String discordId;
  private String dob;
  private String[] oneWayMatched;
  private String[] twoWayMatched;
  private String[] seen;

  /**
   * Constructs a new {@link PrivateUser} with the specified discord ID, date of birth, one-way.
   *
   * @param discordId the discord ID
   * @param dob the date of birth
   * @param oneWayMatched the one-way matched
   * @param twoWayMatched the two-way matched
   */
  public PrivateUser(
      String discordId, String dob, String[] oneWayMatched, String[] twoWayMatched, String[] seen) {
    this.discordId = discordId;
    this.dob = dob;
    this.oneWayMatched = oneWayMatched;
    this.twoWayMatched = twoWayMatched;
    this.seen = seen;
  }

  /** Constructs a new {@link PrivateUser}. */
  public String getDiscordId() {
    return discordId;
  }

  public void setDiscordId(String discordId) {
    this.discordId = discordId;
  }

  public int getAge() {
    return Period.between(LocalDate.parse(dob), LocalDate.now()).getYears();
  }

  public String getDoB() {
    return dob;
  }

  public void setDoB(String dob) {
    this.dob = dob;
  }

  public String[] getOneWayMatched() {
    return oneWayMatched;
  }

  public void setOneWayMatched(String[] oneWayMatched) {
    this.oneWayMatched = oneWayMatched;
  }

  public String[] getTwoWayMatched() {
    return twoWayMatched;
  }

  public void setTwoWayMatched(String[] twoWayMatched) {
    this.twoWayMatched = twoWayMatched;
  }

  @Override
  public String toString() {
    return "PrivateUser{"
        + "discordId='"
        + discordId
        + '\''
        + ", dob='"
        + dob
        + '\''
        + ", oneWayMatched="
        + Arrays.toString(oneWayMatched)
        + ", twoWayMatched="
        + Arrays.toString(twoWayMatched)
        + '}';
  }

  public String[] getSeen() {
    return seen;
  }

  public void setSeen(String[] seen) {
    this.seen = seen;
  }
}

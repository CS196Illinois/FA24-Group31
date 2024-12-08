package org.server;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;

/** This class represents a private user. @Author adhit2 */
public class PrivateUser {
  private String discordId;
  private LocalDate dob;
  private String[] oneWayMatched;
  private String[] twoWayMatched;

  /**
   * Constructs a new {@link PrivateUser} with the specified discord ID, date of birth, one-way.
   *
   * @param discordId the discord ID
   * @param dob the date of birth
   * @param oneWayMatched the one-way matched
   * @param twoWayMatched the two-way matched
   */
  public PrivateUser(String discordId, String dob, String[] oneWayMatched, String[] twoWayMatched) {
    this.discordId = discordId;
    this.dob = LocalDate.parse(dob);
    this.oneWayMatched = oneWayMatched;
    this.twoWayMatched = twoWayMatched;
  }

  /** Constructs a new {@link PrivateUser}. */
  public PrivateUser() {}

  public String getDiscordId() {
    return discordId;
  }

  public void setDiscordId(String discordId) {
    this.discordId = discordId;
  }

  public int getAge() {
    return Period.between(dob, LocalDate.now()).getYears();
  }

  public LocalDate getDoB() {
    return dob;
  }

  public void setDoB(String dob) {
    this.dob = LocalDate.parse(dob);
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
}

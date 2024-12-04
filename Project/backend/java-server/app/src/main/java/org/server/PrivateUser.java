package org.server;

import java.util.Arrays;
import java.time.LocalDate;
import java.time.Period;

public class PrivateUser {
  private String discordId;
  private LocalDate dob;
  private String[] oneWayMatched;
  private String[] twoWayMatched;

  public PrivateUser(String discordId, String dob, String[] oneWayMatched, String[] twoWayMatched) {
    this.discordId = discordId;
    this.dob = LocalDate.parse(dob);
    this.oneWayMatched = oneWayMatched;
    this.twoWayMatched = twoWayMatched;
  }

  public String getDiscordId() {
    return discordId;
  }

  public void setDiscordId(String discordId) {
    this.discordId = discordId;
  }

  public int getAge() {
    return Period.between(dob, LocalDate.now()).getYears();
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
    return "PrivateUser{" +
            "discordId='" + discordId + '\'' +
            ", dob='" + dob + '\'' +
            ", oneWayMatched=" + Arrays.toString(oneWayMatched) +
            ", twoWayMatched=" + Arrays.toString(twoWayMatched) +
            '}';

  }
}

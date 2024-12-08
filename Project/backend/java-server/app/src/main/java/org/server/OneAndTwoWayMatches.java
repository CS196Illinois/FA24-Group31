package org.server;

public class OneAndTwoWayMatches {

  public boolean updateMatches(String discordId, String matchedId) {
    PostgreSQLController pgController = new PostgreSQLController();
    boolean exists = pgController.existsInOneWay(matchedId, discordId);
    if (pgController.containsTwoWayMatched(discordId, matchedId)) {
      return false;
    }
    boolean wasAddedToTwoWay = false;
    if (exists) {
      boolean deleted = pgController.deleteOneWayMatched(matchedId, discordId);
      if (!deleted) {
        return false;
      }
      boolean added_to_two_way = pgController.updateTwoWayMatched(discordId, matchedId);
      if (added_to_two_way) {
        wasAddedToTwoWay = true;
      }
    } else {
      boolean existsInOneWay = pgController.existsInOneWay(matchedId, discordId);
      if (!existsInOneWay) {
        boolean added = pgController.updateOneWayMatched(discordId, matchedId);
        if (!added) {
          return wasAddedToTwoWay;
        }
      } else {
        return false;
      }
    }
    return wasAddedToTwoWay;
  }
}

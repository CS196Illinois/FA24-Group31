package org.server;

import org.server.PostgreSQLController;

public class OneAndTwoWayMatches {

  public boolean updateMatches(String discordId, String matchedId) {
    PostgreSQLController pgController = new PostgreSQLController();
    // check if we are in the one way oiof the other oine, if som remove from that perosns one way
    // and add to both of our 2ways
    // return true if this happens
    // if not, add that person to our one way.
    // return false if this happens
    if (pgController.existsInOneWay(discordId, matchedId)) {
      pgController.deleteOneWayMatched(discordId, matchedId);
      pgController.updateTwoWayMatched(discordId, matchedId);
      pgController.updateTwoWayMatched(matchedId, discordId);
      return true;
    } else if (pgController.existsInOneWay(matchedId, discordId)) {
      pgController.deleteOneWayMatched(matchedId, discordId);
      pgController.updateTwoWayMatched(discordId, matchedId);
      pgController.updateTwoWayMatched(matchedId, discordId);
      return true;
    } else {
      pgController.updateOneWayMatched(discordId, matchedId);
      return false;
    }
  }
}
